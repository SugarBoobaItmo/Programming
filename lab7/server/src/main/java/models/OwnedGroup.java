package models;

public class OwnedGroup {
    private StudyGroup group;
    private String owner;

    public OwnedGroup(StudyGroup group, String owner) {
        this.group = group;
        this.owner = owner;
    }


    public StudyGroup getGroup() {
        return group;
    }

    public String getOwner() {
        return owner;
    }

    
}
