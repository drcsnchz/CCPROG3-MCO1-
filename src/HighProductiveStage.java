/**
 * Represents the High Productive stage of a plant
 *
 * - The plant produces double yield when harvested
 * - Growth behaves normally
 * - Soil and fertilizer provide standard bonuses
 */
public class HighProductiveStage implements GrowthStage {

    /**
     * Determines if the plant can advance to the next stage
     *
     * @param isWatered indicates whether the plant was watered
     * @return true if watered, false otherwise
     */
    @Override
    public boolean canAdvance(boolean isWatered) {
        return isWatered;
    }

    /**
     * Determines how many stages the plant will advance
     *
     * @param isPreferredSoil indicates if the soil is preferred
     * @param hasFertilizer indicates if fertilizer is applied
     * @return number of stages to advance
     */
    @Override
    public int getGrowthSteps(boolean isPreferredSoil, boolean hasFertilizer) {
        int steps = 1;

        if (isPreferredSoil) steps++;
        if (hasFertilizer) steps++;

        return steps;
    }

    /**
     * Determines if the plant can be harvested
     *
     * @return true since this stage produces crops
     */
    @Override
    public boolean isHarvestable() {
        return true;
    }
}