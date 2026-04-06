import javax.swing.JOptionPane;
import java.awt.Point;
import java.util.List;

public class GameController {

    private Field field;
    private Player player;
    private WateringCan wateringCan;
    private int day;

    private VerdantGUI gui;

    private boolean gameEnded;

    public GameController(String name) {

        if (name == null || name.trim().isEmpty()) {
            name = "Player";
        }

        String[][] layout = DataLoader.loadMap("Map.json");
        field = new Field(layout);

        player = new Player(name);
        wateringCan = new WateringCan();
        day = 1;

        gameEnded = false;

        gui = new VerdantGUI(this);

        updateView();
    }

    public Field getField() { return field; }
    public Player getPlayer() { return player; }
    public int getDay() { return day; }
    public WateringCan getWateringCan() { return wateringCan; }

    // =========================
    // ACTIONS (BLOCK AFTER END)
    // =========================

    public void plant(List<Point> tiles, int plantType) {
        if (gameEnded) return;

        for (Point p : tiles) {
            Soil soil = field.getSoil(p.x, p.y);

            if (soil.hasPlant() || soil.isMeteoriteAffected()) continue;

            Plant plant = createPlant(plantType);

            if (player.deductMoney(plant.getSeedPrice())) {
                soil.setPlant(plant);
            }
        }
        updateView();
    }

    public void water(List<Point> tiles) {
        if (gameEnded) return;

        for (Point p : tiles) {
            Soil soil = field.getSoil(p.x, p.y);
            wateringCan.water(soil);
        }
        updateView();
    }

    public void harvest(List<Point> tiles) {
        if (gameEnded) return;

        for (Point p : tiles) {
            Soil soil = field.getSoil(p.x, p.y);

            if (!soil.hasPlant() || soil.isMeteoriteAffected()) continue;

            int earnings = soil.getPlant().harvest();
            player.addMoney(earnings);
            soil.removePlant();
        }
        updateView();
    }

    public void fertilize(List<Point> tiles) {
        if (gameEnded) return;

        for (Point p : tiles) {
            Soil soil = field.getSoil(p.x, p.y);

            if (soil.isMeteoriteAffected()) continue;

            Fertilizer f = new Fertilizer("Basic", 100, 3);

            if (player.getSavings() >= f.getPrice()) {
                if (soil.applyFertilizer(new Fertilizer(f))) {
                    player.deductMoney(f.getPrice());
                }
            }
        }
        updateView();
    }

    public void excavate(int row, int col) {
        if (gameEnded) return;

        int cost = 500;

        Soil soil = field.getSoil(row, col);

        if (!soil.isMeteoriteAffected()) return;

        if (player.getSavings() >= cost) {
            soil.excavate();
            player.deductMoney(cost);
        }

        updateView();
    }

    // =========================
    // DAY PROGRESSION (FIXED)
    // =========================

    public void nextDay() {

        if (gameEnded) return;

        field.nextDay();
        player.addDailyIncome();

        if (day == 15) {
            field.applyMeteoriteEvent();
        }

        day++;


        if (day > 20) {
            endGame();
            return;
        }

        wateringCan.refill();
        updateView();
    }

    // =========================
    // END GAME
    // =========================

    private void endGame() {

        if (gameEnded) return;
        gameEnded = true;

        HighScoreManager manager = new HighScoreManager("HighScores.json");
        manager.addScore(player.getName(), player.getSavings());

        // 👉 SHOW END SCREEN
        new EndScreen(player, manager.getScores());

        gui.dispose(); // close game window
    }

    // =========================

    private Plant createPlant(int type) {
        if (type == 1) return new Turnip();
        if (type == 2) return new Wheat();
        if (type == 3) return new Potato();
        if (type == 4) return new Tomato();
        if (type == 5) return new Thyme();
        return null;
    }

    private void updateView() {
        gui.update();
    }
}