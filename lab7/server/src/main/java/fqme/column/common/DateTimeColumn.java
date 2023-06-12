package fqme.column.common;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import fqme.column.Column;
import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;
import fqme.query.Query;
import fqme.query.QueryArgument;

/**
 * Column realization for DateTime values.
 *
 * @see fqme.column.Column
 */
public class DateTimeColumn extends Column<DateTimeColumn, LocalDateTime> {
    /**
     * Default constructor.
     *
     * @param name name of the column.
     */
    public DateTimeColumn(String name) {
        super(name);
    }

    /**
     * Factory method for creating a column.
     */
    public static DateTimeColumn of(String name) {
        return new DateTimeColumn(name);
    }

    /**
     * Return sql type of the column.
     *
     * @return TIMESTAMP.
     */
    @Override
    public String _getSqlDefinition() {
        return "TIMESTAMP";
    }

    /**
     * Convert value from the database to the java type.
     *
     * @param value expect Timestamp, Date or Time value.
     * @return value converted to the java LocalDateTime type.
     * @throws UnsupportedSqlType if value is not Timestamp, Date or Time.
     */
    @Override
    public LocalDateTime fromSqlType(Object value) throws UnsupportedSqlType {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedSqlType("Value cannot be null.");
            }
            return null;
        } else
        if (value instanceof Timestamp) {
            return ((Timestamp) value).toLocalDateTime();
        } else if (value instanceof Date) {
            return ((Date) value).toLocalDate().atStartOfDay();
        } else if (value instanceof java.sql.Time) {
            return ((Time) value).toLocalTime().atDate(LocalDateTime.now().toLocalDate());
        }
        throw new UnsupportedSqlType(
                String.format("Expected Timestamp, Date or Time got %s instead.", value.getClass().getName()));
    }

    /**
     * Set column to statement
     *
     * @param statement statement to set column to.
     * @param index     index of the column in the statement.
     * @param value     expect LocalDateTime value.
     */
    @Override
    public void setToStatement(PreparedStatement statement, Integer index, Object value)
            throws UnsupportedValueType, SQLException {
        if (value == null) {
            if (!isNullable()) {
                throw new UnsupportedValueType("Value cannot be null.");
            }
            statement.setNull(index, java.sql.Types.TIMESTAMP);

        } else if (value instanceof LocalDateTime) {
            statement.setTimestamp(index, Timestamp.valueOf((LocalDateTime) value));
        } else if (value instanceof Timestamp) {
            statement.setTimestamp(index, (Timestamp) value);
        } else if (value instanceof Date) {
            statement.setDate(index, (Date) value);
        } else if (value instanceof Time) {
            statement.setTime(index, (Time) value);
        } else {
            throw new UnsupportedValueType(
                    String.format("Expected LocalDateTime got %s instead.", value.getClass().getName()));
        }
    }

    /**
     * Return query for before checking.
     *
     * @see fqme.query.Query
     *
     * @param value value to compare with.
     * @return query for before checking.
     */
    public Query before(LocalDateTime value) {
        return new Query("< ?", QueryArgument.of(this, value));
    }

    /**
     * Return query for after checking.
     *
     * @see fqme.query.Query
     *
     * @param value value to compare with.
     * @return query for after checking.
     */
    public Query after(LocalDateTime value) {
        return new Query(this.getName() + " > ?", QueryArgument.of(this, value));
    }

    /**
     * Return query for between checking.
     *
     * @see fqme.query.Query
     *
     * @param startTime start time to compare with.
     * @param endTime   end time to compare with.
     * @return query for between checking.
     */
    public Query between(LocalDateTime startTime, LocalDateTime endTime) {
        return new Query(this.getName() + " BETWEEN ? AND ?", QueryArgument.of(this, startTime),
                QueryArgument.of(this, endTime));
    }
}
