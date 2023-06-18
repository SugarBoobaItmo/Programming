package ui.authtorization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import cli.commands.LoginCommand;
import cli.commands.exceptions.ExecuteError;
import collection_manager.RemoteManager;
import serveradapter.ServerAdapter;
import ui.interfaces.InputReader;
import ui.interfaces.OutputWriter;
import ui.mainframe.MainFrame;

/**
 * 
 * The LogupWindow class represents a graphical user interface window for user
 * logup.
 * 
 * It extends the JFrame class.
 */
public class LogupWindow extends JFrame {
    private ServerAdapter serverAdapter;

    Image background;
    Image logo;

    JButton logupButton;
    JTextField usernameField;
    JTextField passwordField;
    JLabel logupLabel;
    JButton loginButton;

    /**
     * 
     * Creates an instance of the LogupWindow with the specified ServerAdapter.
     * 
     * @param serverAdapter the ServerAdapter to be used for communication with the
     *                      server
     */
    public LogupWindow(ServerAdapter serverAdapter) {
        this.serverAdapter = serverAdapter;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setMinimumSize(new Dimension(800, 600));

        background = new ImageIcon(getClass().getResource("/ui/authtorization/background.png")).getImage();
        logo = new ImageIcon(getClass().getResource("/ui/authtorization/logo.png")).getImage();

        JPanel panel = new JPanel() {
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

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 0, 0);

        usernameField = createTextField();
        passwordField = createTextField();
        logupButton = new JButton("Logup");

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel imageLabel = new JLabel(new ImageIcon(logo));
        panel.add(imageLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(createLabel("Please write your username"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(createLabel("Please come up with your personal password"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(passwordField, gbc);

        logupButton.setBorderPainted(false);
        logupButton.setBackground(new Color(201, 88, 126));
        logupButton.setForeground(Color.WHITE);
        logupButton.setFocusable(false);
        logupButton.addActionListener(new LogupListener());

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(logupButton, gbc);

        JPanel logupPanel = new JPanel();
        logupPanel.setLayout(new FlowLayout());
        logupPanel.setOpaque(false);
        logupLabel = new JLabel("Oh, welcome to our family, my little friend!");

        loginButton = new JButton("Login");
        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.addActionListener(new LoginButtonListener());

        logupPanel.add(logupLabel);
        logupPanel.add(loginButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(logupPanel, gbc);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * 
     * Creates a label with the specified text.
     * 
     * @param text the text for the label
     * @return a JLabel instance with the specified text
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }

    /**
     * 
     * Creates a custom JTextField with a stylized appearance.
     * 
     * @return a JTextField instance with a custom appearance
     */
    private JTextField createTextField() {
        JTextField textField = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setColor(new Color(0, 0, 100, 70));
                g2d.setStroke(new java.awt.BasicStroke(3f));
                g2d.drawLine(0, getHeight() - 1, getWidth() + 300, getHeight() - 1);

                g2d.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width + 300, 20);
            }

            @Override
            public Insets getInsets() {
                return new Insets(0, 0, 1, 0);
            }
        };
        textField.setOpaque(false); // Set the text field as transparent
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return textField;
    }

    /**
     * 
     * ActionListener implementation for the loginButton.
     * Handles the action performed when the loginButton is clicked.
     */
    class LoginButtonListener extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == loginButton) {
                // close the current window
                setVisible(false);
                new AuthorizationWindow(serverAdapter);
            }
        }
    }

    /**
     * 
     * ActionListener implementation for the logupButton.
     * 
     * Handles the action performed when the logupButton is clicked.
     */
    class LogupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == logupButton) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                // create from username input and password input
                Queue<String> queue = new LinkedList<>();
                queue.add(username);
                queue.add(password);

                if (logup(new InputReader(queue), new OutputWriter())) {
                    setVisible(false);
                    new MainFrame(serverAdapter, new RemoteManager(serverAdapter));
                } else {
                    passwordField.setText("");
                    usernameField.setText("");
                }
            }
        }

        private boolean logup(InputReader inputReader, OutputWriter outputReader) {
            try {
                new LoginCommand(serverAdapter).logup(inputReader::readLine,
                        outputReader::writeLine);
                if (serverAdapter.getLogin() != null)
                    return true;
                return false;
            } catch (ExecuteError e1) {
                outputReader.writeLine(e1.getMessage());
                return false;
            }
        }

    }
}
