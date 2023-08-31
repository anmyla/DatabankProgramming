import models.KlausurProjektaufgabe;
import models.KlausurProjekte;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        //two lines are just for display purposes
        String SUNNY = "\u001B[38;2;204;204;102m";
        String RESET = "\u001B[0m";

        //-------------------------------------------------------------------------------
        String databasePath = "src/database/DBPKlausurAriagaNagl.db";
        DBPKlausurHelper helper = new DBPKlausurHelper(databasePath);

        System.out.println(SUNNY + "\nEstablish a connection:" + RESET);
        helper.connect(databasePath);

        //Aufgabe 2, 2a
        System.out.println(SUNNY + "\nCreate DB and tables:" + RESET);
        helper.createDatabase();


        System.out.println(SUNNY + "\nPopulate the KlausurProjekte Table" + RESET);
        helper.insertIntoKlausurProjekte("Requirement def. UNOProject", "Software", 1000, 3);
        helper.insertIntoKlausurProjekte("Writing Java Code for UNO game", "Software", 5000, 30);
        helper.insertIntoKlausurProjekte("Testing the Java Code UNOProject", "Software", 2000, 15);
        helper.insertIntoKlausurProjekte("Documentation for UNOProject", "Software", 2000, 10);
        helper.insertIntoKlausurProjekte("Physical Office setup", "Construction", 20000, 10);
        helper.insertIntoKlausurProjekte("Hiring additional programmers", "HR", 500, 5);

        System.out.println(SUNNY + "\nPopulate the KlausurProjektaufgabe table"+ RESET);
        helper.insertIntoKlausurProjektaufgaben(5, "Purchase of hardware", 20);
        helper.insertIntoKlausurProjektaufgaben(5, "Purchase of office supplies", 8);
        helper.insertIntoKlausurProjektaufgaben(1, "Meeting with clients", 5);
        helper.insertIntoKlausurProjektaufgaben(2, "Writing pseude code for uno project", 5);
        helper.insertIntoKlausurProjektaufgaben(3, "Writing first test reports", 3);
        helper.insertIntoKlausurProjektaufgaben(4, "Proofreading documentation", 8);
        helper.insertIntoKlausurProjektaufgaben(6, "posts to jobsites", 5);


        System.out.println(SUNNY + "\n------PRINT TABLES AND THEIR CONTENTS--------" + RESET);
        helper.printTableContents("KlausurProjekte");
        System.out.println();
        helper.printTableContents("KlausurProjektaufgaben");

        System.out.println(SUNNY + "\n----EXECUTION OF REQUIRED METHODS----" + RESET);

        //Aufgabe3
        System.out.println(SUNNY + "\nInsert into KlausurProjekte table method: " + RESET);
        KlausurProjekte p = new KlausurProjekte("Office relocation", null, 1000, 8);
        helper.insertProjekt(p);
        System.out.println(p);

        //Aufgabe 4a
        System.out.println(SUNNY + "\nGet Project by ID: " + RESET);
        helper.getProjektById(4);
        System.out.println("When ProjectType is null: ");
        helper.getProjektById(7);

        //Aufgabe 4b
        System.out.println(SUNNY + "\nGet all Project sorted by project type filtered by Max Laufzeit: " + RESET);
        ArrayList<KlausurProjekte> list = helper.getAlleProjekteSortedByProjekttypFilteredByMaxLaufzeit(10);

        //Aufgabe 5a
        System.out.println(SUNNY + "\nInsert into KlausurProjekteaufgabe table method: " + RESET);
        KlausurProjektaufgabe pa = new KlausurProjektaufgabe(2, "Writing first prototype code", 40);
        helper.insertProjektaufgabe(pa);
        System.out.println(pa);

        //Aufgabe 6a
        System.out.println(SUNNY + "\nGet Project/s with the most tasks:" + RESET);
        helper.getProjektMitDenMeistenAufgaben();

        //Aufgabe 6c
        System.out.println(SUNNY + "\nAVG AufwandInStunden By Project ID:" + RESET);
        helper.getDurchschnittAufwandInStundenByProjektId(5);

        //Aufgabe 7
        System.out.println(SUNNY + "\nMetadata: Print Table Names" + RESET);
        helper.printTablenames();

        //Aufgabe 8
        System.out.println(SUNNY + "\nCreate additional Table and fill with sample values." + RESET);
        helper.createAdditionalTableAndFillWithSampleValues();
        System.out.println(SUNNY + "\nRe-print table names after additional table creation" + RESET);
        helper.printTablenames();
        System.out.println(SUNNY + "\nPrint Contents of the newly created table" + RESET);
        helper.printTableContents("ProjectLocation");

        //Aufgabe 9
        System.out.println(SUNNY + "\nTransfer Budget: " + RESET);
        System.out.println("----BEFORE TRANSFER: ");
        helper.getProjektById(5);
        helper.getProjektById(2);
        System.out.println("----AFTER TRANSFER: ");
        helper.transferBudget(5, 2, 200);
        helper.getProjektById(5);
        helper.getProjektById(2);
        System.out.println("----IF TRANSFER IS INVALID:");
        helper.transferBudget(5, 56, 200);

        //Close connection after all methods are executed before exiting the Program
        helper.closeConnection();
    }
}