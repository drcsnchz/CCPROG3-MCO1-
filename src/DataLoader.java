import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Utility class for loading and saving game data
 *
 * This class handles:
 * - Generating the map layout
 * - Loading and saving high scores
 *
 */
public class DataLoader {

    // =========================================================
    // MAP (RANDOMIZED VERSION)
    // =========================================================

    /**
     * Generates a randomized 10x10 map of soil types
     *
     * @param filename unused parameter
     * @return 2D array representing soil types
     */
    public static String[][] loadMap(String filename) {

        String[][] map = new String[10][10];
        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                int value = rand.nextInt(10);

                if (value < 5) {
                    map[i][j] = "loam";      // 50%
                }
                else if (value < 8) {
                    map[i][j] = "sand";      // 30%
                }
                else {
                    map[i][j] = "gravel";    // 20%
                }
            }
        }

        return map;
    }

    // =========================================================
    // HIGH SCORES
    // =========================================================

    /**
     * Loads high scores from a JSON file
     *
     * @param filename file path of the high score JSON
     * @return list of high score entries
     */
    public static List<HighScoreEntry> loadHighScores(String filename) {

        List<HighScoreEntry> scores = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            StringBuilder jsonBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line.trim());
            }

            String json = jsonBuilder.toString();
            json = json.substring(1, json.length() - 1);

            if (!json.isEmpty()) {

                String[] entries = json.split("},(?=\\{)");

                for (String entry : entries) {

                    entry = entry.replaceAll("[{}\"]", "");
                    String[] fields = entry.split(",");

                    String name = "";
                    int score = 0;

                    for (String field : fields) {

                        String[] keyValue = field.split(":");
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();

                        if (key.equals("name")) {
                            name = value;
                        } else if (key.equals("score")) {
                            score = Integer.parseInt(value);
                        }
                    }

                    scores.add(new HighScoreEntry(name, score));
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading HighScores.json");
        }

        return scores;
    }

    /**
     * Saves high scores to a JSON file
     *
     * @param filename file path of the high score JSON
     * @param scores list of high score entries
     */
    public static void saveHighScores(String filename, List<HighScoreEntry> scores) {

        try (FileWriter writer = new FileWriter(filename)) {

            writer.write("[\n");

            for (int i = 0; i < scores.size(); i++) {

                HighScoreEntry entry = scores.get(i);

                writer.write("  {\"name\":\"" + entry.getPlayerName() +
                        "\",\"score\":" + entry.getFinalSavings() + "}");

                if (i < scores.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]");

        } catch (IOException e) {
            System.out.println("Error saving HighScores.json");
        }
    }
}