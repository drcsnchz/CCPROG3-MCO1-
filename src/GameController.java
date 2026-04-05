import java.awt.Point;
import java.util.List;

public class GameController {

    private Field field;
    private Player player;
    private WateringCan wateringCan;
    private int day;

    private VerdantGUI gui;

    public GameController() {
        String[][] layout = DataLoader.loadMap("Map.json");
        field = new Field(layout);

        player = new Player("Player");
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

            if (player.deductMoney(plant.getSeedPrice())) {
                soil.plantSeed(plant);
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

    public void harvest(List<Point> tiles) {
        for (Point p : tiles) {
            Soil soil = field.getSoil(p.x, p.y);
            int earnings = soil.harvestPlant();
            player.addMoney(earnings);
        }
        updateView();
    }

    public void nextDay() {
        field.nextDay();
        player.addDailyIncome();

        if (day == 15) {
            field.applyMeteoriteEvent();
        }

        day++;
        updateView();
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