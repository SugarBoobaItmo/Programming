package handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

import database.DatabaseManager;
import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import models.CollectionRecord;
import models.StudyGroup;

public class UpdateHandler extends Handler {
    private String name = "update";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Response handle(Request request, String userId) {
        CollectionRecord collectionRecord = new CollectionRecord();

        DatabaseManager databaseManager = new DatabaseManager();
        try {
            String argument = (String) request.getData().get("argument");
            StudyGroup studyGroup = (StudyGroup) request.getData().get("object");

            String sql = "UPDATE groups SET name = ?, x = ?, y = ?, creation_date = ?, students_count = ?, expelled_students = ?, transferred_students = ?, semester = ?, pers_id = (SELECT id FROM person WHERE name = ? AND birthday = ? AND passportID = ? AND hairColor = ?) WHERE id = ? AND owner_id = ?";
            String sqlPerson = "INSERT INTO person (name, birthday, passportID, hairColor) SELECT ?, ?, ?, ? WHERE NOT EXISTS (SELECT 1 FROM person WHERE name = ? AND birthday = ? AND passportID = ? AND hairColor = ?)";

            Connection connection = databaseManager.getConnection();
            if (studyGroup.getGroupAdmin() != null) {
                PreparedStatement personStatement = connection.prepareStatement(sqlPerson);
                personStatement.setString(1, studyGroup.getGroupAdmin().getName());
                personStatement.setTimestamp(2, Timestamp.valueOf(studyGroup.getGroupAdmin().getBirthday()));
                personStatement.setString(3, studyGroup.getGroupAdmin().getPassportID());
                personStatement.setString(4, studyGroup.getGroupAdmin().getHairColor().toString());
                personStatement.setString(5, studyGroup.getGroupAdmin().getName());
                personStatement.setTimestamp(6, Timestamp.valueOf(studyGroup.getGroupAdmin().getBirthday()));
                personStatement.setString(7, studyGroup.getGroupAdmin().getPassportID());
                personStatement.setString(8, studyGroup.getGroupAdmin().getHairColor().toString());
                personStatement.executeUpdate();
            }

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, studyGroup.getName());
            preparedStatement.setInt(2, studyGroup.getCoordinates().getX());
            preparedStatement.setInt(3, studyGroup.getCoordinates().getY());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(studyGroup.getCreationDate()));
            if (studyGroup.getStudentsCount() != null) {
                preparedStatement.setLong(5, studyGroup.getStudentsCount());
            } else {
                preparedStatement.setNull(5, java.sql.Types.BIGINT);
            }
            preparedStatement.setLong(6, studyGroup.getExpelledStudents());
            preparedStatement.setLong(7, studyGroup.getTransferredStudents());
            if (studyGroup.getSemesterEnum() != null) {
                preparedStatement.setString(8, studyGroup.getSemesterEnum().toString());
            } else {
                preparedStatement.setNull(8, java.sql.Types.VARCHAR);
            }

            if (studyGroup.getGroupAdmin() != null) {
                preparedStatement.setString(9, studyGroup.getGroupAdmin().getName());
                preparedStatement.setTimestamp(10, Timestamp.valueOf(studyGroup.getGroupAdmin().getBirthday()));
                preparedStatement.setString(11, studyGroup.getGroupAdmin().getPassportID());
                preparedStatement.setString(12, studyGroup.getGroupAdmin().getHairColor().toString());
            } else {
                preparedStatement.setNull(9, java.sql.Types.VARCHAR);
                preparedStatement.setNull(10, java.sql.Types.TIMESTAMP);
                preparedStatement.setNull(11, java.sql.Types.VARCHAR);
                preparedStatement.setNull(12, java.sql.Types.VARCHAR);
            }
            preparedStatement.setInt(13, Integer.parseInt(argument));
            preparedStatement.setString(14, userId);
            System.out.println(preparedStatement.toString());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("object", collectionRecord);
                return new Response(true, "StudyGroup updated successfully", data);

            }
            HashMap<String, Object> data = new HashMap<>();
            data.put("object", collectionRecord);
            return new Response(false,
                    "StudyGroup with id: \"" + argument + "\" not found or you have no permission to upgrade it", data);

        } catch (SQLException e) {
            return new Response(false, e.getMessage(), null);

        } catch (IOException e) {
            return new Response(false, e.getMessage(), null);
        }
    }
}
