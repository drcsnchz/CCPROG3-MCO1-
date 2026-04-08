/**
 * Represents the Dormant growth stage of a plant
 *
 * - The plant progresses regardless of watering
 * - Fertilizer and soil preference have no effect
 * - The plant cannot be harvested
 */
public class DormantStage implements GrowthStage {

    /**
     * Determines if the plant can advance to the next stage
     *
     * @param isWatered indicates whether the plant was watered
     * @return true since dormant plants always advance
     */
    @Override
    public boolean canAdvance(boolean isWatered) {
        return true; // always progresses
    }

    /**
     * Determines how many stages the plant will advance
     *
     * @param isPreferredSoil indicates if the soil is preferred
     * @param hasFertilizer indicates if fertilizer is applied
     * @return number of stages to advance (always 1)
     */
    @Override
    public int getGrowthSteps(boolean isPreferredSoil, boolean hasFertilizer) {
        return 1;
    }

    /**
     * Determines if the plant can be harvested
     *
     * @return false since dormant plants cannot be harvested
     */
    @Override
    public boolean isHarvestable() {
        return false;
    }
}