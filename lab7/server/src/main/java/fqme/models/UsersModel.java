package fqme.models;

import fqme.column.common.ByteArrayColumn;
import fqme.column.common.StringColumn;
import fqme.column.common.numeric.SerialColumn;
import fqme.model.Model;
import fqme.model.reflection.ColumnData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UsersModel extends Model<UsersModel>{
    @ColumnData
    @Getter
    private Integer id;
    public static final SerialColumn id_ = SerialColumn.of("id").primary();

    @ColumnData
    @NonNull
    private String login;
    public static final StringColumn login_ = StringColumn.of("login");

    @ColumnData
    @NonNull
    private String password;
    public static final StringColumn password_ = StringColumn.of("password");

    @ColumnData
    @NonNull
    private byte[] salt;
    public static final ByteArrayColumn salt_ = ByteArrayColumn.of("salt");
    
}
