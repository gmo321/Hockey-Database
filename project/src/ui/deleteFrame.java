package ui;

import database.DatabaseConnectionHandler;
import model.TeamModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class deleteFrame extends JFrame implements ActionListener {
    // JTextField
    static JTextField t1;
    static JTextField t2;
    private JFrame deleteFrame;
    static JButton submitButton;

    static JLabel deleteLabel;
    static JLabel teamLabel;
    static JLabel homeCityLabel;

    public deleteFrame() {

        // create a label to display text
        deleteLabel = new JLabel("Choose a Team to delete");

        teamLabel = new JLabel("Team name: ");

        homeCityLabel = new JLabel("Home City: ");

        t1 = new JTextField(8);
        t2 = new JTextField(8);

        t1.setMaximumSize(t1.getPreferredSize());
        t2.setMaximumSize(t2.getPreferredSize());

        // create a new frame to store text field and button
        deleteFrame = new JFrame("Delete a Team");

        // create a new button
        submitButton = new JButton("Submit");

        submitButton.addActionListener(this);

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        //TODO frame size not showing all elements-- implement scroll bar?
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        // add buttons and textfield to panel
        p.add(deleteLabel);
        p.add(teamLabel);
        p.add(t1);
        p.add(homeCityLabel);
        p.add(t2);
        p.add(submitButton);

        deleteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        teamLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        t1.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeCityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        t2.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add panel to frame
        deleteFrame.add(p);

        deleteFrame.setSize(400, 200);

        deleteFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = t1.getText();
        String hc = t2.getText();

        DatabaseConnectionHandler dch = new DatabaseConnectionHandler();

        dch.deleteTeam(hc, name);

        // set the text of field to blank
        t1.setText("");
        t2.setText("");
    }
}
