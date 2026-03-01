import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Manages the high score leaderboard
 *
 * Responsible for:
 * - Adding new scores
 * - Sorting scores
 * - Keeping only the top 10
 * - Saving updated scores
 */
public class HighScoreManager {

    private List<HighScoreEntry> scores;
    private String filename;

    /**
     * Constructs a HighScoreManager and loads existing scores
     *
     * @param filename the HighScores.json file path
     */
    public HighScoreManager(String filename) {
        this.filename = filename;
        this.scores = DataLoader.loadHighScores(filename);
    }

    /**
     * Adds a new score and updates the leaderboard
     *
     * @param playerName the player's name
     * @param finalSavings the player's final savings
     */
    public void addScore(String playerName, int finalSavings) {

        scores.add(new HighScoreEntry(playerName, finalSavings));

        sortScores();
        keepTop10();

        DataLoader.saveHighScores(filename, scores);
    }

    /**
     * Sorts scores in descending order
     */
    private void sortScores() {
        Collections.sort(scores, new Comparator<HighScoreEntry>() {
            public int compare(HighScoreEntry a, HighScoreEntry b) {
                return Integer.compare(b.getFinalSavings(), a.getFinalSavings());
            }
        });
    }

    /**
     * Keeps only the top 10 scores
     */
    private void keepTop10() {
        if (scores.size() > 10) {
            scores = scores.subList(0, 10);
        }
    }

    /**
     * Gets the leaderboard
     *
     * @return list of high score entries
     */
    public List<HighScoreEntry> getScores() {
        return scores;
    }
}