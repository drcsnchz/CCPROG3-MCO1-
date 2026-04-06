import java.util.ArrayList;
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

        scores = DataLoader.loadHighScores(filename);

        // Safety: if file is empty or failed to load
        if (scores == null) {
            scores = new ArrayList<HighScoreEntry>();
        }

        sortScores();
    }

    /**
     * Adds a new score and updates the leaderboard
     *
     * Rules:
     * - Only top 10 scores are kept
     * - If new score is lower than the lowest (and already 10 entries), it is not added
     *
     * @param playerName the player's name
     * @param finalSavings the player's final savings
     */
    public void addScore(String playerName, int finalSavings) {

        HighScoreEntry newEntry = new HighScoreEntry(playerName, finalSavings);

        // Case 1: Less than 10 scores → always add
        if (scores.size() < 10) {
            scores.add(newEntry);
        }
        else {
            // Make sure list is sorted first
            sortScores();

            // Get lowest score (last element)
            HighScoreEntry lowest = scores.get(scores.size() - 1);

            // Only add if better than lowest
            if (finalSavings > lowest.getFinalSavings()) {
                scores.remove(scores.size() - 1);
                scores.add(newEntry);
            }
            else {
                // Not good enough → do nothing
                return;
            }
        }

        // Re-sort after adding
        sortScores();

        // Save updated leaderboard
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
     * Gets the leaderboard
     *
     * @return list of high score entries
     */
    public List<HighScoreEntry> getScores() {
        return scores;
    }
}