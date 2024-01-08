package ui;

import database.DatabaseConnectionHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class joinFrame extends JFrame implements ActionListener {
    // JTextField
    static JTextField t1;
    // JFrame
    static JFrame joinFrame;
    // JButton
    static JButton submitButton;
    // label to display text
    static JLabel teamText;
    static JLabel matchesText;

    public joinFrame() {

        // create a label to display text
        matchesText = new JLabel("Insert a Match ID");

        // create a new frame to store text field and button
        joinFrame = new JFrame("Join");

        // create a new button
        submitButton = new JButton("Submit");

        submitButton.addActionListener(this);

        // create a object of JTextField with 16 columns
        t1 = new JTextField(16);

        // create a panel to add buttons and textfield
        JPanel p = new JPanel();

        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        p.add(matchesText);
        p.add(t1);
        p.add(submitButton);

        matchesText.setAlignmentX(Component.CENTER_ALIGNMENT);
        t1.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add panel to frame
        joinFrame.add(p);

        // set the size of frame
        joinFrame.setSize(400, 200);

        joinFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        int mid = Integer.parseInt(t1.getText());

        DatabaseConnectionHandler dch = new DatabaseConnectionHandler();

        ArrayList<ArrayList<String>> result = dch.joinTeamsPlays(mid);

        // TODO: Output this better
        for (int i = 0; i < result.get(0).size(); i++) {
            System.out.println(result.get(0).get(i) + " " + result.get(1).get(i) + " " + result.get(2).get(i) + " " + result.get(3).get(i));
        }

        class joinTable extends JFrame {
            public joinTable() throws SQLException {
                setLayout(new FlowLayout());
                JTable table = new JTable();

                String[] columnNames = {"Home City", "Name", "Wins", "Losses"};
                DefaultTableModel model = new DefaultTableModel();
                table.setModel(model);

                model.setColumnIdentifiers(columnNames);

                for (int i = 0; i < result.get(0).size(); i++) {
                    Object[] object = {result.get(0).get(i), result.get(1).get(i), result.get(2).get(i), result.get(3).get(i)};
                    model.addRow(object);
                }

                table.setPreferredScrollableViewportSize(new Dimension(800, 200));
                table.setFillsViewportHeight(true);

                JScrollPane jScrollPane = new JScrollPane(table);
                add(jScrollPane);
            }
        }

        try {
            joinTable gui = new joinTable();
            gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gui.setSize(1000, 300);
            gui.setVisible(true);
            gui.setTitle("Join Table");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        // set the text of field to blank
        t1.setText("");
    }
}
