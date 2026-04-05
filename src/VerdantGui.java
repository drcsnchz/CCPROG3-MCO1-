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
                btn.setBackground(Color.LIGHT_GRAY);

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
        JButton harvest = new JButton("Harvest");
        JButton nextDay = new JButton("Next Day");

        plant.addActionListener(e -> showPlantMenu());
        water.addActionListener(e -> {
            controller.water(selectedTiles);
            clearSelection();
        });
        harvest.addActionListener(e -> {
            controller.harvest(selectedTiles);
            clearSelection();
        });
        nextDay.addActionListener(e -> {
            controller.nextDay();
            clearSelection();
        });

        panel.add(plant);
        panel.add(water);
        panel.add(harvest);
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
            tiles[r][c].setBackground(Color.LIGHT_GRAY);
        } else {
            selectedTiles.add(p);
            tiles[r][c].setBackground(Color.YELLOW);
        }
    }

    private void clearSelection() {
        for (Point p : selectedTiles) {
            tiles[p.x][p.y].setBackground(Color.LIGHT_GRAY);
        }
        selectedTiles.clear();
    }

    public void update() {
        Field field = controller.getField();

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                Soil soil = field.getSoil(r, c);

                if (soil.isMeteoriteAffected()) {
                    tiles[r][c].setText("M");
                    tiles[r][c].setBackground(Color.RED);
                } else if (soil.hasPlant()) {
                    tiles[r][c].setText(
                            soil.getPlant().getName().substring(0, 1)
                    );
                    tiles[r][c].setBackground(Color.GREEN);
                } else {
                    tiles[r][c].setText(".");
                    tiles[r][c].setBackground(Color.LIGHT_GRAY);
                }
            }
        }

        dayLabel.setText("Day: " + controller.getDay());
        moneyLabel.setText("Money: " + controller.getPlayer().getSavings());
    }
}