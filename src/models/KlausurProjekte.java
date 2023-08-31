package at.dbp.klausur.models;


public class KlausurProjekte {

    private int ProjektId;

    public int getProjektId() {
        return ProjektId;
    }

    public KlausurProjekte() {
    }

    public KlausurProjekte(int projektId) {
        ProjektId = projektId;
    }

    public KlausurProjekte(int projektId, String projektbezeichnung, String projekttyp, double budget, int laufzeit) {
        ProjektId = projektId;
        Projektbezeichnung = projektbezeichnung;
        Projekttyp = projekttyp;
        Budget = budget;
        Laufzeit = laufzeit;
    }

    @Override
    public String toString() {
        return "KlausurProjekte{" +
                "ProjektId=" + ProjektId +
                ", Projektbezeichnung='" + Projektbezeichnung + '\'' +
                ", Projekttyp='" + Projekttyp + '\'' +
                ", Budget=" + Budget +
                ", Laufzeit=" + Laufzeit +
                '}';
    }

    public void setProjektId(int projektId) {
        ProjektId = projektId;
    }

    public String getProjektbezeichnung() {
        return Projektbezeichnung;
    }

    public void setProjektbezeichnung(String projektbezeichnung) {
        Projektbezeichnung = projektbezeichnung;
    }

    public String getProjekttyp() {
        return Projekttyp;
    }

    public void setProjekttyp(String projekttyp) {
        Projekttyp = projekttyp;
    }

    public double getBudget() {
        return Budget;
    }

    public void setBudget(double budget) {
        Budget = budget;
    }

    public int getLaufzeit() {
        return Laufzeit;
    }

    public void setLaufzeit(int laufzeit) {
        Laufzeit = laufzeit;
    }

    private String Projektbezeichnung;
    private String Projekttyp;
    private double Budget;
    private int Laufzeit;
}
