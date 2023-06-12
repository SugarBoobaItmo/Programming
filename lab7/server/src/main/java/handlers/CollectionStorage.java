package handlers;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;

import fqme.column.exceptions.UnsupportedSqlType;
import fqme.column.exceptions.UnsupportedValueType;
import fqme.connection.ConnectionManager;
import fqme.models.PersonModel;
import fqme.models.StudyGroupModel;
import fqme.view.View;
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

        try (Connection connection = ConnectionManager.getConnection(StudyGroupModel.class)) {

            View<PersonModel> personView = View.of(PersonModel.class, connection);
            View<StudyGroupModel> groupView = View.of(StudyGroupModel.class, connection);

            Set<StudyGroupModel> groupModels = groupView.get();

            if (groupModels.isEmpty()) {
                collectionRecord.getCollection().clear();
                return collectionRecord;
            }

            for (StudyGroupModel groupModel : groupModels) {

                Integer owner = groupModel.getOwnerId();
                Integer id = groupModel.getId();
                String name = groupModel.getName();
                Integer x = groupModel.getX();
                Integer y = groupModel.getY();
                Coordinates coordinates = new Coordinates(x, y);
                LocalDateTime creationDate = groupModel.getCreationDate();

                Long studentsCount = groupModel.getStudentsCount();
                Long expelledStudents = groupModel.getExpelledStudents();
                Long transferredStudents = groupModel.getTransferredStudents();

                Semester semester;
                if (groupModel.getSemesterEnum() == null) {
                    semester = null;
                } else {
                    semester = Semester.valueOf(groupModel.getSemesterEnum());
                }

                Integer adminId = groupModel.getAdminId();
                Person person;
                if (adminId == null) {
                    person = null;
                } else {
                    PersonModel personModel = (PersonModel) personView.get(PersonModel.id_.eq(adminId))
                            .iterator().next();
                    person = new Person(
                            personModel.getName(),
                            personModel.getBirthday(),
                            personModel.getPassportID(),
                            Color.valueOf(personModel.getHairColor()));
                }
                StudyGroup studyGroup = new StudyGroup(id, name, coordinates, creationDate, studentsCount,
                        expelledStudents, transferredStudents, semester, person);
                studyGroup.setOwner(owner.toString());

                collectionRecord.getCollection().put(groupModel.getKey(), studyGroup);

            }

            return collectionRecord;
        } catch (SQLException e) {
            throw new ServerStorageException("Error while loading collection", e);
        } catch (UnsupportedValueType e) {
            throw new ServerStorageException("Error while loading collection", e);
        } catch (UnsupportedSqlType e) {
            throw new ServerStorageException("Error while loading collection", e);
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

    public static CollectionRecord getCollectionRecord() throws ServerStorageException {
        if (collectionRecord == null) {
            try {
                collectionRecord = load("server");
            } catch (ServerStorageException e) {
                throw new ServerStorageException("Error while loading collection", e);
            }
        }
        return collectionRecord;
    }

}
