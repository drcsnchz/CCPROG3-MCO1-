import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VerdantGUI extends JFrame {

    private GameController controller;
    private JButton[][] tiles;
    private List<Point> selectedTiles;

    private JLabel dayLabel;
    private JLabel moneyLabel;

    public VerdantGUI(GameController controller) {
        this.controller = controller;
        this.selectedTiles = new ArrayList<>();

        setTitle("Verdant Sun");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initTopPanel();
        initGrid();
        initButtons();

        setVisible(true);
    }

    private void initTopPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);

        dayLabel = new JLabel();
        moneyLabel = new JLabel();

        dayLabel.setForeground(Color.WHITE);
        moneyLabel.setForeground(Color.WHITE);

        dayLabel.setFont(new Font("Arial", Font.BOLD, 18));
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 18));

        panel.add(dayLabel);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(moneyLabel);

        add(panel, BorderLayout.NORTH);
    }

    private void initGrid() {
        JPanel grid = new JPanel(new GridLayout(10, 10));
        tiles = new JButton[10][10];

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                JButton btn = new JButton(".");
                btn.setFont(new Font("Arial", Font.BOLD, 14));
                btn.setFocusPainted(false);
                btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));

                btn.setOpaque(true);
                btn.setContentAreaFilled(true);
                btn.setBorderPainted(true);

                final int row = r, col = c;
                btn.addActionListener(e -> toggleTile(row, col));

                tiles[r][c] = btn;
                grid.add(btn);
            }
        }

        add(grid, BorderLayout.CENTER);
    }

    private void initButtons() {
        JPanel panel = new JPanel();

        JButton plant = new JButton("Plant");
        JButton water = new JButton("Water");
        JButton fertilize = new JButton("Fertilize");
        JButton harvest = new JButton("Harvest");
        JButton excavate = new JButton("Excavate");
        JButton nextDay = new JButton("Next Day");

        plant.addActionListener(e -> showPlantMenu());

        water.addActionListener(e -> {
            controller.water(selectedTiles);
            clearSelection();
        });

        fertilize.addActionListener(e -> {
            controller.fertilize(selectedTiles);
            clearSelection();
        });

        harvest.addActionListener(e -> {
            controller.harvest(selectedTiles);
            clearSelection();
        });

        excavate.addActionListener(e -> {
            for (Point p : selectedTiles) {
                controller.excavate(p.x, p.y);
            }
            clearSelection();
        });

        nextDay.addActionListener(e -> {
            controller.nextDay();
            clearSelection();
        });

        panel.add(plant);
        panel.add(water);
        panel.add(fertilize);
        panel.add(harvest);
        panel.add(excavate);
        panel.add(nextDay);

        add(panel, BorderLayout.SOUTH);
    }

    private void showPlantMenu() {
        String[] options = {"Turnip", "Wheat", "Potato", "Tomato", "Thyme"};

        int choice = JOptionPane.showOptionDialog(
                this,
                "Select Plant",
                "Plant Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice >= 0) {
            controller.plant(selectedTiles, choice + 1);
            clearSelection();
        }
    }

    private void toggleTile(int r, int c) {
        Point p = new Point(r, c);

        if (selectedTiles.contains(p)) {
            selectedTiles.remove(p);
            tiles[r][c].setBorder(BorderFactory.createLineBorder(Color.GRAY));
        } else {
            selectedTiles.add(p);
            tiles[r][c].setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        }
    }

    private void clearSelection() {
        for (Point p : selectedTiles) {
            tiles[p.x][p.y].setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }
        selectedTiles.clear();
    }

    public void update() {
        Field field = controller.getField();

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {

                Soil soil = field.getSoil(r, c);
                JButton tile = tiles[r][c];

                if (soil.isMeteoriteAffected()) {
                    tile.setText("M");
                    tile.setBackground(Color.RED);
                    tile.setToolTipText("Meteorite Tile");
                }

                else if (soil.hasPlant()) {

                    Plant plant = soil.getPlant();
                    GrowthStage stage = plant.getCurrentStage();

                    String name = plant.getName();
                    String label;

                    switch (name) {
                        case "Turnip": label = "TU"; break;
                        case "Tomato": label = "TO"; break;
                        case "Thyme": label = "TH"; break;
                        case "Wheat": label = "WH"; break;
                        case "Potato": label = "PO"; break;
                        default: label = name.substring(0, 1);
                    }

                    tile.setText(label);

                    if (stage instanceof SeedlingStage)
                        tile.setBackground(Color.GREEN);
                    else if (stage instanceof DormantStage)
                        tile.setBackground(Color.BLUE);
                    else if (stage instanceof EnergizingStage)
                        tile.setBackground(new Color(128, 0, 128));
                    else if (stage instanceof LowProductiveStage)
                        tile.setBackground(Color.ORANGE);
                    else if (stage instanceof HighProductiveStage)
                        tile.setBackground(Color.RED);
                    else if (stage instanceof FullyMatureStage)
                        tile.setBackground(Color.BLACK);

                    String tip = name + " - " + stage.getClass().getSimpleName();

                    if (!plant.isWatered() && stage instanceof SeedlingStage) {
                        tip += " (Needs Water)";
                    }

                    tile.setToolTipText(tip);
                }

                else {

                    String soilType = soil.getSoilType();

                    if (soilType.equalsIgnoreCase("loam")) {
                        tile.setBackground(new Color(181, 101, 29));
                    }
                    else if (soilType.equalsIgnoreCase("sand")) {
                        tile.setBackground(Color.YELLOW);
                    }
                    else if (soilType.equalsIgnoreCase("gravel")) {
                        tile.setBackground(Color.GRAY);
                    }

                    tile.setText(".");
                    tile.setToolTipText("Soil: " + soilType);
                }
            }
        }

        dayLabel.setText("Day: " + controller.getDay());
        moneyLabel.setText("Money: " + controller.getPlayer().getSavings());
    }

    public void showEndScreen(Player player) {

        String message =
                "Game Over!\n\n" +
                        "Player: " + player.getName() + "\n" +
                        "Final Money: " + player.getSavings();

        JOptionPane.showMessageDialog(this, message);

        System.exit(0);
    }
}