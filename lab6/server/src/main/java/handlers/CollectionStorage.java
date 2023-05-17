package handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.TreeMap;

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
    public static synchronized CollectionRecord load(String userAdress) throws ServerStorageException {
        if (collectionRecord == null) {
            collectionRecord = createNewCollectionRecord(userAdress);
        }
        String sql = "SELECT groups.owner_id, groups.key, groups.id, groups.name, groups.x, groups.y, "
                + "groups.creation_date, groups.students_count, groups.expelled_students, groups.transferred_students, "
                + "groups.semester, person.name as admin_name , person.birthday, person.passportID, person.hairColor "
                + "FROM groups LEFT JOIN person ON groups.pers_id = person.id";

        DatabaseManager dbManager = new DatabaseManager();
        try {
            Connection connection = dbManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() == false) {
                collectionRecord.getCollection().clear();
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
                StudyGroup studyGroup = new StudyGroup(id, name, coordinates, creationDate, studentsCount,
                        expelledStudents, transferredStudents, semester, person);
                studyGroup.setOwner(owner);

                collectionRecord.getCollection().put(resultSet.getString("key"), studyGroup);
                connection.close();
            } while (resultSet.next());

            return collectionRecord;
        } catch (SQLException e) {
            throw new ServerStorageException(e.getMessage());
        } catch (IOException e) {
            throw new ServerStorageException(e.getMessage());
        }
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
    public static CollectionRecord createNewCollectionRecord(String userAddress) throws ServerStorageException {
        CollectionRecord collectionRecord;

        TreeMap<String, StudyGroup> collection = new TreeMap<String, StudyGroup>();
        CollectionInfo info = new CollectionInfo(LocalDateTime.now(), userAddress);
        collectionRecord = new CollectionRecord(collection, info);
        return collectionRecord;
    }

    /**
     * 
     * Returns the static HashMap that stores all the collections of all the users.
     * 
     * @return the static HashMap that stores all the collections of all the users
     */
    public static HashMap<String, CollectionRecord> getConnectionMap() {
        return connectionMap;
    }

    public static CollectionRecord getCollectionRecord() {
        if (collectionRecord == null) {
            try {
                // collectionRecord = createNewCollectionRecord("server");
                collectionRecord = load("server");
            } catch (ServerStorageException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
        return collectionRecord;
    }
    
}
