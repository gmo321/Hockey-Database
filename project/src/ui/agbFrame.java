package ui;

import database.DatabaseConnectionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class agbFrame extends JFrame implements ActionListener {
    // JTextField
    static JComboBox<String> operationBox;
    static JComboBox<String> attributeBox;

    // JFrame
    static JFrame agbFrame;
    // JButton
    static JButton submitButton;
    // label to display text
    static JLabel teamText;
    static JLabel operationLabel;
    static JLabel attributeLabel;

    public agbFrame() {

        // create a label to display text
        teamText = new JLabel("Find a city's min/max stats: ");
        operationLabel = new JLabel("Operation: ");
        attributeLabel = new JLabel("Attribute: ");

        // create a new frame to store text field and button
        agbFrame = new JFrame("AGB");

        // create a new button
        submitButton = new JButton("Submit");

        submitButton.addActionListener(this);

        String[] operationChoices = { "MIN", "MAX"};
        operationBox = new JComboBox<String>(operationChoices);

        String[] attributeChoices = { "net_worth", "wins", "losses", "matches_played"};
        attributeBox = new JComboBox<String>(attributeChoices);


        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        // add buttons and textfield to panel
        p.add(teamText);
        p.add(operationLabel);
        p.add(operationBox);
        p.add(attributeLabel);
        p.add(attributeBox);
        p.add(submitButton);

        teamText.setAlignmentX(Component.CENTER_ALIGNMENT);
        operationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        operationBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        attributeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        attributeBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add panel to frame
        agbFrame.add(p);

        // set the size of frame
        agbFrame.setSize(400, 300);

        agbFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String operator = operationBox.getSelectedItem().toString();
        String attribute = attributeBox.getSelectedItem().toString();

        DatabaseConnectionHandler dch = new DatabaseConnectionHandler();

            ArrayList<ArrayList<String>> result = dch.aggregateGBTeams(operator, attribute);

            for (int i = 0; i < result.get(0).size(); i++) {
                System.out.println(result.get(0).get(i) + " " + result.get(1).get(i));
            }
        class agbTable extends JFrame {
            public agbTable() throws SQLException {
                setLayout(new FlowLayout());
                JTable table = new JTable();

                String[] columnNames = {"Home City", operator + " " + attribute};
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
            agbTable gui = new agbTable();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setSize(1000, 300);
            gui.setVisible(true);
            gui.setTitle("Aggregation GROUP BY Table with " + operator + " operator");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
