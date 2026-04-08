import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


/**
 * Graphical user interface for the game
 *
 * Displays:
 * - The field grid
 * - Player information
 * - Game controls (plant, water, harvest, etc.)
 *
 * Handles user interaction with tiles and actions as well
 */

public class VerdantGUI extends JFrame {

    private GameController controller;
    private JButton[][] tiles;
    private List<Point> selectedTiles;

    private JLabel nameLabel;
    private JLabel dayLabel;
    private JLabel moneyLabel;
    private JLabel waterLabel;
    private JLabel selectionLabel;

    public VerdantGUI(GameController controller) {

        this.controller = controller;
        this.selectedTiles = new ArrayList<Point>();

        setTitle("Verdant Sun");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel background = new JPanel() {

            Image bg = new ImageIcon(
                    getClass().getResource("/background.jpg")
            ).getImage();

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };

        background.setLayout(new BorderLayout());
        setContentPane(background);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);

        JPanel grid = new JPanel(new GridLayout(10,10,6,6));
        grid.setOpaque(false);

        tiles = new JButton[10][10];

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {

                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(55,55));
                btn.setFocusPainted(false);
                btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

                final int row = r;
                final int col = c;

                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        toggleTile(row, col);
                    }
                });

                tiles[r][c] = btn;
                grid.add(btn);
            }
        }

        centerWrapper.add(grid);
        background.add(centerWrapper, BorderLayout.CENTER);

        JPanel side = new JPanel();
        side.setPreferredSize(new Dimension(280,0));
        side.setLayout(new BorderLayout());
        side.setBackground(new Color(0, 80, 40, 220));

        JPanel profile = new JPanel();
        profile.setLayout(new BoxLayout(profile, BoxLayout.Y_AXIS));
        profile.setOpaque(false);
        profile.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));

        JLabel title = new JLabel("FARMER PROFILE");
        title.setForeground(Color.LIGHT_GRAY);
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));

        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        nameLabel.setForeground(Color.WHITE);

        dayLabel = createInfo();
        moneyLabel = createInfo();
        waterLabel = createInfo();

        profile.add(title);
        profile.add(Box.createRigidArea(new Dimension(0,10)));
        profile.add(nameLabel);
        profile.add(Box.createRigidArea(new Dimension(0,10)));
        profile.add(dayLabel);
        profile.add(moneyLabel);
        profile.add(waterLabel);

        JPanel middle = new JPanel();
        middle.setLayout(new BoxLayout(middle, BoxLayout.Y_AXIS));
        middle.setOpaque(false);
        middle.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

        JLabel infoTitle = new JLabel("STATUS");
        infoTitle.setForeground(Color.LIGHT_GRAY);
        infoTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));

        selectionLabel = new JLabel("Selected: 0 tiles");
        selectionLabel.setForeground(Color.WHITE);
        selectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        middle.add(infoTitle);
        middle.add(Box.createRigidArea(new Dimension(0,5)));
        middle.add(selectionLabel);

        JPanel actions = new JPanel(new GridLayout(6,1,12,12));
        actions.setOpaque(false);
        actions.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));

        JButton plant = createButton("Plant", new Color(76,175,80));
        JButton water = createButton("Water", new Color(33,150,243));
        JButton harvest = createButton("Harvest", new Color(255,152,0));
        JButton fertilize = createButton("Fertilize", new Color(156,39,176));
        JButton excavate = createButton("Excavate", new Color(121,85,72));
        JButton next = createButton("Next Day", new Color(100,181,246));

        plant.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int type = askPlantType();
                if (type != -1) controller.plant(selectedTiles, type);
                selectedTiles.clear();
                update();
            }
        });

        water.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.water(selectedTiles);
                selectedTiles.clear();
                update();
            }
        });

        harvest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.harvest(selectedTiles);
                selectedTiles.clear();
                update();
            }
        });

        fertilize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.fertilize(selectedTiles);
                selectedTiles.clear();
                update();
            }
        });

        excavate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < selectedTiles.size(); i++) {
                    Point p = selectedTiles.get(i);
                    controller.excavate(p.x, p.y);
                }
                selectedTiles.clear();
                update();
            }
        });

        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.nextDay();
            }
        });

        actions.add(next);
        actions.add(plant);
        actions.add(water);
        actions.add(harvest);
        actions.add(fertilize);
        actions.add(excavate);

        side.add(profile, BorderLayout.NORTH);
        side.add(middle, BorderLayout.CENTER);
        side.add(actions, BorderLayout.SOUTH);

        background.add(side, BorderLayout.EAST);

        setVisible(true);
    }

    private JLabel createInfo() {
        JLabel l = new JLabel();
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        return l;
    }

    private JButton createButton(String text, Color c) {
        JButton b = new JButton(text);
        b.setBackground(c);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("Segoe UI", Font.BOLD, 18));
        b.setPreferredSize(new Dimension(0, 50));
        b.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return b;
    }

    private void toggleTile(int row, int col) {
        Point p = new Point(row, col);

        if (selectedTiles.contains(p)) {
            selectedTiles.remove(p);
        } else {
            selectedTiles.add(p);
        }

        update();
    }

    private String getPlantSymbol(Plant p) {
        if (p instanceof Turnip) return "Tu";
        if (p instanceof Wheat) return "Wh";
        if (p instanceof Potato) return "Po";
        if (p instanceof Tomato) return "To";
        if (p instanceof Thyme) return "Th";
        return "?";
    }

    public void update() {

        nameLabel.setText(controller.getPlayer().getName());
        dayLabel.setText("Day: " + controller.getDay() + " / 20");
        moneyLabel.setText("Gold: " + controller.getPlayer().getSavings());
        waterLabel.setText("Water: " + controller.getWateringCan().getCurrentWater() + " / 10");

        selectionLabel.setText("Selected: " + selectedTiles.size() + " tiles");

        Field field = controller.getField();

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {

                Soil soil = field.getSoil(r, c);
                JButton btn = tiles[r][c];

                btn.setText("");

                if (selectedTiles.contains(new Point(r,c))) {
                    btn.setBorder(BorderFactory.createLineBorder(Color.CYAN, 3));
                } else {
                    btn.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
                }

                if (soil.isMeteoriteAffected()) {
                    btn.setBackground(Color.RED);
                    btn.setText("M");
                }
                else if (soil.hasPlant()) {

                    Plant plant = soil.getPlant();
                    String stage = plant.getCurrentStage().getClass().getSimpleName();

                    if (stage.contains("Seedling")) btn.setBackground(Color.GREEN);
                    else if (stage.contains("Dormant")) btn.setBackground(Color.BLUE);
                    else if (stage.contains("Energizing")) btn.setBackground(new Color(128,0,128));
                    else if (stage.contains("Low")) btn.setBackground(Color.ORANGE);
                    else if (stage.contains("High")) btn.setBackground(Color.RED);
                    else if (stage.contains("Fully")) btn.setBackground(Color.BLACK);

                    btn.setText(getPlantSymbol(plant));
                }
                else {
                    String type = soil.getSoilType().toLowerCase();

                    if (type.equals("loam")) btn.setBackground(new Color(210,180,140));
                    else if (type.equals("sand")) btn.setBackground(Color.YELLOW);
                    else if (type.equals("gravel")) btn.setBackground(Color.LIGHT_GRAY);
                }

                btn.setToolTipText("Soil: " + soil.getSoilType());
            }
        }
    }

    private int askPlantType() {
        String[] options = {"Turnip","Wheat","Potato","Tomato","Thyme"};

        return JOptionPane.showOptionDialog(
                this,
                "Choose plant:",
                "Plant",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        ) + 1;
    }
}