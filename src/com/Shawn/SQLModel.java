package com.Shawn;

import javax.print.DocFlavor;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Wolfknightx on 4/26/2016.
 */
public class SQLModel extends AbstractTableModel {

    ResultSet resultSet;
    int numberOfRows;
    int numberOfColumns;

    //make a constructor to handle the information from the result set
    SQLModel(ResultSet rs) {
        this.resultSet = rs;
        //get the number of rows from the resultset.

        countRows();

        try {
            numberOfColumns = resultSet.getMetaData().getColumnCount();
        } catch (SQLException sqle) {
            System.out.println("Error setting up data model" + sqle);
        }
    }

    protected void countRows() {

        try {
            resultSet.beforeFirst();

            numberOfRows = 0;

            while (resultSet.next()) {
                System.out.println("count");
                numberOfRows++;
            }

            resultSet.beforeFirst(); //this will reset the cursor.

            System.out.println(numberOfRows);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    @Override
    public int getRowCount(){
        return numberOfRows;
    }

    @Override
    public int getColumnCount(){
        return numberOfColumns;
    }

    @Override
    public String getValueAt(int row, int col){
        try {
            resultSet.absolute(row + 1); //tack on a +1 because Java starts at 0 so this will line up with SQL
            Object o = resultSet.getObject(col + 1);
            return o.toString();
        } catch (SQLException sqle) {
            //Display the text of the error message in the cell
            return sqle.toString();
        }
    }
}
