package ui.mainframe;

import javax.swing.*;

import collection_manager.AbstractManager;
import serveradapter.ServerAdapter;

import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    private ServerAdapter serverAdapter;
    private AbstractManager manager;

    Image background;
    private JPanel contentPanel;
    private Drawer drawerPanel;
    private JButton toggleButton;
    private JPanel tableButtonPanel;
    private boolean isDrawerOpen;
    private GroupTable groupTable;

    public MainFrame(ServerAdapter serverAdapter, AbstractManager manager) {
        this.serverAdapter = serverAdapter;
        this.manager = manager;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setMinimumSize(new Dimension(1200, 800));

        // create content panel where all components will be placed
        background = new ImageIcon(getClass().getResource("/ui/mainframe/background.png")).getImage();
        contentPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int panelWidth = getWidth();
                int panelHeight = getHeight();

                float bgScale = background.getWidth(null) / (float) background.getHeight(null);
                int bgSWidth = Math.round(panelHeight * bgScale);
                int bgSHeight = Math.round(panelWidth * (1 / bgScale));

                if (bgSWidth < panelWidth) {
                    g.drawImage(background, 0, 0, panelWidth, bgSHeight, null);
                } else {
                    g.drawImage(background, 0, 0, bgSWidth, panelHeight, null);
                }
            }
        };

        // add drawer
        drawerPanel = new Drawer(serverAdapter);
        contentPanel.add(drawerPanel, BorderLayout.WEST);

        // add button for drawer
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setOpaque(false);

        toggleButton = createButton("...", Color.white, 35, 30);
        toggleButton.setForeground(Color.BLACK);
        toggleButton.setBackground(Color.WHITE);
        toggleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toggleDrawer();
            }
        });
        buttonPanel.add(toggleButton);

        // add table
        groupTable = new GroupTable(manager);
        contentPanel.add(groupTable, BorderLayout.CENTER);

        // add header with logo
        JPanel headPanel = new JPanel(new BorderLayout());
        headPanel.setOpaque(false);
        headPanel.add(buttonPanel, BorderLayout.WEST);

        JLabel headerLabel = new JLabel(
                ("<html><div style='margin-top: 8px;'>Small store of your groups</div></html>"));
        headerLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        ImageIcon icon = new ImageIcon(getClass().getResource("/ui/mainframe/logo.png"));
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(35, 35, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

        headerLabel.setIcon(icon);
        headerLabel.setHorizontalTextPosition(JLabel.LEFT);
        headPanel.add(headerLabel, BorderLayout.CENTER);

        contentPanel.add(headPanel, BorderLayout.NORTH);

        // add buttons for table
        tableButtonPanel = new ButtonsPanel(serverAdapter, manager, groupTable);

        contentPanel.add(tableButtonPanel, BorderLayout.SOUTH);

        this.add(contentPanel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

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

    private void toggleDrawer() {
        if (isDrawerOpen) {
            drawerPanel.setVisible(false);
            enableMainContent(true);
            isDrawerOpen = false;
        } else {
            drawerPanel.setVisible(true);
            enableMainContent(false);
            isDrawerOpen = true;
            groupTable = new GroupTable(manager);

        }
    }

    private void enableMainContent(boolean enabled) {
        tableButtonPanel.setVisible(enabled);
    }

}
