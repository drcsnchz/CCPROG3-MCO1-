/**
 * Represents the Fully Mature stage of a plant
 *
 * - The plant no longer grows
 * - It behaves like a high productive stage
 * - The plant can be harvested
 */
public class FullyMatureStage implements GrowthStage {

    /**
     * Determines if the plant can advance to the next stage
     *
     * @param isWatered indicates whether the plant was watered
     * @return false since the plant no longer grows
     */
    @Override
    public boolean canAdvance(boolean isWatered) {
        return false; // doesn't grow anymore
    }

    /**
     * Determines how many stages the plant will advance
     *
     * @param isPreferredSoil indicates if the soil is preferred
     * @param hasFertilizer indicates if fertilizer is applied
     * @return 0 since no further growth occurs
     */
    @Override
    public int getGrowthSteps(boolean isPreferredSoil, boolean hasFertilizer) {
        return 0;
    }

    /**
     * Determines if the plant can be harvested
     *
     * @return true since fully mature plants are harvestable
     */
    @Override
    public boolean isHarvestable() {
        return true;
    }
}