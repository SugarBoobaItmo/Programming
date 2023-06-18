package ui.mainframe.command_windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.OutputStreamWriter;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cli.commands.checker.Checkers;
import cli.commands.exceptions.IncorrectInteger;
import cli.commands.exceptions.IncorrectLong;
import cli.commands.exceptions.NullParam;
import cli.commands.exceptions.UnallowedSymbol;

public class InsertWindow extends JFrame {
    JTextField nameTextField;
    JTextField xTextField;
    JTextField yTextField;
    JTextField countOfStudentsTextField;
    JTextField expelledStudentsTextField;
    JTextField transferredStudentsTextField;
    JComboBox<String> semesterComboBox;
    JTextField adminNameTextField;
    JTextField adminPassportTextField;
    JComboBox<String> adminHairColorTextField;

    public InsertWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Insert Window");
        setSize(400, 500);
        setMinimumSize(new Dimension(400, 500));
        setMaximumSize(new Dimension(400, 500));
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create and configure the header labels with buttons/comboboxes
        JLabel headerLabel = createLabel("Insert", 400, 50, 22);


        panel.add(headerLabel, BorderLayout.NORTH);
        JPanel bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayout(11, 2));

        // Add labels with buttons/comboboxes to the headerPanel
        // ...
        JLabel nameLabel = createLabel("Name", 100, 30, 12);
        nameTextField = new JTextField();
        
        JLabel xLabel = createLabel("Pos X", 100, 30, 12);
        xTextField = new JTextField();

        JLabel yLabel = createLabel("Pos Y", 100, 30, 12);
        yTextField = new JTextField();

        JLabel countOfStudentsLabel = createLabel("Count of students", 100, 30, 12);
        countOfStudentsTextField = new JTextField();

        JLabel expelledStudentsLabel = createLabel("Expelled students", 100, 30, 12);
        expelledStudentsTextField = new JTextField();

        JLabel transferredStudentsLabel = createLabel("Transferred students", 100, 30, 12);
        transferredStudentsTextField = new JTextField();

        JLabel semesterLabel = createLabel("Semester", 100, 30, 12);
        semesterComboBox = new JComboBox<String>();
        semesterComboBox.addItem("FIFTH");
        semesterComboBox.addItem("SIXTH");
        semesterComboBox.addItem("SEVENTH");
        semesterComboBox.addItem("EIGHTH");
        
        JLabel adminNameLabel = createLabel("Admin name", 100, 30, 12);
        adminNameTextField = new JTextField();
        adminNameTextField.setVisible(false);
        adminNameLabel.setVisible(false);

        JLabel adminPassportLabel = createLabel("Admin passport", 100, 30, 12);
        adminPassportTextField = new JTextField();
        adminPassportLabel.setVisible(false);
        adminPassportTextField.setVisible(false);

        JLabel adminHairColorLabel = createLabel("Admin hair color", 100, 30, 12);
        // JTextField adminHairColorTextField = new JTextField();
        adminHairColorTextField = new JComboBox<String>();
        adminHairColorTextField.addItem("BLUE");
        adminHairColorTextField.addItem("WHITE");
        adminHairColorTextField.addItem("YELLOW");

        adminHairColorLabel.setVisible(false);
        adminHairColorTextField.setVisible(false);
        
        JLabel adminExistanceLabel = createLabel("Admin exist?", 100, 30, 12);
        JRadioButton adminRadioButton = new JRadioButton();
        adminRadioButton.addActionListener(e -> {
            if (adminRadioButton.isSelected()) {
                adminNameTextField.setVisible(true);
                adminPassportTextField.setVisible(true);
                adminHairColorTextField.setVisible(true);
                adminNameLabel.setVisible(true);
                adminPassportLabel.setVisible(true);
                adminHairColorLabel.setVisible(true);
            } else {
                adminNameTextField.setVisible(false);
                adminPassportTextField.setVisible(false);
                adminHairColorTextField.setVisible(false);
                adminNameLabel.setVisible(false);
                adminPassportLabel.setVisible(false);
                adminHairColorLabel.setVisible(false);
            }
        });
        

        bodyPanel.add(nameLabel);
        bodyPanel.add(nameTextField);

        bodyPanel.add(xLabel);
        bodyPanel.add(xTextField);

        bodyPanel.add(yLabel);
        bodyPanel.add(yTextField);

        bodyPanel.add(countOfStudentsLabel);
        bodyPanel.add(countOfStudentsTextField);

        bodyPanel.add(expelledStudentsLabel);
        bodyPanel.add(expelledStudentsTextField);

        bodyPanel.add(transferredStudentsLabel);
        bodyPanel.add(transferredStudentsTextField);

        bodyPanel.add(semesterLabel);
        bodyPanel.add(semesterComboBox);

        bodyPanel.add(adminExistanceLabel);
        bodyPanel.add(adminRadioButton);
        
        bodyPanel.add(adminNameLabel);
        bodyPanel.add(adminNameTextField);

        bodyPanel.add(adminPassportLabel);
        bodyPanel.add(adminPassportTextField);

        bodyPanel.add(adminHairColorLabel);
        bodyPanel.add(adminHairColorTextField);


        panel.add(bodyPanel, BorderLayout.CENTER);

        // Create and configure the save and cancel buttons
        JPanel footerPanel = new JPanel();
        JButton saveButton = createButton("Save", Color.GRAY, 100, 30);
        saveButton.addActionListener(new SaveButtonListener());
        JButton cancelButton = createButton("Cancel", Color.GRAY, 100, 30);

        footerPanel.add(saveButton);
        footerPanel.add(cancelButton);

        panel.add(footerPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    public JLabel createLabel(String text, int width, int height, int fontSize) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(width, height));
        label.setFont(new Font("Arial", Font.PLAIN, fontSize));
        label.setHorizontalAlignment(JLabel.CENTER);
        
        return label;
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
    public class SaveButtonListener extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            // throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            nameTextField.setBackground(Color.WHITE);
            xTextField.setBackground(Color.WHITE);
            yTextField.setBackground(Color.WHITE);
            countOfStudentsTextField.setBackground(Color.WHITE);
            try {
                Checkers.checkSymbols(nameTextField.getText());
            } catch (UnallowedSymbol e1) {
                nameTextField.setBackground(new Color(255, 0, 0));
                nameTextField.setText("");
            }
            try {
                Checkers.checkInteger(xTextField.getText());
            } catch (IncorrectInteger e1) {
                xTextField.setBackground(new Color(255, 0, 0));
                xTextField.setText("");
            }
            try {
                Checkers.checkInteger(yTextField.getText());
            } catch (IncorrectInteger e1) {
                yTextField.setBackground(new Color(255, 0, 0));
                yTextField.setText("");
            }
            try {
                Checkers.checkNull(countOfStudentsTextField.getText());
            } catch (NullParam e1) {
                try {
                    Checkers.checkLong(countOfStudentsTextField.getText());
                } catch (IncorrectLong e2) {
                    countOfStudentsTextField.setBackground(new Color(255, 0, 0, 50));
                    countOfStudentsTextField.setText("");
                }

            }
           
        }

    
    }
}
