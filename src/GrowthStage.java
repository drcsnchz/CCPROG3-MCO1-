/**
 * Interface representing a plant's growth stage
 *
 * Each stage defines:
 * - Whether the plant can advance
 * - How many stages it advances
 * - Whether it can be harvested
 */
public interface GrowthStage {

    /**
     * Determines if the plant can advance to the next stage
     *
     * @param isWatered indicates whether the plant was watered
     * @return true if the plant can advance, false otherwise
     */
    boolean canAdvance(boolean isWatered);

    /**
     * Determines how many stages the plant will advance
     *
     * @param isPreferredSoil indicates if the soil is preferred
     * @param hasFertilizer indicates if fertilizer is applied
     * @return number of stages to advance
     */
    int getGrowthSteps(boolean isPreferredSoil, boolean hasFertilizer);

    /**
     * Determines if the plant can be harvested
     *
     * @return true if harvestable, false otherwise
     */
    boolean isHarvestable();
}