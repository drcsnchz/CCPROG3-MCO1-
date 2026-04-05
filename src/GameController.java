import javax.swing.JOptionPane;
import java.awt.Point;
import java.util.List;

public class GameController {

    private Field field;
    private Player player;
    private WateringCan wateringCan;
    private int day;

    private VerdantGUI gui;

    public GameController() {

        String name = JOptionPane.showInputDialog(null, "Enter your name:");

        if (name == null || name.trim().isEmpty()) {
            name = "Player";
        }

        String[][] layout = DataLoader.loadMap("Map.json");
        field = new Field(layout);

        player = new Player(name);
        wateringCan = new WateringCan();
        day = 1;

        gui = new VerdantGUI(this);

        updateView();
    }

    public Field getField() { return field; }
    public Player getPlayer() { return player; }
    public int getDay() { return day; }
    public WateringCan getWateringCan() { return wateringCan; }

    public void plant(List<Point> tiles, int plantType) {
        for (Point p : tiles) {
            Soil soil = field.getSoil(p.x, p.y);

            if (soil.hasPlant() || soil.isMeteoriteAffected()) continue;

            Plant plant = createPlant(plantType);

            if (plant != null && player.deductMoney(plant.getSeedPrice())) {
                soil.setPlant(plant);
            }
        }
        updateView();
    }

    public void water(List<Point> tiles) {
        for (Point p : tiles) {
            Soil soil = field.getSoil(p.x, p.y);
            wateringCan.water(soil);
        }
        updateView();
    }

    public void fertilize(List<Point> tiles) {
        for (Point p : tiles) {
            Soil soil = field.getSoil(p.x, p.y);

            if (!soil.isMeteoriteAffected()) {

                Fertilizer fertilizer = new Fertilizer("Basic Fertilizer", 100, 3);

                if (player.deductMoney(fertilizer.getPrice())) {
                    soil.applyFertilizer(fertilizer);
                }
            }
        }
        updateView();
    }

    public void harvest(List<Point> tiles) {
        for (Point p : tiles) {
            Soil soil = field.getSoil(p.x, p.y);

            if (soil.hasPlant()) {
                Plant plant = soil.getPlant();
                int earnings = plant.harvest();

                if (earnings > 0) {
                    player.addMoney(earnings);
                }

                soil.removePlant();
            }
        }
        updateView();
    }

    public void excavate(int row, int col) {

        int cost = 500;

        if (player.getSavings() >= cost) {

            boolean success = field.excavateTile(row, col);

            if (success) {
                player.deductMoney(cost);
            }
        }

        updateView();
    }

    public void nextDay() {

        if (day >= 20) {
            endGame();
            return;
        }

        field.nextDay();
        player.addDailyIncome();
        wateringCan.refill();

        if (day == 15) {
            field.applyMeteoriteEvent();
        }

        day++;

        if (day > 20) {
            endGame();
            return;
        }

        updateView();
    }

    private void endGame() {

        HighScoreManager manager = new HighScoreManager("HighScores.json");
        manager.addScore(player.getName(), player.getSavings());

        gui.showEndScreen(player);
    }

    private Plant createPlant(int type) {
        switch (type) {
            case 1: return new Turnip();
            case 2: return new Wheat();
            case 3: return new Potato();
            case 4: return new Tomato();
            case 5: return new Thyme();
        }
        return null;
    }

    private void updateView() {
        gui.update();
    }
}