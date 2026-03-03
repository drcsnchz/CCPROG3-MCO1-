import java.util.Map;
import java.util.Scanner;

public class VerdantSunGame {

    private static final int SEASON_LENGTH = 15;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // === INITIALIZATION ===
        System.out.print("Enter player name: ");
        String name = sc.nextLine();

        Player player = new Player(name);


        String[][] layout = DataLoader.loadMap("Map.json");
        Field field = new Field(layout);

        WateringCan wateringCan = new WateringCan();

        HighScoreManager highScoreManager =
                new HighScoreManager("HighScores.json");

        // Load plant & fertilizer templates
        Map<String, Plant> plantTemplates =
                DataLoader.loadPlants("Plants.json");

        Map<String, Fertilizer> fertilizerTemplates =
                DataLoader.loadFertilizers("Fertilizers.json");

        int day = 1;
        boolean running = true;

        // === GAME LOOP ===
        while (running && day <= SEASON_LENGTH) {

            displayHeader(day, player, wateringCan);
            field.displayField();

            displayMenu();

            System.out.print("\nSelect action: ");
            int choice = readIntSafe(sc);

            switch (choice) {
                case 1:
                    handlePlantSeed(sc, player, field, plantTemplates);
                    break;

                case 2:
                    handleWaterPlant(sc, wateringCan, field);
                    break;

                case 3:
                    handleRefill(player, wateringCan);
                    break;

                case 4:
                    handleFertilizer(sc, player, field, fertilizerTemplates);
                    break;

                case 5:
                    handleRemoveHarvest(sc, player, field);
                    break;

                case 6:
                    handleExcavate(sc, player, field);
                    break;

                case 7:
                    day = nextDay(day, player, field);
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        System.out.println("\n=== SEASON ENDED ===");
        System.out.println("Final Savings: " + player.getSavings());

        // FIXED: Save high score
        highScoreManager.addScore(player.getName(),
                player.getSavings());

        sc.close();
    }

    // =====================================================
    // DISPLAY
    // =====================================================

    private static void displayHeader(int day, Player player, WateringCan can) {
        System.out.println("\n=================================");
        System.out.println("Day: " + day + " / " + SEASON_LENGTH);
        System.out.println("Savings: " + player.getSavings());
        System.out.println("Water Level: " + can.getCurrentWater()); // FIXED
        System.out.println("=================================");
    }

    private static void displayMenu() {
        System.out.println("\n=== MAIN MENU ===");
        System.out.println("1. Plant a seed");
        System.out.println("2. Water a plant");
        System.out.println("3. Refill watering can");
        System.out.println("4. Apply fertilizer");
        System.out.println("5. Remove/Harvest plant");
        System.out.println("6. Excavate meteorite");
        System.out.println("7. Next day");
        System.out.println("0. Exit");
    }

    // =====================================================
    // ACTION HANDLERS
    // =====================================================

    private static void handlePlantSeed(Scanner sc,
                                        Player player,
                                        Field field,
                                        Map<String, Plant> plantTemplates) {

        System.out.println("\nAvailable Plants:");
        int index = 1;
        Plant[] options = new Plant[plantTemplates.size()];

        for (Plant p : plantTemplates.values()) {
            if (player.getSavings() >= p.getSeedPrice()) {
                System.out.println(index + ". " + p.getName()
                        + " (Cost: " + p.getSeedPrice() + ")");
                options[index - 1] = p;
                index++;
            }
        }

        if (index == 1) {
            System.out.println("Not enough money.");
            return;
        }

        System.out.print("Select plant: ");
        int choice = readIntSafe(sc);

        if (choice <= 0 || choice >= index) {
            System.out.println("Invalid choice.");
            return;
        }

        Plant selected = new Plant(options[choice - 1]);

        System.out.print("Row (0-9): ");
        int row = readIntSafe(sc);
        System.out.print("Col (0-9): ");
        int col = readIntSafe(sc);

        Soil soil = field.getSoil(row, col);

        if (soil.hasPlant() || soil.isMeteoriteAffected()) {
            System.out.println("Cannot plant here.");
            return;
        }

        if (!player.deductMoney(selected.getSeedPrice())) {
            System.out.println("Not enough savings.");
            return;
        }

        soil.plantSeed(selected);
        System.out.println("Planted successfully.");
    }

    private static void handleWaterPlant(Scanner sc,
                                         WateringCan can,
                                         Field field) {

        if (can.getCurrentWater() <= 0) {
            System.out.println("Watering can is empty.");
            return;
        }

        System.out.print("Row (0-9): ");
        int row = readIntSafe(sc);
        System.out.print("Col (0-9): ");
        int col = readIntSafe(sc);

        Soil soil = field.getSoil(row, col);

        if (can.water(soil)) {
            System.out.println("Watered successfully.");
        } else {
            System.out.println("Cannot water this tile.");
        }
    }

    private static void handleRefill(Player player, WateringCan can) {
        if (!player.deductMoney(100)) {
            System.out.println("Not enough savings to refill.");
            return;
        }

        can.refill();
        System.out.println("Watering can refilled.");
    }

    private static void handleFertilizer(Scanner sc,
                                         Player player,
                                         Field field,
                                         Map<String, Fertilizer> fertilizerTemplates) {

        System.out.println("\nAvailable Fertilizers:");
        int index = 1;
        Fertilizer[] options =
                new Fertilizer[fertilizerTemplates.size()];

        for (Fertilizer f : fertilizerTemplates.values()) {
            if (player.getSavings() >= f.getPrice()) {
                System.out.println(index + ". " + f.getName()
                        + " (Cost: " + f.getPrice() + ")");
                options[index - 1] = f;
                index++;
            }
        }

        if (index == 1) {
            System.out.println("Not enough money.");
            return;
        }

        System.out.print("Select fertilizer: ");
        int choice = readIntSafe(sc);

        if (choice <= 0 || choice >= index) {
            System.out.println("Invalid choice.");
            return;
        }

        Fertilizer selected =
                new Fertilizer(options[choice - 1]);

        System.out.print("Row (0-9): ");
        int row = readIntSafe(sc);
        System.out.print("Col (0-9): ");
        int col = readIntSafe(sc);

        Soil soil = field.getSoil(row, col);

        if (!soil.applyFertilizer(selected)) {
            System.out.println("Cannot apply fertilizer here.");
            return;
        }

        if (!player.deductMoney(selected.getPrice())) {
            System.out.println("Not enough savings.");
            return;
        }

        System.out.println("Fertilizer applied.");
    }

    private static void handleRemoveHarvest(Scanner sc,
                                            Player player,
                                            Field field) {

        System.out.print("Row (0-9): ");
        int row = readIntSafe(sc);
        System.out.print("Col (0-9): ");
        int col = readIntSafe(sc);

        Soil soil = field.getSoil(row, col);

        if (!soil.hasPlant()) {
            System.out.println("No plant here.");
            return;
        }

        int earnings = soil.harvestPlant();

        if (earnings > 0) {
            player.addMoney(earnings);
            System.out.println("Harvested! Earned: " + earnings);
        } else {
            soil.removePlant();
            System.out.println("Plant removed.");
        }
    }

    private static void handleExcavate(Scanner sc,
                                       Player player,
                                       Field field) {

        System.out.print("Row (0-9): ");
        int row = readIntSafe(sc);
        System.out.print("Col (0-9): ");
        int col = readIntSafe(sc);

        if (!player.deductMoney(500)) {
            System.out.println("Not enough savings.");
            return;
        }

        if (field.excavateTile(row, col)) {
            System.out.println("Excavated successfully.");
        } else {
            System.out.println("Cannot excavate this tile.");
        }
    }

    // =====================================================
    // NEXT DAY ENGINE
    // =====================================================

    private static int nextDay(int currentDay,
                               Player player,
                               Field field) {

        System.out.println("\nAdvancing to next day...");


        field.nextDay();

        // Daily income
        player.addDailyIncome();

        int nextDay = currentDay + 1;

        if (currentDay == 7) {
            triggerMeteorite(field);
        }

        return nextDay;
    }

    private static void triggerMeteorite(Field field) {

        System.out.println("\n*** METEORITE EVENT OCCURRED! ***");

        field.applyMeteoriteEvent(); // FIXED
    }

    // =====================================================
    // INPUT SAFETY
    // =====================================================

    private static int readIntSafe(Scanner sc) {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Enter a valid number: ");
        }
        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }
}