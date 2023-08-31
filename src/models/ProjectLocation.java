package models;

public class ProjectLocation {
    private int projectLocationID;
    private int projectID;
    private int projektAufgabeID;
    private String location;
    private String personInCharge;

    public int getProjectLocationID() {
        return projectLocationID;
    }

    public void setProjectLocationID(int projectLocationID) {
        this.projectLocationID = projectLocationID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getProjektAufgabeID() {
        return projektAufgabeID;
    }

    public void setProjektAufgabeID(int projektAufgabeID) {
        this.projektAufgabeID = projektAufgabeID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    @Override
    public String toString() {
        return "ProjectLocation " +
                "ProjectLocationID: " + projectLocationID +
                " projectID: " + projectID +
                " projektAufgabeID: " + projektAufgabeID +
                " Location: " + location +
                " PersonInCharge: " + personInCharge;
    }

}
