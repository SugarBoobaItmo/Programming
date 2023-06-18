package ui.mainframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import cli.commands.ClearCommand;
import cli.commands.exceptions.ExecuteError;
import collection_manager.AbstractManager;
import javafx.stage.WindowEvent;
import serveradapter.ServerAdapter;
import ui.interfaces.InputReader;
import ui.interfaces.OutputWriter;
import ui.mainframe.command_windows.InsertWindow;

public class ButtonsPanel extends JPanel {
    private ServerAdapter serverAdapter;
    private AbstractManager manager;
    private GroupTable table;

    public ButtonsPanel(ServerAdapter serverAdapter, AbstractManager manager, GroupTable table) {
        this.serverAdapter = serverAdapter;
        this.manager = manager;
        this.table = table;

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setOpaque(false);

        JButton insertButton = createButton("Insert", Color.GRAY, 150, 30);
        insertButton.addActionListener(new InsertButtonListener());
        JButton clearButton = createButton("Clear", Color.GRAY, 150, 30);
        clearButton.addActionListener(new ClearButtonListener());
        JButton updateButton = createButton("Update", Color.GRAY, 150, 30);
        updateButton.addActionListener(new UpdateButtonListener());

        JComboBox<String> removeButtonsComboBox = new JComboBox<String>();
        removeButtonsComboBox.setPreferredSize(new Dimension(150, 30));
        removeButtonsComboBox.addItem("Remove by id");
        removeButtonsComboBox.addItem("Remove greater");
        removeButtonsComboBox.addItem("Remove lower");

        removeButtonsComboBox.addActionListener(e -> {
            String selected = (String) removeButtonsComboBox.getSelectedItem();
            if (selected.equals("Remove by id")) {
                System.out.println("Remove by id");
            } else if (selected.equals("Remove greater")) {
                System.out.println("Remove greater");
            } else if (selected.equals("Remove lower")) {
                System.out.println("Remove lower");
            }
        });

        add(insertButton);
        add(clearButton);
        add(updateButton);
        add(removeButtonsComboBox);
        setVisible(true);
    }

    private JButton createButton(String text, Color color, int width, int height) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(width, height));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        return button;
    }

    class InsertButtonListener extends AbstractAction {
        private boolean insertWindowOpen = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (!insertWindowOpen) {
                insertWindowOpen = true;
                InsertWindow insertWindow = new InsertWindow();
                insertWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        insertWindowOpen = false;
                    }
                });
            }

        }

    }

    class ClearButtonListener extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            ClearCommand clear = new ClearCommand(manager);
            List<String> args = new ArrayList<String>();
            args.add("clear");
            try {
                clear.execute(args, new InputReader(null), new OutputWriter(), true);
                table.updateTable();

            } catch (ExecuteError e1) {
                new OutputWriter().writeLine(e1.getMessage());
            }

        }

    }

    class UpdateButtonListener extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            table.updateTable();

        }

    }
}
