/**
 * Represents the player of the game
 *
 * The player has a name and savings used for game actions
 */
public class Player {

    private String name;
    private int savings;

    /**
     * Constructs a Player
     *
     * @param name player name
     */
    public Player(String name) {
        this.name = name;
        this.savings = 1000;
    }

    /**
     * Gets player name
     *
     * @return player name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets player savings
     *
     * @return savings amount
     */
    public int getSavings() {
        return savings;
    }

    /**
     * Adds money to savings
     *
     * @param amount amount to add
     */
    public void addMoney(int amount) {
        if (amount > 0) {
            savings += amount;
        }
    }

    /**
     * Deducts money from savings
     *
     * @param amount amount to deduct
     * @return true if successful
     */
    public boolean deductMoney(int amount) {
        if (amount > 0 && savings >= amount) {
            savings -= amount;
            return true;
        }
        return false;
    }

    /**
     * Adds daily income
     */
    public void addDailyIncome() {
        savings += 50;
    }
}