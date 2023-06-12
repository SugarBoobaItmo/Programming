package handlers;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import durgaapi.Handler;
import durgaapi.Request;
import durgaapi.Response;
import fqme.connection.ConnectionManager;
import fqme.models.PersonModel;
import fqme.models.StudyGroupModel;
import fqme.view.View;
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
    public Response handle(Request request, String userId) throws Exception {
        Random random = new Random();

        CollectionRecord collectionRecord = CollectionStorage.getCollectionRecord();
        String argument = (String) request.getData().get("argument"); // key of the studyGroup
        StudyGroup studyGroup = (StudyGroup) request.getData().get("object"); // studyGroup to add

        // generate birthday
        LocalDateTime date = LocalDateTime.now().minusYears(random.nextInt(3) - 17);

        if (studyGroup.getGroupAdmin() != null) {
            studyGroup.getGroupAdmin().setBirthday(date);
        }

        try (Connection connection = ConnectionManager.getConnection(StudyGroupModel.class)) {

            View<PersonModel> personView = View.of(PersonModel.class, connection);
            View<StudyGroupModel> groupView = View.of(StudyGroupModel.class, connection);

            // check if key already present
            Set<StudyGroupModel> checkSet = groupView
                    .get(StudyGroupModel.key_.eq(argument).and(StudyGroupModel.ownerId_.eq(Integer.parseInt(userId))));
            if (!checkSet.isEmpty()) {
                return new Response(false, "Key already exists", null);
            }

            // insert new person to DB
            PersonModel personModel;
            Integer adminId;

            if (studyGroup.getGroupAdmin() != null) {
                Set<PersonModel> checkPersonSet = personView
                        .get(PersonModel.name_.eq(studyGroup.getGroupAdmin().getName())
                        .and(PersonModel.passportID_.eq(studyGroup.getGroupAdmin().getPassportID()))
                        .and(PersonModel.hairColor_.eq(studyGroup.getGroupAdmin().getHairColor().toString())));
                if (checkPersonSet.isEmpty()) {
                    String name = studyGroup.getGroupAdmin().getName();
                    LocalDateTime birthday = studyGroup.getGroupAdmin().getBirthday();
                    String passportId = studyGroup.getGroupAdmin().getPassportID();
                    String hairColor = studyGroup.getGroupAdmin().getHairColor().toString();
                    personModel = new PersonModel(name, birthday, passportId, hairColor);

                    personView.put(personModel);

                    adminId = personView.get(PersonModel.name_.eq(studyGroup.getGroupAdmin().getName())
                            .and(PersonModel.birthday_.eq(studyGroup.getGroupAdmin().getBirthday()))
                            .and(PersonModel.passportID_.eq(studyGroup.getGroupAdmin().getPassportID()))
                            .and(PersonModel.hairColor_.eq(studyGroup.getGroupAdmin().getHairColor().toString())))
                            .iterator().next()
                            .getId();
                } else {
                    adminId = checkPersonSet.iterator().next().getId();
                }

            } else {
                adminId = null;
            }

            // insert new group to DB
            StudyGroupModel groupModel;
            String key = argument;
            String name = studyGroup.getName();
            Integer x = studyGroup.getCoordinates().getX();
            Integer y = studyGroup.getCoordinates().getY();
            LocalDateTime creationDate = studyGroup.getCreationDate();
            Long studentsCount = studyGroup.getStudentsCount();
            Long expelledStudents = studyGroup.getExpelledStudents();
            Long transferredStudents = studyGroup.getTransferredStudents();
            String semester = studyGroup.getSemesterEnum() == null ? null : studyGroup.getSemesterEnum().toString();
            Integer ownerId = Integer.parseInt(userId);

            groupModel = new StudyGroupModel(null, key, name, x, y, creationDate, studentsCount, expelledStudents,
                    transferredStudents, semester, adminId, ownerId);

            groupView.put(groupModel);

            HashMap<String, Object> data = new HashMap<>();
            studyGroup.setOwner((userId));

            synchronized (collectionRecord) {
                collectionRecord.getCollection().put(argument, studyGroup);
            }

            data.put("object", collectionRecord);
            return new Response(true, "StudyGroup added successfully", data);
        }
    }
}
