package ui;

import database.DatabaseConnectionHandler;
import model.TeamModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class selectionFrame extends JFrame implements ActionListener {
    static JTextField t1;
    static JTextField t2;
    static JTextField t3;
    static JTextField t4;
    static JTextField t5;
    static JTextField t6;
    // JFrame
    static JFrame selectionFrame;
    // JButton
    static JButton submitButton;
    // label to display text
    static JLabel teamText;
    static JLabel homeCityLabel;
    static JLabel nameLabel;
    static JLabel netWorthLabel;
    static JLabel winsLabel;
    static JLabel lossesLabel;
    static JLabel mpLabel;

    static JCheckBox checkbox1;
    static JCheckBox checkbox2;
    static JCheckBox checkbox3;
    static JCheckBox checkbox4;
    static JCheckBox checkbox5;
    static JCheckBox checkbox6;

    public selectionFrame() {
        // create a label to display text
        teamText = new JLabel("Show the Teams where");

        homeCityLabel = new JLabel("Home City: ");

        nameLabel = new JLabel("Team Name: ");

        netWorthLabel = new JLabel("Net Worth: ");

        winsLabel = new JLabel("Number of Wins: ");

        lossesLabel = new JLabel("Number of Losses: ");

        mpLabel = new JLabel("Number of Matches: ");

        // create a new frame to store text field and button
        selectionFrame = new JFrame("Selection");

        // create a new button
        submitButton = new JButton("Submit");

        submitButton.addActionListener(this);

        // create a object of JTextField with 16 columns
        t1 = new JTextField(16);
        t2 = new JTextField(16);
        t3 = new JTextField(16);
        t4 = new JTextField(16);
        t5 = new JTextField(16);
        t6 = new JTextField(16);

        checkbox1 = new JCheckBox();
        checkbox2 = new JCheckBox();
        checkbox3 = new JCheckBox();
        checkbox4 = new JCheckBox();
        checkbox5 = new JCheckBox();
        checkbox6 = new JCheckBox();

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        // add buttons and textfield to panel
        p.add(teamText);
        p.add(nameLabel);
        p.add(checkbox1);
        p.add(t1);
        p.add(homeCityLabel);
        p.add(checkbox2);
        p.add(t2);
        p.add(netWorthLabel);
        p.add(checkbox3);
        p.add(t3);
        p.add(winsLabel);
        p.add(checkbox4);
        p.add(t4);
        p.add(lossesLabel);
        p.add(checkbox5);
        p.add(t5);
        p.add(mpLabel);
        p.add(checkbox6);
        p.add(t6);
        p.add(submitButton);

        teamText.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        t1.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeCityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        t2.setAlignmentX(Component.CENTER_ALIGNMENT);
        netWorthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        t3.setAlignmentX(Component.CENTER_ALIGNMENT);
        winsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        t4.setAlignmentX(Component.CENTER_ALIGNMENT);
        lossesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        t5.setAlignmentX(Component.CENTER_ALIGNMENT);
        mpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        t6.setAlignmentX(Component.CENTER_ALIGNMENT);


        // add panel to frame
        selectionFrame.add(p);

        // set the size of frame
        selectionFrame.setSize(400, 700);

        selectionFrame.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        String name = t1.getText();
        String hc = t2.getText();
        float nw = (float) Integer.parseInt(t3.getText());
        int wins = Integer.parseInt(t4.getText());
        int losses = Integer.parseInt(t5.getText());
        int mp = wins + losses;

        int selection = 0;
        if (checkbox1.isSelected()) selection += 2;
        if (checkbox2.isSelected()) selection += 1;
        if (checkbox3.isSelected()) selection += 4;
        if (checkbox4.isSelected()) selection += 8;
        if (checkbox5.isSelected()) selection += 16;
        if (checkbox6.isSelected()) selection += 32;

        DatabaseConnectionHandler dch = new DatabaseConnectionHandler();

        ArrayList<TeamModel> result = dch.selectionTeams(selection, hc, name, nw, wins, losses, mp);

        for (TeamModel team : result) {
            System.out.println(team.getHome_city() + " " + team.getName() + " " + team.getNet_worth() + " " + team.getWins() + " " + team.getLosses() + " " + team.getMatches_played());
        }

        class selectionTable extends JFrame {
            public selectionTable() throws SQLException {
                setLayout(new FlowLayout());
                JTable table = new JTable();

                String[] columnNames = {"Home City", "Name", "Net Worth", "Wins", "Losses", "Matches Played"};
                DefaultTableModel model = new DefaultTableModel();
                table.setModel(model);

                model.setColumnIdentifiers(columnNames);

                for (TeamModel team : result) {
                    Object[] object = {team.getHome_city(), team.getName(), team.getNet_worth(), team.getWins(), team.getLosses(), team.getMatches_played()};
                    model.addRow(object);
                }

                table.setPreferredScrollableViewportSize(new Dimension(800, 200));
                table.setFillsViewportHeight(true);

                JScrollPane jScrollPane = new JScrollPane(table);
                add(jScrollPane);
            }
        }

        try {
            selectionTable gui = new selectionTable();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setSize(1000, 300);
            gui.setVisible(true);
            gui.setTitle("Selection Table");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // set the text of field to blank
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        t5.setText("");
        t6.setText("");
    }
}