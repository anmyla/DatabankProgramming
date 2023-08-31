import models.KlausurProjektaufgabe;
import models.KlausurProjekte;

import java.sql.*;
import java.util.ArrayList;

public class DBPKlausurHelper {
    //Location of the DB
    private static String url = "jdbc:sqlite:src/database/DBPKlausurAriagaNagl.db";

    //New instance of Connection Object (part of java.sql Package)
    private static Connection connection = null;

    public DBPKlausurHelper(String databasePath) {
    }

    public static void connect(String databasePath) {
        try {
            String url = "jdbc:sqlite:" + databasePath;
            connection = DriverManager.getConnection(url);

            ////Aufgabe 2d
            // Enable foreign key support
            connection.createStatement().execute("PRAGMA foreign_keys = ON;");

            System.out.println("Connection to the database has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("\nConnection to the database is now closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement preparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    public static boolean tableExists(String tableName) {
        try {
            String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'";
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            return resultSet != null && resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void createDatabase() {
        // Check if the table already exists because if tables exist it means, the DB has been created
        boolean tableExists1 = tableExists("KlausurProjekte");
        boolean tableExists2 = tableExists("KlausurProjektaufgaben");

        //2b. Create tables
        if (!tableExists1 && !tableExists2) {
            createTableKlausurProjekte();
            createTableKlausurProjektaufgaben();
        } else {
            System.out.println("\nDatabase, DBPKlausurAriagaNagl, already exists.");
        }
    }


    //Aufgabe 2a
    public static void createTableKlausurProjekte() {
// try (Connection conn = DriverManager.getConnection(url)) { -->> I removed this line because I want to have a ONE single connection for all my methods
        try {
            String ddlCreateProjekte = "CREATE TABLE KlausurProjekte ( " +
                    "    ProjektID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "    Projektbezeichnung varchar(50), " +
                    "    Projekttyp varchar(50), " +
                    "    Budget double(10,2), " +
                    "    Laufzeit int " +
                    ")";

            Statement ddlCreateProjekteStmt = connection.createStatement();
            ddlCreateProjekteStmt.execute(ddlCreateProjekte);
            System.out.println("Table KlausurProjekte successfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Aufgabe 2b
    private int getLastInsertId() throws SQLException {
        // After inserting a new row into an AUTOINCREMENT column, you can retrieve the last assigned
        // AUTOINCREMENT value using the "last_insert_rowid()" function provided by SQLite.
        // This will give you the value of the last inserted AUTOINCREMENT ID in the table.
        String readLastId = "SELECT last_insert_rowid() as rowid ";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(readLastId);
        rs.next();
        int id = rs.getInt("rowid");
        return id;
    }

    //Aufgabe 2c
    public static void createTableKlausurProjektaufgaben() {

        try {
            String ddlCreateProjektaufgaben = "CREATE TABLE KlausurProjektaufgaben ( " +
                    "    ProjektAufgabeID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "    ProjektID INTEGER, " +
                    "    Aufgabenbezeichnung varchar(50), " +
                    "    AufwandInStunden int, " +
                    "    FOREIGN KEY (ProjektID) REFERENCES KlausurProjekte(ProjektID) " +
                    ")";

            Statement ddlCreateProjektaufgabenStmt = connection.createStatement();
            ddlCreateProjektaufgabenStmt.execute(ddlCreateProjektaufgaben);
            System.out.println("Table KlausurProjektaufgaben successfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Aufgabe 2d
    /*  We do the following for SQLite to effectively monitor and enforce foreign key constraints
        1. Define Constraints: Use FOREIGN KEY during table creation, referencing primary keys.
        2. Enable foreign key support: Set foreign_keys pragma to ON: connection.createStatement().execute("PRAGMA foreign_keys = ON)"
        3. Committing changes only if no violation is detected -> (connection.commit()) but setAutoCommit(false) beforehand;
        4. Violation Handling: Raise exception on constraint violation, ROLL BACK operation (connection.rollback();)
        5. Transactions: Wrap changes in transactions for atomicity; rollback if constraints violated.
    */

    //This method is to insert data into the KlausurProjekte table
    public void insertIntoKlausurProjekte(String projektbezeichnung, String projekttyp, double budget, int laufzeit) {
        KlausurProjekte p = new KlausurProjekte();
        String insertQuery = "INSERT INTO KlausurProjekte (Projektbezeichnung, Projekttyp, Budget, Laufzeit) " +
                "VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement insertToKP = preparedStatement(insertQuery);
            insertToKP.setString(1, projektbezeichnung);
            insertToKP.setString(2, projekttyp);
            insertToKP.setDouble(3, budget);
            insertToKP.setInt(4, laufzeit);

            insertToKP.executeUpdate();

            //retrieve last assigned Autoincrement value.
            int id = getLastInsertId();
            p.setProjektId(id);

            System.out.println("Data inserted into KlausurProjekte");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    ////This method is to insert data into the KlausurProjektaufgaben table
    public void insertIntoKlausurProjektaufgaben(int projektID, String aufgabenbezeichnung, int aufwandInStunden) {
        KlausurProjektaufgabe pa = new KlausurProjektaufgabe();
        String insertQuery = "INSERT INTO KlausurProjektaufgaben (ProjektID, Aufgabenbezeichnung, AufwandInStunden) " +
                "VALUES (?, ?, ?)";

        try {
            PreparedStatement insertToKPA = preparedStatement(insertQuery);
            insertToKPA.setInt(1, projektID);
            insertToKPA.setString(2, aufgabenbezeichnung);
            insertToKPA.setInt(3, aufwandInStunden);

            insertToKPA.executeUpdate();

            //retrieve last assigned Autoincrement value.
            int id = getLastInsertId();
            pa.setProjektAufgabeID(id);

            System.out.println("Data inserted into KlausurProjektaufgaben");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    //Aufgabe 3
    public KlausurProjekte insertProjekt(KlausurProjekte neuesProjekt) {
        String insertQuery = "INSERT INTO KlausurProjekte (Projektbezeichnung, Projekttyp, Budget, Laufzeit) " +
                "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement insertToKP = preparedStatement(insertQuery);
            insertToKP.setString(1, neuesProjekt.getProjektbezeichnung());
            insertToKP.setString(2, neuesProjekt.getProjekttyp());
            insertToKP.setDouble(3, neuesProjekt.getBudget());
            insertToKP.setInt(4, neuesProjekt.getLaufzeit());

            insertToKP.executeUpdate();

            // Retrieve last assigned Autoincrement value
            int id = getLastInsertId();
            neuesProjekt.setProjektId(id);

            System.out.println("New Project inserted into KlausurProjekte");
            return neuesProjekt;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //Aufgabe 4a
    public KlausurProjekte getProjektById(int projektId) {
        KlausurProjekte projekt = new KlausurProjekte();
        String selectQuery = "SELECT * FROM KlausurProjekte WHERE ProjektID = ?";

        try {
            PreparedStatement selectProjekt = preparedStatement(selectQuery);
            selectProjekt.setInt(1, projektId);

            ResultSet resultSet = selectProjekt.executeQuery();

            if (resultSet.next()) {
                projekt.setProjektId(resultSet.getInt("ProjektID"));
                projekt.setProjektbezeichnung(resultSet.getString("Projektbezeichnung"));
                projekt.setProjekttyp(resultSet.getString("Projekttyp"));
                projekt.setBudget(resultSet.getDouble("Budget"));
                projekt.setLaufzeit(resultSet.getInt("Laufzeit"));

                System.out.println(projekt);

            } else {
                System.out.println("Project with ProjektID " + projektId + " is not found.");
                return null;
            }

            if (projekt.getProjekttyp() == null) {
                System.out.println("Warning: Projekttyp is NULL.");
            }
            return projekt;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //Aufgabe 4b
    public ArrayList<KlausurProjekte> getAlleProjekteSortedByProjekttypFilteredByMaxLaufzeit(int maxLaufzeit) {
        ArrayList<KlausurProjekte> projekteList = new ArrayList<>();
        String selectQuery = "SELECT * FROM KlausurProjekte WHERE Laufzeit <= ? ORDER BY Projekttyp ASC";

        try {
            PreparedStatement selectProjects = preparedStatement(selectQuery);
            selectProjects.setInt(1, maxLaufzeit);

            ResultSet resultSet = selectProjects.executeQuery();

            while (resultSet.next()) {
                KlausurProjekte project = new KlausurProjekte();
                project.setProjektId(resultSet.getInt("ProjektID"));
                project.setProjektbezeichnung(resultSet.getString("Projektbezeichnung"));
                project.setProjekttyp(resultSet.getString("Projekttyp"));
                project.setBudget(resultSet.getDouble("Budget"));
                project.setLaufzeit(resultSet.getInt("Laufzeit"));

                projekteList.add(project);
            }

            for (KlausurProjekte projekt : projekteList) {
                System.out.println(projekt);
            }

            return projekteList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //Aufgabe 5a
    public KlausurProjektaufgabe insertProjektaufgabe(KlausurProjektaufgabe aufgabe) {
        String insertQuery = "INSERT INTO KlausurProjektaufgaben (ProjektID, Aufgabenbezeichnung, AufwandInStunden) " +
                "VALUES (?, ?, ?)";

        try {
            // Start a transaction
            connection.setAutoCommit(false);

            // Check if the provided ProjektID exists in KlausurProjekte
            String checkProjektExistsQuery = "SELECT COUNT(*) FROM KlausurProjekte WHERE ProjektID = ?";
            PreparedStatement checkProjektExists = preparedStatement(checkProjektExistsQuery);


            checkProjektExists.setInt(1, aufgabe.getProjektID());
            ResultSet result = checkProjektExists.executeQuery();

            if (!result.next() || result.getInt(1) == 0) {
                System.out.println("Project with ID " + aufgabe.getProjektID() + " does not exist.");
                connection.rollback();
                return null;
            }

            // Insert the project task
            PreparedStatement insertThisAufgabe = preparedStatement(insertQuery);
            insertThisAufgabe.setInt(1, aufgabe.getProjektID());
            insertThisAufgabe.setString(2, aufgabe.getAufgabenbezeichnung());
            insertThisAufgabe.setInt(3, aufgabe.getAufwandInStunden());

            // Retrieve last assigned Autoincrement value an assign value to row/Object
            int id = getLastInsertId();
            aufgabe.setProjektAufgabeID(id);

            int affectedRows = insertThisAufgabe.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting project task failed, no rows affected.");
            }

            // Commit the transaction
            connection.commit();
            System.out.println("Project task inserted with ID: " + aufgabe.getProjektAufgabeID());
            return aufgabe;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                connection.rollback(); // ROLL BACK on constraint violation
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
            return null;
        } finally {
            try {
                // Set auto-commit back to true
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Setting auto-commit to true failed: " + ex.getMessage());
            }
        }
    }

    // Aufgabe 6a
    public ArrayList<KlausurProjekte> getProjektMitDenMeistenAufgaben() {
        ArrayList<KlausurProjekte> projectsWithMostTasks = new ArrayList<>();
        String selectQuery = "SELECT p.ProjektID, p.Projektbezeichnung, p.Projekttyp, p.Budget, p.Laufzeit, " +
                "(SELECT COUNT(ProjektAufgabeID) FROM KlausurProjektaufgaben WHERE ProjektID = p.ProjektID) AS TaskCount " +
                "FROM KlausurProjekte p " +
                "WHERE (SELECT COUNT(ProjektAufgabeID) FROM KlausurProjektaufgaben WHERE ProjektID = p.ProjektID) = " +
                "(SELECT MAX(TaskCount) FROM (SELECT ProjektID, COUNT(ProjektAufgabeID) AS TaskCount FROM KlausurProjektaufgaben GROUP BY ProjektID))";

        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(selectQuery);

            int taskCount = 0;
            while (resultSet.next()) {
                KlausurProjekte project = new KlausurProjekte();
                project.setProjektId(resultSet.getInt("ProjektID"));
                project.setProjektbezeichnung(resultSet.getString("Projektbezeichnung"));
                project.setProjekttyp(resultSet.getString("Projekttyp"));
                project.setBudget(resultSet.getDouble("Budget"));
                project.setLaufzeit(resultSet.getInt("Laufzeit"));

                taskCount = resultSet.getInt("TaskCount");

                projectsWithMostTasks.add(project);
            }

            for (KlausurProjekte project : projectsWithMostTasks) {
                System.out.println(project + " Anzahl Aufgaben: " + taskCount);
            }

            return projectsWithMostTasks;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    // Aufgabe 6c
    public double getDurchschnittAufwandInStundenByProjektId(int projektId) {
        String selectQuery = "SELECT AVG(AufwandInStunden) AS AverageEffort " +
                "FROM KlausurProjektaufgaben " +
                "WHERE ProjektID = ?";

        try {
            PreparedStatement selectAverageEffort = preparedStatement(selectQuery);
            selectAverageEffort.setInt(1, projektId);

            ResultSet resultSet = selectAverageEffort.executeQuery();

            if (resultSet.next()) {
                double averageEffort = resultSet.getDouble("AverageEffort");
                System.out.println("Average effort for ProjektID " + projektId + ": " + averageEffort);
                return averageEffort;
            } else {
                System.out.println("No data found for ProjektID " + projektId);
                return 0.0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0.0;
        }
    }

    //Aufgabe 7
    public void printTablenames() {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table found: " + tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Aufgabe 8
    public void createAdditionalTableAndFillWithSampleValues() {
        boolean tableExists = tableExists("ProjectLocation");

        try {
            Statement statement = connection.createStatement();

            if (!tableExists) {
                // Create the additional table
                String createTableQuery = "CREATE TABLE ProjectLocation ( " +
                        "    ProjectLocationID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "    ProjectID INTEGER, " +
                        "    ProjektAufgabeID INTEGER, " +
                        "    Location varchar(100), " +
                        "    PersonInCharge varchar(100), " +
                        "    FOREIGN KEY (ProjectID) REFERENCES KlausurProjekte(ProjektID), " +
                        "    FOREIGN KEY (ProjektAufgabeID) REFERENCES KlausurProjektaufgaben(ProjektAufgabeID) " +
                        ")";
                statement.execute(createTableQuery);
                System.out.println("Project Location Table is created successfully.");


                // Insert sample records
                String insertSampleRecordsQuery = "INSERT INTO ProjectLocation (ProjectID, ProjektAufgabeID, Location, PersonInCharge) " +
                        "VALUES (?, ?, ?, ?)";
                PreparedStatement insertSampleRecords = preparedStatement(insertSampleRecordsQuery);

                // Insert records with ProjectID and ProjektAufgabeID from existing projects and tasks
                insertSampleRecords.setInt(1, 5);
                insertSampleRecords.setInt(2, 1);
                insertSampleRecords.setString(3, "Graz");
                insertSampleRecords.setString(4, "King Kong");
                insertSampleRecords.executeUpdate();

                insertSampleRecords.setInt(1, 1);
                insertSampleRecords.setInt(2, 3);
                insertSampleRecords.setString(3, "Maribor");
                insertSampleRecords.setString(4, "Ding Dong");
                insertSampleRecords.executeUpdate();

                System.out.println("Records are successfully inserted into Project Location Table");

            } else {
                System.out.println("This table already exists!");
            }

        } catch (SQLException e) {
            System.out.println("Error creating additional table! " + e.getMessage());
        }
    }

    //Aufgabe 9
    public void transferBudget(int oldProjektId, int newProjektId, double budget) {
        try {
            // Start a transaction
            connection.setAutoCommit(false);

            // Debit from the old project
            String debitQuery = "UPDATE KlausurProjekte SET Budget = Budget - ? WHERE ProjektID = ?";
            PreparedStatement debitStatement = preparedStatement(debitQuery);
            debitStatement.setDouble(1, budget);
            debitStatement.setInt(2, oldProjektId);
            int debitAffectedRows = debitStatement.executeUpdate();

            if (debitAffectedRows == 0) {
                throw new SQLException("Debit operation failed, no rows affected.");
            }

            // Credit to the new project
            String creditQuery = "UPDATE KlausurProjekte SET Budget = Budget + ? WHERE ProjektID = ?";
            PreparedStatement creditStatement = preparedStatement(creditQuery);
            creditStatement.setDouble(1, budget);
            creditStatement.setInt(2, newProjektId);
            int creditAffectedRows = creditStatement.executeUpdate();

            if (creditAffectedRows == 0) {
                throw new SQLException("Credit operation failed, no rows affected.");
            }

            // Commit the transaction
            connection.commit();
            System.out.println("Budget transferred successfully.");

        } catch (SQLException e) {
            System.out.println("Error transferring budget: " + e.getMessage());
            try {
                // ROLL BACK operation on constrain violation/failure
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Rollback failed: " + ex.getMessage());
            }
        } finally {
            try {
                // Set auto-commit back to true
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Setting auto-commit to true failed: " + ex.getMessage());
            }
        }
    }


    //Extra method to print contents of my tables on the console -> using DatabaseMetaData metadata.getColumns();
    public void printTableContents(String tableName) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, tableName, null);

            ArrayList<String> content = new ArrayList<>();
            while (resultSet.next()) {
                content.add(resultSet.getString("COLUMN_NAME"));
            }

            String selectQuery = "SELECT * FROM " + tableName;

            Statement stmt = connection.createStatement();
            ResultSet dataResultSet = stmt.executeQuery(selectQuery);

            System.out.println("Table: " + tableName);

            while (dataResultSet.next()) {
                for (String columnName : content) {
                    System.out.print(columnName + ": " + dataResultSet.getString(columnName) + "\t \t");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
