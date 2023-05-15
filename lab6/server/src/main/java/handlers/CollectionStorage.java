package handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.TreeMap;

import csvutils.CsvUtils;
import database.DatabaseManager;
import handlers.exceptions.ServerStorageException;
import models.CollectionRecord;
import models.Color;
import models.Coordinates;
import models.Person;
import models.Semester;
import models.CollectionInfo;
import models.StudyGroup;

/**
 * 
 * The CollectionStorage class provides functionality to load and save a
 * collection record.
 * 
 */
public class CollectionStorage {
    // static map to store all the collections of all the users
    // private static CollectionRecord collectionRecord = new CollectionRecord();
    public static HashMap<String, CollectionRecord> connectionMap = new HashMap<>();
    private static CollectionRecord collectionRecord;
    /**
     * 
     * Loads the collection for the specified user from the HashMap. If the
     * collection does not exist in the HashMap, it is created and added to the
     * HashMap.
     * 
     * @param userAddress the address of the user whose collection to load
     * @return the CollectionRecord object associated with the user
     * @throws ServerStorageException if an error occurs while loading the
     *                                collection
     */
    public static CollectionRecord load(String userAdress) throws ServerStorageException {
        // if the collection is already exist in the map, return it
        if (connectionMap.containsKey(userAdress)) {
            return (CollectionRecord) connectionMap.get(userAdress);
        } 
        if (collectionRecord == null) {
            collectionRecord = createNewCollectionRecord(userAdress);
        }
        String sql = "SELECT groups.owner_id, groups.key, groups.id, groups.name, groups.x, groups.y, groups.creation_date, groups.students_count, groups.expelled_students, groups.transferred_students, groups.semester, person.name as admin_name , person.birthday, person.passportID, person.hairColor FROM groups LEFT JOIN person ON groups.pers_id = person.id";
        // String sql = "Select * from groups where owner_id = ?";
        
        DatabaseManager dbManager = new DatabaseManager();
        // CollectionRecord collectionRecord = createNewCollectionRecord(userAdress);
        try {
            Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            // statement.setString(1, userAdress);
            
            ResultSet resultSet = statement.executeQuery();
            // HashMap<String, StudyGroup> collection = new HashMap<String, StudyGroup>();
            if (resultSet.next() == false) {
                collectionRecord = createNewCollectionRecord(userAdress);
                return collectionRecord;
            }
            do {
                String owner = resultSet.getString("owner_id");
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Integer x = resultSet.getInt("x");
                Integer y = resultSet.getInt("y");
                Coordinates coordinates = new Coordinates(x, y);
                LocalDateTime creationDate = resultSet.getTimestamp("creation_date").toLocalDateTime();

                Long studentsCount = (Long) resultSet.getObject("students_count");
                if (resultSet.wasNull()) {
                    studentsCount = null;
                }

                Long expelledStudents = resultSet.getLong("expelled_students");
                Long transferredStudents = resultSet.getLong("transferred_students");
                // Semester semester = Semester.valueOf(resultSet.getString("semester"));
                // Semester semester = (Semester) resultSet.getObject("semester");
                Semester semester;
                if (resultSet.getObject("semester") == null) {
                    semester = null;
                } else {
                    semester = Semester.valueOf(resultSet.getString("semester"));
                }
                Person person = null;
                if (resultSet.getObject("admin_name") == null) {
                    person = null;
                } else {
                    String adminName = resultSet.getString("admin_name");
                    LocalDateTime birthday = resultSet.getTimestamp("birthday").toLocalDateTime();
                    String passportId = resultSet.getString("passportID");
                    Color hairColor = Color.valueOf(resultSet.getString("hairColor"));
                    person = new Person(adminName, birthday, passportId, hairColor);
                }
                // String adminName = resultSet.getString("admin_name");
                // LocalDateTime birthday = resultSet.getTimestamp("birthday").toLocalDateTime();
                // String passportId = resultSet.getString("passportID");
                // Color hairColor = Color.valueOf(resultSet.getString("hairColor"));
                // Person person = new Person(adminName, birthday, passportId, hairColor);
                StudyGroup studyGroup = new StudyGroup(id, name, coordinates, creationDate, studentsCount,
                        expelledStudents, transferredStudents, semester, person);
                studyGroup.setOwner(owner);
                // collection.put(resultSet.getString("key"), studyGroup);
                // return collectionRecord.getCollection().putAll(collection);
                // try {
                //     collectionRecord.getCollection().put(resultSet.getString("key"), studyGroup);
                // } catch (NullPointerException e) {
                //     collectionRecord = createNewCollectionRecord(userAdress);
                //     collectionRecord.getCollection().put(resultSet.getString("key"), studyGroup);
                //     return collectionRecord;
                // }
                collectionRecord.getCollection().put(resultSet.getString("key"), studyGroup);
            } while (resultSet.next());
            // if (collectionRecord.getCollection().isEmpty()) {
            //     collectionRecord = createNewCollectionRecord(userAdress);
            // }
            
            return collectionRecord;
        } catch (SQLException e) {
            e.printStackTrace();
            // collectionRecord = createNewCollectionRecord(userAdress);
        } catch (IOException e) {
            e.printStackTrace();
            // collectionRecord = createNewCollectionRecord(userAdress);

        }
        return collectionRecord;
    }

