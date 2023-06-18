package ui.mainframe;

import javax.swing.*;

import cli.commands.ExitCommand;
import cli.commands.exceptions.ExecuteError;
import serveradapter.ServerAdapter;
import ui.interfaces.InputReader;
import ui.interfaces.OutputWriter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Drawer extends JPanel {
    private ServerAdapter serverAdapter;
    JButton button1;
    JButton button2;
    JButton button3;
    JButton button4;

    public Drawer(ServerAdapter serverAdapter) {
        this.serverAdapter = serverAdapter;
        setLayout(new GridLayout(3, 1));
        setBackground(new Color(242, 229, 233, 100));
        setPreferredSize(new Dimension(200, getHeight()));
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(new Color(72, 71, 71, 100)));

        // add header with user icon and name
        JPanel headerPanel = new JPanel(new BorderLayout());

        JPanel circlePanel = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                int diameter = 85;
                int x = (getWidth() - diameter) / 2;
                int y = (getHeight() - diameter) / 2;
                g2d.setColor(new Color(198, 109, 138, 100));
                g2d.fillOval(x, y, diameter, diameter);
                g2d.dispose();
            }
        };
        // add user icon
        ImageIcon acc_icon = new ImageIcon(getClass().getResource("/ui/mainframe/drawer/user.png"));
        Image acc_image = acc_icon.getImage();
        Image acc_newimg = acc_image.getScaledInstance(65, 65, java.awt.Image.SCALE_SMOOTH);
        acc_icon = new ImageIcon(acc_newimg);
        circlePanel.add(new JLabel(acc_icon), BorderLayout.CENTER);
        circlePanel.setOpaque(false);

        headerPanel.add(circlePanel, BorderLayout.CENTER);

        JLabel label = new JLabel(serverAdapter.getUsername());
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setForeground(Color.white);
        label.setHorizontalAlignment(JLabel.CENTER);
        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);
        labelPanel.setOpaque(false);

        headerPanel.add(labelPanel, BorderLayout.SOUTH);

        headerPanel.setPreferredSize(new Dimension(200, getHeight()));
        headerPanel.setBackground(new Color(201, 88, 126, 100));

        // add buttons
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(7, -90, 0, 0);

        gbc.gridx = 0;
        gbc.gridy = 1;

        ImageIcon icon = new ImageIcon(getClass().getResource("/ui/mainframe/drawer/visualize.png"));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

        button1 = createTransparentButton("View", icon);
        button1.addActionListener(new VisualizeButtonListener());
        buttonPanel.add(button1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;

        ImageIcon icon2 = new ImageIcon(getClass().getResource("/ui/mainframe/drawer/help.png"));
        Image image2 = icon2.getImage();
        Image newimg2 = image2.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        icon2 = new ImageIcon(newimg2);

        button2 = createTransparentButton("Help", icon2);
        button2.addActionListener(new HelpButtonListener());
        buttonPanel.add(button2, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;

        ImageIcon icon3 = new ImageIcon(getClass().getResource("/ui/mainframe/drawer/map.png"));
        Image image3 = icon3.getImage();
        Image newimg3 = image3.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        icon3 = new ImageIcon(newimg3);

        button3 = createTransparentButton("Map", icon3);
        button3.addActionListener(new MapButtonListener());
        buttonPanel.add(button3, gbc);

        // add logout button
        JPanel bottomPanel = new JPanel(new GridBagLayout());

        ImageIcon icon4 = new ImageIcon(getClass().getResource("/ui/mainframe/drawer/exit.png"));
        Image image4 = icon4.getImage();
        Image newimg4 = image4.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        icon4 = new ImageIcon(newimg4);
        JButton logoutButton = createTransparentButton("Exit", icon4);
        logoutButton.addActionListener(new ExitButtonListener());
        bottomPanel.add(logoutButton, gbc);

        add(headerPanel);
        add(buttonPanel);
        add(bottomPanel);
        setVisible(false);
    }

    public JButton createTransparentButton(String text, ImageIcon icon) {

        JButton button = new JButton(text);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setIcon(icon);
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setFocusable(false);
        button.setIconTextGap(10);
        button.setContentAreaFilled(false);
        button.setFont(new Font("Arial", Font.PLAIN, 15));
        button.setForeground(Color.GRAY);
        return button;
    }

    class VisualizeButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println("Visualize button clicked");
        }
    }
    class HelpButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            // HelpCommand helpCommand = new HelpCommand(serverAdapter);

        }
    }
    class MapButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println("Map button clicked");
        }
    }
    class ExitButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            ExitCommand exitCommand = new ExitCommand(serverAdapter);
            List<String> args = new ArrayList<>();
            args.add("exit");
            try {
                exitCommand.execute(args, new InputReader(null), new OutputWriter(), true);
            } catch (ExecuteError e1) {
                new OutputWriter().writeLine(e1.getMessage());
            }

        }
    }
}
