package ui.components.labels;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ui.components.constants.CustomColor;

public class IconLabel extends JLabel implements CustomColor{
    private IconLabel(String text, ImageIcon icon) {
        super(text);
        setHorizontalAlignment(JLabel.CENTER);
    }

    public static Builder builder(String text, ImageIcon icon) {
        return new Builder(text, icon);
    }

    public static class Builder {
        private String text;
        private int size = 12;
        private int alignment = SwingConstants.LEADING;
        private Color foreground = BLACK;
        private ImageIcon icon;

        public Builder(String text, ImageIcon icon) {
            this.text = text;
            this.icon = icon;
        }

        public Builder small() {
            this.size = 12;
            return this;
        }

        public Builder medium() {
            this.size = 14;
            return this;
        }

        public Builder large() {
            this.size = 18;
            return this;
        }

        public Builder left() {
            this.alignment = SwingConstants.LEFT;
            return this;
        }

        public Builder center() {
            this.alignment = SwingConstants.CENTER;
            return this;
        }

        public Builder white() {
            foreground = WHITE;
            return this;
        }

        public IconLabel build() {
            IconLabel label = new IconLabel(this.text, this.icon);
            label.setFont(label.getFont().deriveFont((float) this.size));
            label.setHorizontalAlignment(this.alignment);
            label.setForeground(this.foreground);
            return label;
        }
    }


}