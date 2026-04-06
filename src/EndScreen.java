import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
import java.util.List;

public class EndScreen extends JFrame {

    public EndScreen(Player player, List<HighScoreEntry> scores) {

        setTitle("Season Summary");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font titleFont = loadFont(40f);
        Font textFont = loadFont(18f);
        Font listFont = loadFont(20f);

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
        panel.setPreferredSize(new Dimension(600, 600));
        panel.setBackground(new Color(0, 0, 0, 180));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Season Complete!");
        title.setFont(titleFont);
        title.setForeground(Color.YELLOW);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel name = new JLabel("Farmer: " + player.getName());
        name.setFont(textFont);
        name.setForeground(Color.WHITE);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel gold = new JLabel("Final Gold: " + player.getSavings());
        gold.setFont(textFont);
        gold.setForeground(Color.WHITE);
        gold.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel leaderboardTitle = new JLabel("Top 10 Farmers");
        leaderboardTitle.setFont(textFont);
        leaderboardTitle.setForeground(Color.WHITE);
        leaderboardTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setOpaque(false);

        for (int i = 0; i < scores.size(); i++) {

            HighScoreEntry entry = scores.get(i);

            JLabel row = new JLabel(
                    (i + 1) + ". " + entry.getPlayerName() + " - " + entry.getFinalSavings()
            );

            row.setForeground(Color.WHITE);
            row.setFont(listFont);
            row.setAlignmentX(Component.CENTER_ALIGNMENT);

            list.add(row);
        }

        JButton restart = new JButton("Restart Game");
        restart.setBackground(new Color(76,175,80));
        restart.setForeground(Color.WHITE);
        restart.setFocusPainted(false);
        restart.setFont(textFont);
        restart.setAlignmentX(Component.CENTER_ALIGNMENT);

        restart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new WelcomeScreen();
            }
        });

        JButton exit = new JButton("Exit Game");
        exit.setBackground(new Color(100,181,246));
        exit.setForeground(Color.WHITE);
        exit.setFocusPainted(false);
        exit.setFont(textFont);
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0,20)));
        panel.add(name);
        panel.add(gold);
        panel.add(Box.createRigidArea(new Dimension(0,30)));
        panel.add(leaderboardTitle);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(list);
        panel.add(Box.createRigidArea(new Dimension(0,30)));
        panel.add(restart);
        panel.add(Box.createRigidArea(new Dimension(0,10)));
        panel.add(exit);

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