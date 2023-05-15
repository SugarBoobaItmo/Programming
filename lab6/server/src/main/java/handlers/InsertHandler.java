package handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;

import database.DatabaseManager;
import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import models.CollectionRecord;
import models.StudyGroup;

/**
 * 
 * This class represents an Insert Handler, which is responsible for handling
 * requests
 * 
 * to insert a new study group into the collection. It extends the Handler class
 * and implements
 * 
 * the handle method and getName method.
 */
public class InsertHandler extends Handler {
    private String name = "insert";

    /**
     * 
     * Returns the command name of the InsertHandler.
     * 
     * @return The command name of the InsertHandler.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Handles the request to insert a new study group into the collection.
     * Generates a random id and
     * 
     * birthday for the study group and checks if the key already exists in the
     * collection.
     * 
     * @param request The request to insert a new study group.
     * 
     * @param userId  The id of the user making the request.
     * 
     * @return A Response object indicating whether the study group was added
     *         successfully or not.
     */
    @Override
    public Response handle(Request request, String userId) {
        Random random = new Random();

        CollectionRecord collectionRecord = new CollectionRecord();
        try {
            String argument = (String) request.getData().get("argument"); // key of the studyGroup
            StudyGroup studyGroup = (StudyGroup) request.getData().get("object"); // studyGroup to add

            // generate birthday
            LocalDateTime date = LocalDateTime.now().minusYears(random.nextInt(3) - 17);

            if (studyGroup.getGroupAdmin() != null) {
                studyGroup.getGroupAdmin().setBirthday(date);
            }

            String checkKey = "SELECT * FROM groups WHERE key = ? and owner_id = ?";
            String insertGroupQuery = "INSERT INTO groups "
                    + "(key, name, x, y, creation_date, students_count, expelled_students, "
                    + "transferred_students, semester, pers_id, owner_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, (SELECT id FROM person WHERE name = ? AND birthday = ? AND passportID = ? AND hairColor = ?), ?) "
                    + "RETURNING id";

            DatabaseManager databaseManager = new DatabaseManager();
            Connection connection = databaseManager.getConnection();

            PreparedStatement checkStatement = connection.prepareStatement(checkKey);
            checkStatement.setString(1, argument);
            checkStatement.setString(2, userId);
            ResultSet resultCheckSet = checkStatement.executeQuery();
            // check if key already present
            if (resultCheckSet.next()) {
                return new Response(false, "Key already exists", null);
            }

            PreparedStatement preparedStatement = connection.prepareStatement(insertGroupQuery);
            preparedStatement.setString(1, argument);
            preparedStatement.setString(2, studyGroup.getName());
            preparedStatement.setInt(3, studyGroup.getCoordinates().getX());
            preparedStatement.setInt(4, studyGroup.getCoordinates().getY());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(studyGroup.getCreationDate()));
            if (studyGroup.getStudentsCount() != null) {
                preparedStatement.setLong(6, studyGroup.getStudentsCount());
            } else {
                preparedStatement.setNull(6, java.sql.Types.BIGINT);
            }
            preparedStatement.setLong(7, studyGroup.getExpelledStudents());
            preparedStatement.setLong(8, studyGroup.getTransferredStudents());
            if (studyGroup.getSemesterEnum() != null) {
                preparedStatement.setString(9, studyGroup.getSemesterEnum().toString());
            } else {
                preparedStatement.setNull(9, java.sql.Types.VARCHAR);
            }

            if (studyGroup.getGroupAdmin() != null) {
                String insertPersonQuery = "INSERT INTO person "
                        + "(name, birthday, passportID, hairColor) "
                        + "SELECT ?, ?, ?, ? "
                        + "WHERE NOT EXISTS "
                        + "(SELECT 1 FROM person WHERE name = ? AND birthday = ? AND passportID = ? AND hairColor = ?)";

                PreparedStatement personStatement = connection.prepareStatement(insertPersonQuery);
                personStatement.setString(1, studyGroup.getGroupAdmin().getName());
                personStatement.setTimestamp(2, Timestamp.valueOf(studyGroup.getGroupAdmin().getBirthday()));
                personStatement.setString(3, studyGroup.getGroupAdmin().getPassportID());
                personStatement.setString(4, studyGroup.getGroupAdmin().getHairColor().toString());
                personStatement.setString(5, studyGroup.getGroupAdmin().getName());
                personStatement.setTimestamp(6, Timestamp.valueOf(studyGroup.getGroupAdmin().getBirthday()));
                personStatement.setString(7, studyGroup.getGroupAdmin().getPassportID());
                personStatement.setString(8, studyGroup.getGroupAdmin().getHairColor().toString());
                personStatement.executeUpdate();

                preparedStatement.setString(10, studyGroup.getGroupAdmin().getName());
                preparedStatement.setTimestamp(11, Timestamp.valueOf(studyGroup.getGroupAdmin().getBirthday()));
                preparedStatement.setString(12, studyGroup.getGroupAdmin().getPassportID());
                preparedStatement.setString(13, studyGroup.getGroupAdmin().getHairColor().toString());
            } else {
                preparedStatement.setNull(10, java.sql.Types.VARCHAR);
                preparedStatement.setNull(11, java.sql.Types.TIMESTAMP);
                preparedStatement.setNull(12, java.sql.Types.VARCHAR);
                preparedStatement.setNull(13, java.sql.Types.VARCHAR);
            }
            preparedStatement.setString(14, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                studyGroup.setId(id);
            }

            HashMap<String, Object> data = new HashMap<>();
            studyGroup.setOwner(userId);
            collectionRecord.getCollection().put(argument, studyGroup);
            data.put("object", collectionRecord);

            return new Response(true, "StudyGroup added successfully", data);
        } catch (SQLException e) {
            return new Response(false, e.getMessage(), null);
        } catch (IOException e) {
            return new Response(false, e.getMessage(), null);
        }
    }
}
