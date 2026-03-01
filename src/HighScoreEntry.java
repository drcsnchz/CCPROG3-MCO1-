
/**
 * Represents a single high score entry
 *
 * Stores the player's name and their final savings.
 */

public class HighScoreEntry {

    private String playerName;
    private int finalSavings;

    /**
     * Constructs a HighScoreEntry
     *
     * @param playerName the name of the player
     * @param finalSavings the player's final savings
     */
    public HighScoreEntry(String playerName, int finalSavings) {
        this.playerName = playerName;
        this.finalSavings = finalSavings;
    }

    /**
     * Gets the player's name
     *
     * @return player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets the player's final savings
     *
     * @return final savings
     */
    public int getFinalSavings() {
        return finalSavings;
    }
}