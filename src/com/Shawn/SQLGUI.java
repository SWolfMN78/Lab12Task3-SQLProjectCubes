package com.Shawn;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/**
 * Created by Wolfknightx on 4/26/2016.
 */
public class SQLGUI extends JFrame{
    private JPanel rootPanel;
    private JButton btnLogInfo;
    private JTable sqlTable1;
    private JTextField txtSolverName;
    private JTextField txtSolverTime;
    private JButton btnDeleteSelection;
    private JButton btnUpdateSelection;
    private JTextField txtUpdateTime;

    Main main = new Main();

    protected SQLGUI(SQLModel sqlm){
        setContentPane(rootPanel);
        setPreferredSize(new Dimension(650, 650));
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //draft up the model and plug the JTable into it.
        sqlTable1.setModel(sqlm);
        sqlTable1.setGridColor(Color.black);
        sqlTable1.getColumnModel().getColumn(0).setWidth(450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //apply the button clicks
        buttonConfig();
    }

    public void insertStatement(){
        //method with a prepared statement that will insert the time into the database and reload the view.
        String solverName = txtSolverName.getText();
        String solverTime = txtSolverTime.getText();
        try{
            String prepStatmentInsert = "Insert Into Cubes VALUES (?, ?)";
            PreparedStatement psInsert = Main.conn.prepareStatement(prepStatmentInsert);
            psInsert.setString(1, solverName); //replace the first ?
            psInsert.setDouble(2, Double.parseDouble(solverTime)); //replace the second ?
            psInsert.executeUpdate();
            Main.setTable(); //using this to reload the screen and display the new data.
        }catch (SQLException sqle){
            System.out.println("Insert statment didn't work.");
        }

    }
    public void deleteStatement(){
        //here is a basic delete statement: delete from cubes where Rubiks_Solver = 'John';
        String solverName = txtSolverName.getText(); //bas place holder 
        try{
            String prepStatDelete = "Delete from cubes where Rubiks_solver = ?";
            PreparedStatement psDelete = Main.conn.prepareStatement(prepStatDelete);
            psDelete.setString(1, solverName );//I can't figure out the next bit to this...maybe it's too late.
        }catch (SQLException sqle){
            sqle.printStackTrace();
            System.out.println("Line couldn't be deleted.  Please check code and try again.");
        }

    }
    public void updateStatement(){
        //set up an update statment into the code based on what the user has selected. **Really difficult**
        String solverName = txtSolverName.getText();
        String solverTime = txtUpdateTime.getText();
        String update = "UPDATE cubes SET Solve_Duration = ? WHERE Rubiks_Solver = ?";

    }

    public void buttonConfig(){
        //set up the buttons to be used.
        btnLogInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertStatement();
            }
        });
        btnUpdateSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnDeleteSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }



}

