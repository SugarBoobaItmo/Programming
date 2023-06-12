package fqme.models;

import java.time.LocalDateTime;

import fqme.column.common.DateTimeColumn;
import fqme.column.common.ForeignColumn;
import fqme.column.common.StringColumn;
import fqme.column.common.numeric.BigIntColumn;
import fqme.column.common.numeric.IntegerColumn;
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
public class StudyGroupModel extends Model<StudyGroupModel>{

    @ColumnData
    private Integer id;
    public static final SerialColumn id_ = SerialColumn.of("id").primary();

    @ColumnData
    @NonNull
    private String key;
    public static final StringColumn key_ = StringColumn.of("key");

    @ColumnData
    @NonNull
    private String name;
    public static final StringColumn name_ = StringColumn.of("name");

    @ColumnData
    @NonNull
    private Integer x;
    public static final IntegerColumn x_ = IntegerColumn.of("x");

    @ColumnData
    @NonNull
    private Integer y;
    public static final IntegerColumn y_ = IntegerColumn.of("y");

    @ColumnData
    @NonNull
    private LocalDateTime creationDate;
    public static final DateTimeColumn creationDate_ = DateTimeColumn.of("creationDate");
    
    @ColumnData
    // @NonNull
    private Long studentsCount;
    public static final BigIntColumn studentsCount_ = BigIntColumn.of("studentsCount").nullable(true);

    @ColumnData
    @NonNull
    private Long expelledStudents;
    public static final BigIntColumn expelledStudents_ = BigIntColumn.of("expelledStudents");

    @ColumnData
    @NonNull
    private Long transferredStudents;
    public static final BigIntColumn transferredStudents_ = BigIntColumn.of("transferredStudents");

    @ColumnData
    // @NonNull
    private String semesterEnum;
    public static final StringColumn semesterEnum_ = StringColumn.of("semesterEnum").nullable(true);

    @ColumnData
    // @NonNull
    private Integer adminId;
    public static final ForeignColumn adminId_ = ForeignColumn.of("adminId", PersonModel.class,
            PersonModel.id_).nullable(true);

    @ColumnData
    @NonNull
    private Integer ownerId;
    public static final ForeignColumn ownerId_ = ForeignColumn.of("ownerId", UsersModel.class,
            UsersModel.id_);
    
    }
