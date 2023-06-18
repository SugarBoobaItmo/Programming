package ui.components.comboboxes;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxUI;

import ui.components.constants.CustomColor;

public class ComboBox extends JComboBox<String> implements CustomColor {
    protected ComboBox() {
        super();
        setBackground(GRAY);
        setForeground(WHITE);
        setFocusable(false);
        setUI(new CustomComboBoxUI());
        setAlignmentX(CENTER_ALIGNMENT);

    }
    protected class CustomComboBoxUI extends BasicComboBoxUI {

        @Override
        protected JButton createArrowButton() {
            // Create a custom arrow button with a different triangle icon
            return null;
        }
    }

    public static ComboBox defaultCombobox() {
        return new ComboBox();
    }

    public static ComboBox smallCombobox() {
        ComboBox combobox = new ComboBox();
        combobox.setFont(combobox.getFont().deriveFont(12f));
        return combobox;
    }

    public static ComboBox mediumCombobox() {
        ComboBox combobox = new ComboBox();
        combobox.setFont(combobox.getFont().deriveFont(14f));
        return combobox;
    }

    public static ComboBox largeCombobox() {
        ComboBox combobox = new ComboBox();
        combobox.setFont(combobox.getFont().deriveFont(18f));
        return combobox;
    }
}
