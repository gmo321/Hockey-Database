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

public class nestedFrame extends JFrame implements ActionListener {

    // JFrame
    static JFrame nestedFrame;
    // JButton
    static JButton submitButton;
    // label to display text
    static JLabel teamText;

    public nestedFrame() {
        // create a label to display text
        teamText = new JLabel("Find team for which their minimum number of wins is the minimum over all cities");

        // create a new frame to store text field and button
        nestedFrame = new JFrame("Nested Aggregation");

        // create a new button
        submitButton = new JButton("Submit");


        // addActionListener to button
        submitButton.addActionListener(this);

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        // add buttons and textfield to panel
        p.add(teamText);
        p.add(submitButton);

        teamText.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add panel to frame
        nestedFrame.add(p);

        // set the size of frame
        nestedFrame.setSize(400, 300);

        nestedFrame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        DatabaseConnectionHandler dch = new DatabaseConnectionHandler();

            ArrayList<ArrayList<String>> result = dch.aggregateNested();

            for (int i = 0; i < result.get(0).size(); i++) {
                System.out.println(result.get(0).get(i) + " " + result.get(1).get(i));
            }

        class nestedTable extends JFrame {
            public nestedTable() throws SQLException {
                setLayout(new FlowLayout());
                JTable table = new JTable();

                String[] columnNames = {"Home City", "MIN Wins"};
                DefaultTableModel model = new DefaultTableModel();
                table.setModel(model);

                model.setColumnIdentifiers(columnNames);

                for (int i = 0; i < result.get(0).size(); i++) {
                    Object[] object = {result.get(0).get(i), result.get(1).get(i)};
                    model.addRow(object);
                }

                table.setPreferredScrollableViewportSize(new Dimension(800, 200));
                table.setFillsViewportHeight(true);

                JScrollPane jScrollPane = new JScrollPane(table);
                add(jScrollPane);
            }
        }

        try {
            nestedTable gui = new nestedTable();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setSize(1000, 300);
            gui.setVisible(true);
            gui.setTitle("Nested Table");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }
}
