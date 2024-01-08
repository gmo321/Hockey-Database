package ui;

import database.DatabaseConnectionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class divisionFrame extends JFrame implements ActionListener {

    //Find all spectators who have been to every game

    // JFrame
    static JFrame divisionFrame;
    // JButton
    static JButton submitButton;
    // label to display text
    static JLabel spectatorsLabel;

    public divisionFrame() {

        // create a label to display text
        spectatorsLabel = new JLabel("Find all spectators SIN who have been to every match");

        divisionFrame = new JFrame("Division");

        // create a new button
        submitButton = new JButton("Submit");

        submitButton.addActionListener(this);

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        // add buttons and textfield to panel
        p.add(spectatorsLabel);

        p.add(submitButton);

        spectatorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add panel to frame
        divisionFrame.add(p);

        // set the size of frame
        divisionFrame.setSize(400, 200);

        divisionFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        DatabaseConnectionHandler dch = new DatabaseConnectionHandler();

        ArrayList<ArrayList<String>> result = dch.divisionSpectators();

        // // TODO: Output this better
        for (int i = 0; i < result.get(0).size(); i++) {
            System.out.println(result.get(0).get(i) + " " + result.get(1).get(i));
        }

        class divisionTable extends JFrame {
            public divisionTable() throws SQLException {
                setLayout(new FlowLayout());
                JTable table = new JTable();

                String[] columnNames = {"SIN", "Name"};
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
            divisionTable gui = new divisionTable();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setSize(1000, 300);
            gui.setVisible(true);
            gui.setTitle("Division Table");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
