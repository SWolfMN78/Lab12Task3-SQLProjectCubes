package com.Shawn;
import java.sql.*;
import java.util.concurrent.SynchronousQueue;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/";
    static final String DB_Name = "cubes";   //todo change?
    static final String USER = "root";
    static final String PASSWORD = "YOUR PASSWORD WAS HERE";  //todo change

    static Statement statement = null;
    static Connection conn = null;
    static ResultSet rs = null;

    public static void main(String[] args) {
        // Write an application that creates a table called “cubes”, and allows the user to add, adjust and delete records.
        setup();
        setTable();
    }

    public static void setTable(){
        try{
            String getAllData = "SELECT * FROM cubes"; //pull the information from SQL
            rs = statement.executeQuery(getAllData); //push the information from SQL into the built resultset

            SQLModel sqlModel = new SQLModel(rs); //push the informatin from the resultset into the object(model) to be used.
            SQLGUI sqlgui = new SQLGUI(sqlModel); //start up the GUI
        }catch (Exception e){
            e.printStackTrace();
            shutdown();
        }
    }

    public static void setup(){
        //set up the connection to SQL to be used.
        try{
            try{
                String Driver = "com.mysql.jdbc.Driver"; //try and to open the driver to run sql.
                Class.forName(Driver);
            }catch (ClassNotFoundException cnfe){
                System.out.println("No database drivers found. Quitting");
                System.exit(-1);
            }

            //now that MySQL is open and running, lets start the connection to the DB with the user name & PW
            conn = DriverManager.getConnection(DB_CONNECTION_URL + DB_Name, USER, PASSWORD);
            statement = conn.createStatement();
            statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //create a new table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS cubes (Rubiks_Solver VARCHAR (40), " +
                    " Solve_Duration DOUBLE )";
            statement.executeUpdate(createTableSQL);
            System.out.println("Table: cubes is created.");

            //clear the table upon loading, then we'll load it with new info.
            String deleteSQLInfo = "Delete from cubes where 1 = 1";
            statement.executeUpdate(deleteSQLInfo);

            //Add data to the table
            String addDataSQL = "INSERT INTO cubes VALUES ('Cubestormer II robot', 5.270)";
            statement.executeUpdate(addDataSQL);

            addDataSQL = "INSERT INTO cubes VALUES ('Fakhri Raihaan (using his feet)', 27.93)";
            statement.executeUpdate(addDataSQL);

            addDataSQL = "Insert into cubes VALUES ('Ruxin Liu (age 3)', 99.33)";
            statement.executeUpdate(addDataSQL);

            addDataSQL = "Insert into cubes VALUES ('Mats Valk (human record holder)',6.27)";
            statement.executeUpdate(addDataSQL);

            System.out.println("Four rows added");

        }catch (SQLException sqle){
            System.out.println("The cubes table already exist, check SQL for any possible issues.");
            System.out.println(sqle);
        }
    }

    public static void shutdown(){
        //shut down the connection to SQL.
        try {
            if (statement != null) {
                statement.close();
                System.out.println("Statement closed");
            }
        } catch (SQLException se) {
            //Closing the connection could throw an exception too
            se.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();  //Close connection to database
                System.out.println("Database connection closed");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        System.exit(0);
    }
}
