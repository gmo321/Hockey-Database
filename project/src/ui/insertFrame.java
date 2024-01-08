package ui;

import database.DatabaseConnectionHandler;
import model.TeamModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class insertFrame extends JFrame implements ActionListener {
    // JTextField
    static JTextField t1;
    static JTextField t2;
    static JTextField t3;
    static JTextField t4;
    static JTextField t5;
    // JFrame
    static JFrame insertFrame;
    // JButton
    static JButton submitButton;
    // label to display text
    static JLabel teamText;
    static JLabel homeCityLabel;
    static JLabel nameLabel;
    static JLabel netWorthLabel;
    static JLabel winsLabel;
    static JLabel lossesLabel;

    public insertFrame() {

        // create a label to display text
        teamText = new JLabel("Insert a new Team");

        homeCityLabel = new JLabel("Home City: ");

        nameLabel = new JLabel("Team Name: ");

        netWorthLabel = new JLabel("Net Worth: ");

        winsLabel = new JLabel("Number of Wins: ");

        lossesLabel = new JLabel("Number of Losses: ");


        // create a new frame to store text field and button
        insertFrame = new JFrame("Insert");

        // create a new button
        submitButton = new JButton("Submit");

        submitButton.addActionListener(this);

        // create a object of JTextField with 16 columns
        t1 = new JTextField(16);
        t2 = new JTextField(16);
        t3 = new JTextField(16);
        t4 = new JTextField(16);
        t5 = new JTextField(16);

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        // add buttons and textfield to panel
        p.add(teamText);
        p.add(nameLabel);
        p.add(t1);
        p.add(homeCityLabel);
        p.add(t2);
        p.add(netWorthLabel);
        p.add(t3);
        p.add(winsLabel);
        p.add(t4);
        p.add(lossesLabel);
        p.add(t5);
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


        // add panel to frame
        insertFrame.add(p);

        // set the size of frame
        insertFrame.setSize(400, 700);

        insertFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String name = t1.getText();
        String hc = t2.getText();
        float nw = (float) Integer.parseInt(t3.getText());
        int wins = Integer.parseInt(t4.getText());
        int losses = Integer.parseInt(t5.getText());
        int mp = wins + losses;

        DatabaseConnectionHandler dch = new DatabaseConnectionHandler();
        TeamModel tm = new TeamModel(hc, name, nw, wins, losses, mp);

        dch.insertTeam(tm);

        // set the text of field to blank
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        t5.setText("");
    }
}
