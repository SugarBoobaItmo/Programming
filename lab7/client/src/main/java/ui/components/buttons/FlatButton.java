package ui.components.buttons;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

import ui.components.constants.CustomColor;

public class FlatButton extends JButton implements CustomColor {
    private FlatButton(String text) {
        super(text);
    }

    public static Builder builder(String text) {
        return new Builder(text);
    }

    public static class Builder {
        private String text;
        private Color background = PINK;
        private Color foreground = WHITE;
        private Dimension size;

        public Builder(String text) {
            this.text = text;
        }

        public Builder white() {
            background = WHITE;
            foreground = BLACK;
            return this;
        }

        public Builder purple() {
            background = PINK;
            foreground = WHITE;
            return this;
        }

        public Builder small() {
            size = new Dimension(100, 30);
            return this;
        }

        public Builder medium() {
            size = new Dimension(150, 30);
            return this;
        }

        public Builder large() {
            size = new Dimension(200, 30);
            return this;
        }

        public FlatButton build() {
            FlatButton button = new FlatButton(text);
            button.setBackground(background);
            button.setForeground(foreground);
            button.setPreferredSize(size);

            button.setBorderPainted(false);
            button.setFocusable(false);
            // button.setFont(new Font("Arial", Font.BOLD, 12));
            return button;
        }
    }
}
