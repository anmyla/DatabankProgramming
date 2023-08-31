package at.dbp.klausur;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBPKlausurHelper {
    private String url = "jdbc:sqlite:C:\\LVs\\DBP2023\\DBPKlausurIhrName.db";

    //Aufgabe 2a
    public void createTableKlausurProjekte(){

        try (Connection conn = DriverManager.getConnection(url)) {

            String ddlCreateProjekte="CREATE TABLE KlausurProjekte\n" +
                    "(\n" +
                    "    ProjektID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    Projektbezeichnung varchar(50), " +
                    "    Projekttyp varchar(50),\n" +
                    "    Budget double(10,2),\n" +
                    "    Laufzeit int\n" +
                    ")";

            Statement ddlCreateProjekteStmt = conn.createStatement();
            ddlCreateProjekteStmt.execute(ddlCreateProjekte);
            System.out.println("Table KlausurProjekte succesfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTableKlausurProjektaufgaben(){

    }
}
