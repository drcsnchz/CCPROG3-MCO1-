/**
 * Represents the Seedling stage of a plant
 *
 * - The plant must be watered to grow
 * - Growth bonuses from soil and fertilizer are doubled
 * - The plant cannot be harvested
 */
public class SeedlingStage implements GrowthStage {

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
     * @return number of stages to advance (bonuses doubled)
     */
    @Override
    public int getGrowthSteps(boolean isPreferredSoil, boolean hasFertilizer) {
        int steps = 1;

        if (isPreferredSoil) steps += 2;
        if (hasFertilizer) steps += 2;

        return steps;
    }

    /**
     * Determines if the plant can be harvested
     *
     * @return false since seedlings cannot be harvested
     */
    @Override
    public boolean isHarvestable() {
        return false;
    }
}