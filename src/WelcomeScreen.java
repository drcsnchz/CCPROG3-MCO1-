import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;



/**
 * Represents the starting screen of the game
 *
 * Allows the player to:
 * - Enter their name
 * - Start a new game
 */

public class WelcomeScreen extends JFrame {

    public WelcomeScreen() {

        setTitle("Verdant Sun");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font pixelTitle = loadFont(72f);
        Font pixelSub = loadFont(24f);
        Font pixelButton = loadFont(20f);

        JPanel background = new JPanel() {

            Image bg = new ImageIcon(
                    getClass().getResource("/background.jpg")
            ).getImage();

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        background.setLayout(new GridBagLayout());
        setContentPane(background);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(600, 500));
        panel.setBackground(new Color(0, 0, 0, 180));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Verdant Sun");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(255, 220, 100));
        title.setFont(pixelTitle);

        JLabel subtitle = new JLabel("Totally not Stardew Valley");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(pixelSub);

        JLabel prompt = new JLabel("Enter your farmer name:");
        prompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        prompt.setForeground(Color.WHITE);
        prompt.setFont(pixelSub);

        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(300, 40));

        JButton start = new JButton("Start Game");
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        start.setBackground(new Color(76,175,80));
        start.setForeground(Color.WHITE);
        start.setFocusPainted(false);
        start.setFont(pixelButton);

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                dispose();
                new GameController(name);
            }
        });

        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(subtitle);
        panel.add(Box.createRigidArea(new Dimension(0,30)));
        panel.add(prompt);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(nameField);
        panel.add(Box.createRigidArea(new Dimension(0,30)));
        panel.add(start);
        panel.add(Box.createVerticalGlue());

        background.add(panel);

        setVisible(true);
    }

    private Font loadFont(float size) {
        try {
            InputStream is = getClass().getResourceAsStream("/pixel.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);
        } catch (Exception e) {
            return new Font("Arial", Font.BOLD, (int) size);
        }
    }
}