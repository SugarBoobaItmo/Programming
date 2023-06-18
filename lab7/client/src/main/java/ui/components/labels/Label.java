package ui.components.labels;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ui.components.constants.CustomColor;

public class Label extends JLabel implements CustomColor {
    private Label(String text) {
        super(text);
    }

    public static Builder builder(String text) {
        return new Builder(text);
    }

    public static class Builder {
        private String text;
        private int size = 12;
        private int alignment = SwingConstants.LEADING;
        private Color foreground = BLACK;

        public Builder(String text) {
            this.text = text;
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

        public Label build() {
            Label label = new Label(this.text);
            label.setFont(label.getFont().deriveFont((float) this.size));
            label.setHorizontalAlignment(this.alignment);
            label.setForeground(this.foreground);
            return label;
        }
    }

}
