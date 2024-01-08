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

public class projectionFrame extends JFrame implements ActionListener {

   
    // JFrame
    static JFrame projectionFrame;
    // JButton
    static JButton submitButton;
    // label to display text
    static JLabel teamText;

    public projectionFrame() {

        // create a label to display text
        teamText = new JLabel("Display attributes Team Name, Home City, Net Worth for Teams");

    
        projectionFrame = new JFrame("Projection");

        // create a new button
        submitButton = new JButton("Submit");

        submitButton.addActionListener(this);


        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        // add buttons and textfield to panel
        p.add(teamText);
    
        p.add(submitButton);

        teamText.setAlignmentX(Component.CENTER_ALIGNMENT);


        // add panel to frame
        projectionFrame.add(p);

        // set the size of frame
        projectionFrame.setSize(400, 200);

        projectionFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        DatabaseConnectionHandler dch = new DatabaseConnectionHandler();

        ArrayList<ArrayList<String>> result = dch.projectionTeams();

        // // TODO: Output this better
        for (int i = 0; i < result.get(0).size(); i++) {
            System.out.println(result.get(0).get(i) + " " + result.get(1).get(i) + " " + result.get(2).get(i));
        }

        class projectionTable extends JFrame {
            public projectionTable() throws SQLException {
                setLayout(new FlowLayout());
                JTable table = new JTable();

                String[] columnNames = {"Home City", "Name", "Net Worth"};
                DefaultTableModel model = new DefaultTableModel();
                table.setModel(model);

                model.setColumnIdentifiers(columnNames);

                for (int i = 0; i < result.get(0).size(); i++) {
                    Object[] object = {result.get(0).get(i), result.get(1).get(i), result.get(2).get(i)};
                    model.addRow(object);
                }

                table.setPreferredScrollableViewportSize(new Dimension(800, 200));
                table.setFillsViewportHeight(true);

                JScrollPane jScrollPane = new JScrollPane(table);
                add(jScrollPane);
            }
        }

        try {
            projectionTable gui = new projectionTable();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setSize(1000, 300);
            gui.setVisible(true);
            gui.setTitle("Projection Table");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
