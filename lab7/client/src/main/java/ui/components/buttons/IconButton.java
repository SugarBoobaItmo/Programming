package ui.components.buttons;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import ui.components.constants.CustomColor;

public class IconButton extends JButton implements CustomColor {
    private IconButton(String text, ImageIcon icon) {
        super(text);
        this.setIcon(icon);
    }

    public static Builder builder(String text, ImageIcon icon) {
        return new Builder(text, icon);
    }

    public static class Builder {
        private String text;
        private ImageIcon icon;

        private Color foreground = GRAY;
        private Dimension size;

        public Builder(String text, ImageIcon icon) {
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

        public IconButton build() {
            IconButton button = new IconButton(text, icon);
            button.setForeground(foreground);
            button.setPreferredSize(size);

            button.setBorder(BorderFactory.createEmptyBorder());
            button.setHorizontalTextPosition(SwingConstants.RIGHT);
            button.setFocusable(false);
            button.setIconTextGap(10);
            button.setContentAreaFilled(false);
            // setFont(new Font("Arial", Font.PLAIN, 15));
            return button;
        }
    }
}
