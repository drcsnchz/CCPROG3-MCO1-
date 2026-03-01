
/**
 * Represents the watering can used by the player
 *
 * The watering can has limited water per day
 */

public class WateringCan {

    private static final int MAX_WATER = 10;
    private int currentWater;

    /**
     * Constructs a watering can with full capacity
     */
    public WateringCan() {
        currentWater = MAX_WATER;
    }

    /**
     * Attempts to water a soil tile
     *
     * @param soil the soil to water
     * @return true if watering was successful
     */
    public boolean water(Soil soil) {

        if (currentWater <= 0) {
            return false;
        }

        if (soil.waterPlant()) {
            currentWater--;
            return true;
        }

        return false;
    }

    /**
     * Refills the watering can
     * Called at the start of a new day
     */
    public void refill() {
        currentWater = MAX_WATER;
    }

    /**
     * Gets remaining water
     *
     * @return remaining water
     */
    public int getCurrentWater() {
        return currentWater;
    }
}