    /**
     * 
     * Creates a new CollectionRecord object for the specified user and saves it to
     * a file.
     * 
     * @param userAddress the address of the user for whom to create a new
     *                    collection
     * @return the newly created CollectionRecord object
     * @throws ServerStorageException if an error occurs while creating or saving
     *                                the collection record
     */
    public static CollectionRecord createNewCollectionRecord(String userAdress) throws ServerStorageException {
        CollectionRecord collectionRecord;

        TreeMap<String, StudyGroup> collection = new TreeMap<String, StudyGroup>();
        CollectionInfo info = new CollectionInfo(LocalDateTime.now(), userAdress);
        // create a new collection record
        collectionRecord = new CollectionRecord(
                collection,
                info);
        // save the collection record to the file
        // save(userAdress, collectionRecord);

        return collectionRecord;

    }

    /**
     * 
     * Saves the specified CollectionRecord object to a file.
     * 
     * @param userAddress      the address of the user whose collection to save
     * @param collectionRecord the CollectionRecord object to save
     * @throws ServerStorageException if an error occurs while saving the collection
     *                                record
     */
    // public static void save(String userAdress, CollectionRecord collectionRecord) throws ServerStorageException {
    //     String filePath = normalizePath(userAdress);
    //     CsvUtils.recordToCsv(collectionRecord, filePath);
    //     DatabaseManager dbManager = new DatabaseManager();
    //     try {
    //         Connection connection = dbManager.getConnection();

    //         collectionRecord.getCollection().forEach((key, value) -> {
    //             // dbManager.insertStudyGroup(connection, value);
    //             Integer adminId = null;
    //             String name = value.getName();
    //             Integer x = value.getCoordinates().getX();
    //             Integer y = value.getCoordinates().getY();
    //             LocalDateTime creationDate = value.getCreationDate();
    //             Long studentsCount = value.getStudentsCount();
    //             Long expelledStudents = value.getExpelledStudents();
    //             Long transferredStudents = value.getTransferredStudents();
    //             Semester semester = value.getSemesterEnum();
    //             Person groupAdmin = value.getGroupAdmin();

    //             if (groupAdmin != null) {
    //                 String adminName = groupAdmin.getName();
    //                 LocalDateTime birthday = groupAdmin.getBirthday();
    //                 String passportID = groupAdmin.getPassportID();
    //                 Color hairColor = groupAdmin.getHairColor();

    //                 // String adminSqlStatement = "INSERT INTO person (name, birthday, passportID,
    //                 // hairColor) VALUES ('" + adminName + "', '" + birthday + "', '" + passportID +
    //                 // "', '" + hairColor + "')";
    //                 String adminSqlStatement = "INSERT INTO person (name, birthday, passportID, hairColor) VALUES (?, ?, ?, ?)";
    //                 try {
    //                     connection.createStatement().execute(adminSqlStatement);
    //                     // String adminIdSqlStatement = "SELECT id FROM person WHERE name = '" +
    //                     // adminName + "' AND birthday = '" + "' AND passportID = '" + passportID + "'
    //                     // AND hairColor = '" + hairColor + "'";
    //                     // String adminIdSqlStatement =
    //                     // write ptrepared statement
    //                     String adminIdSqlStatement = "SELECT id FROM person WHERE name = ? AND birthday = ? AND passportID = ? AND hairColor = ?";
    //                     // prepare statement
    //                     PreparedStatement preparedStatement = connection.prepareStatement(adminIdSqlStatement);
    //                     // set values
    //                     preparedStatement.setString(1, adminName);
    //                     preparedStatement.setTimestamp(2, Timestamp.valueOf(birthday));
    //                     preparedStatement.setString(3, passportID);
    //                     preparedStatement.setString(4, hairColor.toString());

