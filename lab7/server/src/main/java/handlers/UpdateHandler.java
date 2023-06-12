package handlers;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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

public class UpdateHandler extends Handler {
    private String name = "update";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Response handle(Request request, String userId) throws Exception {

        CollectionRecord collectionRecord = CollectionStorage.getCollectionRecord();
        Integer argument =  Integer.parseInt(request.getData().get("argument").toString());
        StudyGroup studyGroup = (StudyGroup) request.getData().get("object");

        try (Connection connection = ConnectionManager.getConnection(StudyGroupModel.class)) {

            View<StudyGroupModel> groupView = View.of(StudyGroupModel.class, connection);
            View<PersonModel> personView = View.of(PersonModel.class, connection);

            // check if group with this id exists
            HashMap<String, Object> data = new HashMap<>();

            StudyGroupModel checkGroup = groupView.get(StudyGroupModel.id_.eq(argument)).iterator().next();
            if (checkGroup == null) {
                return new Response(false, "No group with this id found", data);
            } else if (checkGroup.getOwnerId() != Integer.parseInt(userId)) {
                return new Response(false, "You don't have permission to update this group", data);
            }

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

            String name = studyGroup.getName();
            Integer x = studyGroup.getCoordinates().getX();
            Integer y = studyGroup.getCoordinates().getY();
            LocalDateTime creationDate = studyGroup.getCreationDate();
            Long studentsCount = studyGroup.getStudentsCount();
            Long expelledStudents = studyGroup.getExpelledStudents();
            Long transferredStudents = studyGroup.getTransferredStudents();
            String semester = studyGroup.getSemesterEnum() == null ? null : studyGroup.getSemesterEnum().toString();
            Integer ownerId = Integer.parseInt(userId);

            StudyGroupModel updatedGroupModel = new StudyGroupModel(checkGroup.getId(), checkGroup.getKey(),
                    name, x, y, creationDate, studentsCount, expelledStudents, transferredStudents, semester,
                    adminId, ownerId);
            groupView.put(updatedGroupModel);

                synchronized (collectionRecord) {
                    Iterator<Map.Entry<String, StudyGroup>> iterator = collectionRecord.getCollection().entrySet()
                            .iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, StudyGroup> entry = iterator.next();
                        if (entry.getValue().getId() == argument) {
                            iterator.remove();
                            collectionRecord.getCollection().put(entry.getKey(), studyGroup);
                        }
                    }
                }

                data.put("object", collectionRecord);
                return new Response(true, "StudyGroup updated successfully", data);
            
        }
    }

}
