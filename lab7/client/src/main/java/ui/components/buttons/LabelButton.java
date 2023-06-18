package ui.components.buttons;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

import ui.components.constants.CustomColor;

public class LabelButton extends JButton implements CustomColor {
    private LabelButton(String text) {
        super(text);
    }

    public static Builder builder(String text) {
        return new Builder(text);
    }

    public static class Builder {
        private String text;
        private Color foreground = BLACK;
        private Dimension size;

        public Builder(String text) {
            this.text = text;
        }

        public Builder white() {
            foreground = BLACK;
            return this;
        }

        public Builder black() {
            foreground = BLACK;
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

        public LabelButton build() {
            LabelButton button = new LabelButton(text);
            button.setForeground(foreground);
            button.setPreferredSize(size);

            button.setOpaque(false);
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusable(false);
            return button;
        }
    }
}
