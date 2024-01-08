package ui;

import database.DatabaseConnectionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class agbhFrame extends JFrame implements ActionListener {
    // JTextField
    static JComboBox<String> operationBox;
    static JComboBox<String> attributeBox;
    static JComboBox<String> comparatorBox;
    static JTextField t1;
    // JFrame
    static JFrame agbhFrame;
    // JButton
    static JButton submitButton;
    // label to display text
    static JLabel teamText;
    static JLabel operationLabel;
    static JLabel attributeLabel;
    static JLabel comparatorLabel;

    static JLabel checkboxLabel;
    static JCheckBox checkbox1;

    public agbhFrame() {
        // create a label to display text
        teamText = new JLabel("Find a team's min/max stats grouped by an attribute");
        operationLabel = new JLabel("Operation: ");
        attributeLabel = new JLabel("Attribute: ");
        comparatorLabel = new JLabel("Where the attribute is: ");
        checkboxLabel = new JLabel("With conditions on the result?");

        checkbox1 = new JCheckBox();
        t1 = new JTextField(16);

        // create a new frame to store text field and button
        agbhFrame = new JFrame("AGBH");

        // create a new button
        submitButton = new JButton("Submit");

        submitButton.addActionListener(this);

        String[] operationChoices = { "MIN", "MAX"};
        operationBox = new JComboBox<String>(operationChoices);

        String[] attributeChoices = { "net_worth", "wins", "losses", "matches_played"};
        attributeBox = new JComboBox<String>(attributeChoices);

        String[] comparatorChoices = { "=", ">", "<", ">=", "<=", "<>"};
        comparatorBox = new JComboBox<String>(comparatorChoices);

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        // add buttons and textfield to panel
        p.add(teamText);
        p.add(operationLabel);
        p.add(operationBox);
        p.add(attributeLabel);
        p.add(attributeBox);
        p.add(checkboxLabel);
        p.add(checkbox1);
        p.add(comparatorLabel);
        p.add(comparatorBox);
        p.add(t1);
        p.add(submitButton);

        teamText.setAlignmentX(Component.CENTER_ALIGNMENT);
        operationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        operationBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        attributeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        attributeBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkboxLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkbox1.setAlignmentX(Component.CENTER_ALIGNMENT);
        comparatorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        comparatorBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        t1.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add panel to frame
        agbhFrame.add(p);

        // set the size of frame
        agbhFrame.setSize(400, 300);

        agbhFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String operator = operationBox.getSelectedItem().toString();
        String attribute = attributeBox.getSelectedItem().toString();
        String comparator = comparatorBox.getSelectedItem().toString();
        String cval = t1.getText();

        DatabaseConnectionHandler dch = new DatabaseConnectionHandler();

        if (checkbox1.isSelected()) {
            ArrayList<ArrayList<String>> result = dch.aggregateHavingTeams(operator, attribute, comparator, cval);

            for (int i = 0; i < result.get(0).size(); i++) {
                System.out.println(result.get(0).get(i) + " " + result.get(1).get(i));
                class agbhTable1 extends JFrame {
                    public agbhTable1() throws SQLException {
                        setLayout(new FlowLayout());
                        JTable table = new JTable();

                        String[] columnNames = {"Home City", operator + " " + attribute + " " + comparator + " " + cval};
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
                    agbhTable1 gui = new agbhTable1();
                    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    gui.setSize(1000, 300);
                    gui.setVisible(true);
                    gui.setTitle("Aggregation GROUP BY Table with " + operator + " operator");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else {
            ArrayList<ArrayList<String>> result = dch.aggregateGBTeams(operator, attribute);

            for (int i = 0; i < result.get(0).size(); i++) {
                System.out.println(result.get(0).get(i) + " " + result.get(1).get(i));
            }

            class agbhTable2 extends JFrame {
                public agbhTable2() throws SQLException {
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
                agbhTable2 gui = new agbhTable2();
                gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gui.setSize(1000, 300);
                gui.setVisible(true);
                gui.setTitle("Aggregation with HAVING");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }
}

