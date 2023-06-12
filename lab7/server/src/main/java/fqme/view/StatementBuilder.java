package fqme.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import fqme.column.Column;
import fqme.column.exceptions.UnsupportedValueType;
import fqme.model.Model;
import fqme.model.reflection.ModelReflection;
import fqme.query.Query;
import fqme.query.QueryArgument;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatementBuilder<T extends Model<T>> {
    /**
     * A model class that is associated with this view.
     */
    private final ModelReflection<T> modelReflection;

    /**
     * A connection to the database.
     */
    private final Connection connection;

    /**
     * Build a statement for creating a table.
     *
     * @return
     * @throws Exception
     */
    public PreparedStatement buildCreateTableStatement() throws SQLException {
        String tableName = modelReflection.getTableName();
        List<String> columnsDefinitions = new ArrayList<>();
        for (Entry<String, Column<?, ?>> entry : modelReflection.getColumns().entrySet()) {
            String columnName = entry.getKey();
            Column<?, ?> column = entry.getValue();

            String columnDefinition = String.format("%s %s", columnName, column.getSqlDefinition());
            columnsDefinitions.add(columnDefinition);

            if (entry.getValue().isPrimary()) {
                String primaryColumnIndex = String.format("CONSTRAINT %s_%s_unique UNIQUE (%s)"
                        , tableName, columnName, columnName);

                columnsDefinitions.add(primaryColumnIndex);
            }
        }
        String sql = String.format("CREATE TABLE IF NOT EXISTS %s (%s)"
                , tableName, String.join(", ", columnsDefinitions));

        PreparedStatement statement = connection.prepareStatement(sql);
        return statement;
    }

    /**
     * Build a statement for getting many models by a query.
     *
     * @param query a query
     * @return a statement
     * @throws Exception
     */
    public PreparedStatement buildGetStatement(Query query) throws SQLException, UnsupportedValueType {
        String sql = String.format("SELECT * FROM %s WHERE %s"
                , modelReflection.getTableName(), query.getWhereClause());

        PreparedStatement statement = connection.prepareStatement(sql);
        List<QueryArgument<?, ?>> whereArgs = query.getWhereArgs();
        for (Integer index = 0; index < whereArgs.size(); index++) {
            QueryArgument<?, ?> argument = whereArgs.get(index);
            argument.getColumn().setToStatement(statement, index + 1, argument.getValue());
        }
        return statement;
    }

    public PreparedStatement buildGetStatement() throws SQLException, UnsupportedValueType {
        String sql = String.format("SELECT * FROM %s"
                , modelReflection.getTableName());

        PreparedStatement statement = connection.prepareStatement(sql);
        return statement;
    }

    /**
     * Build a statement for deleting models.
     *
     * @param query a query
     * @return a statement
     * @throws Exception
     */
    public PreparedStatement buildDeleteStatement(Query query) throws SQLException, UnsupportedValueType {
        String sql = String.format("DELETE FROM %s WHERE %s RETURNING *"
                , modelReflection.getTableName(), query.getWhereClause());

        PreparedStatement statement = connection.prepareStatement(sql);
        List<QueryArgument<?, ?>> whereArgs = query.getWhereArgs();
        for (Integer index = 0; index < whereArgs.size(); index++) {
            QueryArgument<?, ?> argument = whereArgs.get(index);
            argument.getColumn().setToStatement(statement, index + 1, argument.getValue());
        }
        return statement;
    }

    public PreparedStatement buildDeleteStatement() throws SQLException, UnsupportedValueType {
        String sql = String.format("DELETE FROM %s RETURNING *"
                , modelReflection.getTableName());

        PreparedStatement statement = connection.prepareStatement(sql);
        return statement;
    }

    /**
     * Build statement for inserting model.
     * If a model already exists, it will be updated.
     *
     * @param model a model
     * @return a prepared statement
     */
    public PreparedStatement buildPutStatement(T model) throws SQLException, UnsupportedValueType {
        LinkedHashMap<String, Object> fieldsValues = modelReflection.getFieldsSupplier().getFieldsValues(model);

        LinkedHashMap<String, Column<?, ?>> settableColumns = new LinkedHashMap<>();
        LinkedHashMap<String, Column<?, ?>> primaryColumns = new LinkedHashMap<>();
        for (Entry<String, Column<?, ?>> entry : modelReflection.getColumns().entrySet()) {
            String columnName = entry.getKey();
            Column<?, ?> column = entry.getValue();
            Object fieldValue = fieldsValues.get(columnName);

            if (column.isPrimary()) {
                primaryColumns.put(columnName, column);
            }

            if (fieldValue == null && column.isPrimary()) {
                continue;
            } else if (fieldValue == null && !column.isNullable()) {
                throw new IllegalArgumentException("Column " + columnName + " is not nullable");
            } else {
                settableColumns.put(columnName, column);
            }
        }
        String fieldsNames = String.join(", ", settableColumns.keySet());
        String placeholders = String.join(", ", Collections.nCopies(settableColumns.size(), "?"));
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)"
                , modelReflection.getTableName(), fieldsNames, placeholders);

        if (!primaryColumns.isEmpty()) {
            String conflictClause = String.join(", ", primaryColumns.keySet());
            String updateClause = settableColumns.keySet().stream()
            .map(columnName -> columnName + " = EXCLUDED." + columnName)
            .collect(Collectors.joining(", "));

            sql += String.format(" ON CONFLICT (%s) DO UPDATE SET %s"
                    , conflictClause, updateClause);
        }
        sql += " RETURNING *";

        PreparedStatement statement = connection.prepareStatement(sql);
        Integer index = 1;
        for (Column<?, ?> column : settableColumns.values()) {
            Object fieldValue = fieldsValues.get(column.getName());
            column.setToStatement(statement, index++, fieldValue);
        }
        return statement;
    }
}
