package ui.components.textfields;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import java.awt.Insets;

import ui.components.constants.CustomColor;

public class LineField extends JTextField implements CustomColor {
    public LineField() {
        super();
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(PURPLE);
        g2d.setStroke(new java.awt.BasicStroke(3f));
        g2d.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);

        g2d.dispose();
    }

    @Override
    public Insets getInsets() {
        return new Insets(0, 0, 1, 0);
    }

}
