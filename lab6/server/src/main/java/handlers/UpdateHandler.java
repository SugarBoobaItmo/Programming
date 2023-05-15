package handlers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.naming.spi.DirStateFactory.Result;

import database.DatabaseManager;
import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import handlers.exceptions.ServerStorageException;
import javafx.scene.chart.PieChart.Data;
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
        Random random = new Random();

        CollectionRecord collectionRecord = new CollectionRecord();

        // collectionRecord = CollectionStorage.load(userId);
        DatabaseManager databaseManager = new DatabaseManager();

        try {
            String argument = (String) request.getData().get("argument");
            StudyGroup studyGroup = (StudyGroup) request.getData().get("object");
            // System.out.println(studyGroup.getId());
            // String id = (String) request.getData().get("id");
            // generate random id and birthday
            // studyGroup.setId(random.nextInt(1000000));
            // LocalDateTime date = LocalDateTime.now().minusYears(random.nextInt(3) - 17);

            // if (studyGroup.getGroupAdmin() != null) {
            //     studyGroup.getGroupAdmin().setBirthday(date);
            // }

            String sql = "UPDATE groups SET name = ?, x = ?, y = ?, creation_date = ?, students_count = ?, expelled_students = ?, transferred_students = ?, semester = ?, pers_id = (SELECT id FROM person WHERE name = ? AND birthday = ? AND passportID = ? AND hairColor = ?) WHERE id = ? AND owner_id = ?";
            // String sqlPerson = 
            // insert into person if it doesn't exist
            // String sqlPerson = "INSERT INTO person (name, birthday, passportID, hairColor) VALUES (?, ?, ?, ?) ON CONFLICT (name, birthday, passportID, hairColor) DO NOTHING";
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
            // preparedStatement.setString(1, argument);
            // preparedStatement.setString(1, argument);
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

            // ResultSet resultSet = preparedStatement.executeQuery();
            // preparedStatement.executeUpdate();
            // ResultSet resultSet = preparedStatement.executeUpdate();
            // ResultSet resultSet = preparedStatement.executeQuery();
            // preparedStatement.executeUpdate();
            // ResultSet resultSet = preparedStatement.getResultSet();
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0 ) {
                HashMap<String, Object> data = new HashMap<>();
                data.put("object", collectionRecord);
                return new Response(true, "StudyGroup updated successfully", data);

            }
            HashMap<String, Object> data = new HashMap<>();
            data.put("object", collectionRecord);
            return new Response(false, "StudyGroup with id: \"" + argument + "\" not found or you have no permission to upgrade it", data);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }

        // check if id exists
        // for (Map.Entry<String, StudyGroup> entry :
        // collectionRecord.getCollection().entrySet()) {
        // if (entry.getValue().getId() == Integer.parseInt(argument)) {

        // collectionRecord.getCollection().put(entry.getKey(), studyGroup);

        // HashMap<String, Object> data = new HashMap<>();
        // data.put("object", collectionRecord);

        // return new Response(true, "StudyGroup updated successfully", data);

        // }
        // }

        // if key doesn't exist return error
        // HashMap<String, Object> data = new HashMap<>();
        // data.put("object", collectionRecord);

        // return new Response(false, "StudyGroup with id: \"" + argument + "\" not
        // found", data);

    }

}
