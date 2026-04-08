/**
 * Represents the watering can used by the player
 *
 * The watering can:
 * - Has limited water capacity
 * - Can water plants
 * - Refills daily
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
     * Waters a soil tile
     *
     * @param soil the soil to water
     * @return true if successful
     */
    public boolean water(Soil soil) {

        if (currentWater <= 0) {
            return false;
        }

        if (soil.hasPlant()) {
            soil.getPlant().water();
            currentWater--;
            return true;
        }

        return false;
    }

    /**
     * Refills the watering can
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