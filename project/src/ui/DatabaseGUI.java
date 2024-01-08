package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class DatabaseGUI extends JFrame implements ActionListener {

    static JButton insertButton = new JButton("Insert");
    static JButton deleteButton = new JButton("Delete");
    static JButton updateButton = new JButton("Update");
    static JButton selectionButton = new JButton("Selection");
    static JButton projectionButton = new JButton("Projection");
    static JButton joinButton = new JButton("Join");

    static JButton agbButton = new JButton("Find a city's min/max stats");

    static JButton agbhButton = new JButton("Find a team's min/max stats grouped by an attribute");

    static JButton nestedButton = new JButton("Find a city's min wins stats");

    static JButton divisionButton = new JButton("Find all spectators who have watched all matches");

    // default constructor
    public DatabaseGUI(){

    }


    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Hockey Database");

        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();

        // Set the Boxlayout to be Y_AXIS from top to down
        BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);

        panel.setLayout(boxlayout);

        insertButton = new JButton("Insert");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");
        selectionButton = new JButton("Selection");
        projectionButton = new JButton("Projection");
        joinButton = new JButton("Join");
        agbButton = new JButton("Find a city's min/max stats");
        agbhButton = new JButton("Find a team's min/max stats grouped by an attribute");
        nestedButton = new JButton("Find a city's min wins stats");
        divisionButton = new JButton("Find all spectators who have watched all matches");

        panel.add(insertButton);
        panel.add(deleteButton);
        panel.add(updateButton);
        panel.add(selectionButton);
        panel.add(projectionButton);
        panel.add(joinButton);
        panel.add(agbButton);
        panel.add(agbhButton);
        panel.add(nestedButton);
        panel.add(divisionButton);

        insertButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        projectionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        joinButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        agbButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        agbhButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nestedButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        divisionButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Display the window.
        frame.add(panel);
        frame.setPreferredSize(new Dimension(400, 300));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new insertFrame();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new updateFrame();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new deleteFrame();
            }
        });

        selectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new selectionFrame();
            }
        });

        projectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new projectionFrame();
            }
        });

        joinButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new joinFrame();
            }
        });

        agbButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new agbFrame();
            }
        });

        agbhButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new agbhFrame();
            }
        });

        nestedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new nestedFrame();
            }
        });

        divisionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new divisionFrame();
            }
        });
    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }


    public void actionPerformed(ActionEvent e) {

    }
}
