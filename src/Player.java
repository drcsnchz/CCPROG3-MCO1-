
//Represents the player of the game
 /**
 * The player has a name and savings which are used
 * to perform actions such as planting, watering,
 * fertilizing, and excavation
 */

public class Player {

    private String name;
    private int savings;

    /**
     * Constructs a Player with the given name
     * Starting savings is set to 1000 as required by game rules
     *
     * @param name the player's name
     */
    public Player(String name) {
        this.name = name;
        this.savings = 1000;
    }


    public String getName() {
        return name;
    }


    public int getSavings() {
        return savings;
    }


     // Adds money to the player's savings.

     /**
     * Pre-condition: amount must be positive
     * Post-condition: savings increases if valid
     *
     * @param amount the amount to add
     */
    public void addMoney(int amount) {
        if (amount > 0) {
            savings += amount;
        }
    }


     //Attempts to deduct money from the player's savings

     /**
     * Pre-condition: amount must be positive
     * Post-condition: savings decreases only if sufficient funds exist
     *
     * @param amount the amount to deduct
     * @return true if deduction successful, false otherwise
     */
    public boolean deductMoney(int amount) {
        if (amount > 0 && savings >= amount) {
            savings -= amount;
            return true;
        }
        return false;
    }


    public void addDailyIncome() {
        savings += 50;
    }


     /** Returns a formatted string representation of the player
     *
     * @return string containing player name and savings
     */
    public String toString() {
        return "Player: " + name + " | Savings: " + savings;
    }
}