import java.util.Scanner;

public class VerdantSunGame {

    private static final int SEASON_LENGTH = 15;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        showWelcomeScreen(sc);

        System.out.print("\nEnter player name: ");
        String name = sc.nextLine();

        Player player = new Player(name);

        String[][] layout = DataLoader.loadMap("Map.json");
        Field field = new Field(layout);

        WateringCan wateringCan = new WateringCan();

        HighScoreManager highScoreManager =
                new HighScoreManager("HighScores.json");



        int day = 1;
        boolean running = true;

        while (running && day <= SEASON_LENGTH) {

            displayHeader(day, player, wateringCan);
            field.displayField();
            displayMenu();

            System.out.print("\nSelect action: ");
            int choice = readIntSafe(sc);

            switch (choice) {
                case 1:
                    handlePlantSeed(sc, player, field);
                    break;

                case 2:
                    handleWaterPlant(sc, wateringCan, field);
                    break;

                case 3:
                    handleRefill(player, wateringCan);
                    break;

                case 4:
                    handleFertilizer(sc, player, field,
                            DataLoader.loadFertilizers("Fertilizers.json"));
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

        showEndScreen(player);

        highScoreManager.addScore(player.getName(),
                player.getSavings());

        sc.close();
    }

    // =====================================================
    // WELCOME SCREEN
    // =====================================================

    private static void showWelcomeScreen(Scanner sc) {
        System.out.println("=================================================");
        System.out.println("                 VERDANT SUN");
        System.out.println("=================================================");
        System.out.println("           Seasonal Farming Simulation");
        System.out.println("           Grow crops, Manage resources!");
        System.out.println("         By Airam Tumbocon & Deric Sanchez");
        System.out.println("=================================================");
        System.out.print("Press ENTER to begin...");
        sc.nextLine();
    }

    // =====================================================
    // DISPLAY
    // =====================================================

    private static void displayHeader(int day, Player player, WateringCan can) {

        System.out.println();
        System.out.println("=================================================");
        System.out.printf(" Day %d / %d%n", day, SEASON_LENGTH);
        System.out.println("-------------------------------------------------");
        System.out.printf(" Savings     : %,d%n", player.getSavings());
        System.out.printf(" Water Level : %d%n", can.getCurrentWater());
        System.out.println("=================================================");
    }

    private static void displayMenu() {

        System.out.println("\n============== MAIN MENU =============");
        System.out.println(" 1. Plant a Seed");
        System.out.println(" 2. Water a Plant");
        System.out.println(" 3. Refill Watering Can (100)");
        System.out.println(" 4. Apply Fertilizer");
        System.out.println(" 5. Remove / Harvest Plant");
        System.out.println(" 6. Excavate Meteorite (500)");
        System.out.println(" 7. Next Day");
        System.out.println(" 0. Exit Game");
        System.out.println("=======================================");
    }

    // =====================================================
    // ACTION HANDLERS
    // =====================================================

    private static void handlePlantSeed(Scanner sc,
                                        Player player,
                                        Field field) {


        Plant[] options = {
                new Plant("Turnip", 50, 5, 3, 5, "loam"),
                new Plant("Wheat", 40, 4, 3, 5, "sand")
        };

        System.out.println("\nAvailable Plants:");

        for (int i = 0; i < options.length; i++) {
            if (player.getSavings() >= options[i].getSeedPrice()) {
                System.out.println((i + 1) + ". " + options[i].getName()
                        + " (" + options[i].getSeedPrice() + ")");
            }
        }

        System.out.print("Select plant: ");
        int choice = readIntSafe(sc);

        if (choice <= 0 || choice > options.length) {
            System.out.println("Invalid selection.");
            return;
        }

        Plant selected = new Plant(options[choice - 1]);

        System.out.print("Row (0-9): ");
        int row = readIntSafe(sc);
        System.out.print("Col (0-9): ");
        int col = readIntSafe(sc);

        Soil soil = field.getSoil(row, col);

        if (soil.hasPlant() || soil.isMeteoriteAffected()) {
            System.out.println("Cannot plant on this tile.");
            return;
        }

        if (!player.deductMoney(selected.getSeedPrice())) {
            System.out.println("Not enough savings.");
            return;
        }

        soil.plantSeed(selected);
        System.out.println("Plant successfully placed.");
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
            System.out.println("Plant watered successfully.");
        } else {
            System.out.println("Cannot water this tile.");
        }
    }

    private static void handleRefill(Player player, WateringCan can) {

        if (!player.deductMoney(100)) {
            System.out.println("Not enough savings.");
            return;
        }

        can.refill();
        System.out.println("Watering can refilled.");
    }

    private static void handleFertilizer(Scanner sc,
                                         Player player,
                                         Field field,
                                         java.util.Map<String, Fertilizer> fertilizerTemplates) {

        System.out.println("\nAvailable Fertilizers:");

        int index = 1;
        Fertilizer[] options =
                new Fertilizer[fertilizerTemplates.size()];

        for (Fertilizer f : fertilizerTemplates.values()) {
            if (player.getSavings() >= f.getPrice()) {
                System.out.println(index + ". " + f.getName()
                        + " (" + f.getPrice() + ")");
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
            System.out.println("Invalid selection.");
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
            System.out.println("Harvested. Earned " + earnings);
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

        if (!field.excavateTile(row, col)) {
            System.out.println("Cannot excavate this tile.");
            return;
        }

        if (!player.deductMoney(500)) {
            System.out.println("Not enough savings.");
            return;
        }

        System.out.println("Tile excavated.");
    }

    // =====================================================
    // NEXT DAY ENGINE
    // =====================================================

    private static int nextDay(int currentDay,
                               Player player,
                               Field field) {

        System.out.println("\nAdvancing to next day...");

        field.nextDay();
        player.addDailyIncome();

        if (currentDay == 7) {
            triggerMeteorite(field);
        }

        return currentDay + 1;
    }

    private static void triggerMeteorite(Field field) {

        System.out.println("\nMeteorite event occurred.");
        field.applyMeteoriteEvent();
    }

    // =====================================================
    // END SCREEN
    // =====================================================

    private static void showEndScreen(Player player) {

        System.out.println();
        System.out.println("=================================================");
        System.out.println("                SEASON COMPLETE");
        System.out.println("=================================================");
        System.out.printf(" Final Savings: %,d%n", player.getSavings());
        System.out.println("=================================================");
        System.out.println(" Thank you for playing Verdant Sun.");
        System.out.println("=================================================");
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