    //                     // execute query
    //                     ResultSet resultSet = preparedStatement.executeQuery();
    //                     // get result
    //                     while (resultSet.next()) {
    //                         adminId = resultSet.getInt("id");
    //                     }
    //                 } catch (SQLException e) {
    //                     e.printStackTrace();
    //                 }
    //                 try {
    //                     connection.createStatement().execute(adminSqlStatement);
    //                     // String adminIdSqlStatement = "SELECT id FROM person WHERE name = '" +
    //                     // adminName + "' AND birthday = '" + "' AND passportID = '" + passportID + "'
    //                     // AND hairColor = '" + hairColor + "'";
    //                     // String adminIdSqlStatement =
    //                     // write ptrepared statement
    //                     String adminIdSqlStatement = "SELECT id FROM person WHERE name = ? AND birthday = ? AND passportID = ? AND hairColor = ?";
    //                     // prepare statement
    //                     PreparedStatement preparedStatement = connection.prepareStatement(adminIdSqlStatement);
    //                     // set values
    //                     preparedStatement.setString(1, adminName);
    //                     preparedStatement.setTimestamp(2, Timestamp.valueOf(birthday));
    //                     preparedStatement.setString(3, passportID);
    //                     preparedStatement.setString(4, hairColor.toString());

    //                     // execute query
    //                     ResultSet resultSet = preparedStatement.executeQuery();
    //                     // get result
    //                     while (resultSet.next()) {
    //                         adminId = resultSet.getInt("id");
    //                     }
    //                 } catch (SQLException e) {
    //                     e.printStackTrace();
    //                 }
    //             }

    //             // String groupSqlStatement = "INSERT INTO groups (name, x, y, creationDate,
    //             // studentsCount, expelledStudents, transferredStudents, semesterEnum, pers_id)
    //             // VALUES ('" + name + "', '" + x + "', '" + y + "', '" + creationDate + "', '"
    //             // + studentsCount + "', '" + expelledStudents + "', '" + transferredStudents +
    //             // "', '" + semester + "', '" + adminId + "')";
    //             String groupSqlStatement = "INSERT INTO groups (name, x, y, creationDate, studentsCount, expelledStudents, transferredStudents, semesterEnum, pers_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    //             // write prepared statement
    //             try {
    //                 // prepare statement
    //                 PreparedStatement preparedStatement = connection.prepareStatement(groupSqlStatement);
    //                 // set values
    //                 preparedStatement.setString(1, name);
    //                 preparedStatement.setInt(2, x);
    //                 preparedStatement.setInt(3, y);
    //                 preparedStatement.setTimestamp(4, Timestamp.valueOf(creationDate));
    //                 // preparedStatement.setLong(5, studentsCount);
    //                 if (studentsCount == null) {
    //                     preparedStatement.setNull(5, java.sql.Types.BIGINT);
    //                 } else {
    //                     preparedStatement.setLong(5, studentsCount);
    //                 }
    //                 preparedStatement.setLong(6, expelledStudents);
    //                 preparedStatement.setLong(7, transferredStudents);
    //                 // preparedStatement.setString(8, semester.toString());
    //                 if (semester == null) {
    //                     preparedStatement.setNull(8, java.sql.Types.VARCHAR);
    //                 } else {
    //                     preparedStatement.setString(8, semester.toString());
    //                 }
    //                 // preparedStatement.setInt(9, adminId);
    //                 if (adminId == null) {
    //                     preparedStatement.setNull(9, java.sql.Types.INTEGER);
    //                 } else {
    //                     preparedStatement.setInt(9, adminId);
    //                 }
    //                 // execute query
    //                 preparedStatement.execute();
    //             } catch (SQLException e) {
    //                 e.printStackTrace();
    //             }

    //         });
    //     } catch (SQLException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     } catch (IOException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     }
    // }

    // /**
    //  * 
    //  * Normalizes the specified path by removing the ".csv" extension and any
    //  * trailing slash or port number.
    //  * 
    //  * @param path the path to normalize
    //  * @return the normalized path
    //  */
    // public static String normalizePath(String path) {
    //     path = path.replaceAll(".csv", "");
    //     return path.replaceAll("/|:\\d+", "") + ".csv";
    // }

    /**
     * 
     * Returns the static HashMap that stores all the collections of all the users.
     * 
     * @return the static HashMap that stores all the collections of all the users
     */
    public static HashMap<String, CollectionRecord> getConnectionMap() {
        return connectionMap;
    }
    
    // public boolean checkIfValueExists()
}
