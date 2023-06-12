package fqme.models;

import java.time.LocalDateTime;

import fqme.column.common.DateTimeColumn;
import fqme.column.common.StringColumn;
import fqme.column.common.numeric.SerialColumn;
import fqme.model.Model;
import fqme.model.reflection.ColumnData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PersonModel extends Model<PersonModel>{

    @ColumnData
    private Integer id;
    public static final SerialColumn id_ = SerialColumn.of("id").primary();

    @ColumnData
    @NonNull
    private String name;
    public static final StringColumn name_ = StringColumn.of("name");

    
    @ColumnData
    @NonNull
    private LocalDateTime birthday;
    public static final DateTimeColumn birthday_ = DateTimeColumn.of("birthday");

    @ColumnData
    @NonNull
    private String passportID;
    public static final StringColumn passportID_ = StringColumn.of("passportID");

    @ColumnData
    @NonNull
    private String hairColor;
    public static final StringColumn hairColor_ = StringColumn.of("hairColor");


}
