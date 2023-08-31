package models;

public class KlausurProjektaufgaben {
    private int projektAufgabeID;
    private int projektID;
    private String aufgabenbezeichnung;
    private int aufwandInStunden;


    // Default constructor
    public KlausurProjektaufgaben() {

    }

    public KlausurProjektaufgaben(int projektAufgabeID, int projektID, String aufgabenbezeichnung, int aufwandInStunden) {
        this.projektAufgabeID = projektAufgabeID;
        this.projektID = projektID;
        this.aufgabenbezeichnung = aufgabenbezeichnung;
        this.aufwandInStunden = aufwandInStunden;
    }

    // Getters and Setters
    public int getProjektAufgabeID() {
        return projektAufgabeID;
    }

    public void setProjektAufgabeID(int projektAufgabeID) {
        this.projektAufgabeID = projektAufgabeID;
    }

    public int getProjektID() {
        return projektID;
    }

    public void setProjektID(int projektID) {
        this.projektID = projektID;
    }

    public String getAufgabenbezeichnung() {
        return aufgabenbezeichnung;
    }

    public void setAufgabenbezeichnung(String aufgabenbezeichnung) {
        this.aufgabenbezeichnung = aufgabenbezeichnung;
    }

    public int getAufwandInStunden() {
        return aufwandInStunden;
    }

    public void setAufwandInStunden(int aufwandInStunden) {
        this.aufwandInStunden = aufwandInStunden;
    }

    @Override
    public String toString() {
        return "KlausurProjektaufgabe [ " +
                "ProjektAufgabeID: " + projektAufgabeID +
                "ProjektID: " + projektID +
                "Aufgabenbezeichnung: " + aufgabenbezeichnung +
                "AufwandInStunden: " + aufwandInStunden +
                " ]";
    }
}
