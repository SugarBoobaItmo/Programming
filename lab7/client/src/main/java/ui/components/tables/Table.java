package ui.components.tables;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.plaf.basic.BasicScrollBarUI;

import ui.components.constants.CustomColor;

public class Table extends JPanel implements CustomColor {
    private JTable table;

    public JTable getTable() {
        return this.table;
    }

    public Table() {
        this.table = new JTable();
        table.setOpaque(false);
        table.getTableHeader().setBackground(WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        add(scrollPane);
    }

    class CustomScrollBarUI extends BasicScrollBarUI {
        // Override methods to customize the appearance and behavior of the scroll pane
        // Here's an example of customizing the scroll bar color
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setPaint(GRAY);
            g2.fillRoundRect(thumbBounds.x + 5, thumbBounds.y, thumbBounds.width - 10, thumbBounds.height, 10, 10);
            g2.dispose();
        }
    }
}
