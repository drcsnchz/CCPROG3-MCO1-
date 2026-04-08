/**
 * Abstract base class representing a plant in the game
 *
 * A plant:
 * - Has a lifecycle consisting of multiple growth stages
 * - Can be watered and progress through stages
 * - Produces crops depending on its current stage
 *
 */
public abstract class Plant {

    protected String name;
    protected int seedPrice;
    protected int cropPrice;
    protected int yield;
    protected String preferredSoil;

    protected boolean watered;

    protected GrowthStage[] lifecycle;
    protected int currentStageIndex;

    /**
     * Initializes the plant at the first stage and not watered.
     */
    public Plant() {
        this.watered = false;
        this.currentStageIndex = 0;
    }

    /**
     * Copy constructor
     *
     * @param other the plant to copy
     */
    public Plant(Plant other) {
        this.name = other.name;
        this.seedPrice = other.seedPrice;
        this.cropPrice = other.cropPrice;
        this.yield = other.yield;
        this.preferredSoil = other.preferredSoil;
        this.lifecycle = other.lifecycle;

        this.currentStageIndex = 0;
        this.watered = false;
    }

    /**
     * Gets the plant name
     *
     * @return plant name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the seed price
     *
     * @return seed price
     */
    public int getSeedPrice() {
        return seedPrice;
    }

    /**
     * Gets the crop price
     * @return crop price
     */
    public int getCropPrice() {
        return cropPrice;
    }

    /**
     * Gets the yield of the plant
     *
     * @return yield amount
     */
    public int getYield() {
        return yield;
    }

    /**
     * Gets the preferred soil type
     *
     * @return preferred soil
     */
    public String getPreferredSoil() {
        return preferredSoil;
    }

    /**
     * Checks if the plant is wateref
     *
     * @return true if watered, false otherwise
     */
    public boolean isWatered() {
        return watered;
    }

    /**
     * Gets the current growth stage
     *
     * @return current GrowthStage
     */
    public GrowthStage getCurrentStage() {
        return lifecycle[currentStageIndex];
    }

    /**
     * Gets the current stage index
     *
     * @return stage index
     */
    public int getStageIndex() {
        return currentStageIndex;
    }

    /**
     * Waters the plant
     */
    public void water() {
        if (!watered) {
            watered = true;
        }
    }

    /**
     * Resets watered status at the end of the day
     */
    public void resetWatered() {
        watered = false;
    }

    /**
     * Advances the plant's growth stage
     *
     * @param soil the soil the plant is planted in
     */
    public void advanceStage(Soil soil) {

        GrowthStage stage = getCurrentStage();

        boolean preferred =
                soil.getSoilType().equalsIgnoreCase(preferredSoil);
        boolean fertilized = soil.hasFertilizer();

        if (!stage.canAdvance(watered)) {
            return;
        }

        int steps = stage.getGrowthSteps(preferred, fertilized);

        currentStageIndex += steps;

        if (currentStageIndex >= lifecycle.length) {
            currentStageIndex = lifecycle.length - 1;
        }
    }

    /**
     * Checks if the plant can be harvested
     *
     * @return true if harvestable, false otherwise
     */
    public boolean canHarvest() {

        GrowthStage stage = getCurrentStage();

        return (stage instanceof LowProductiveStage ||
                stage instanceof HighProductiveStage ||
                stage instanceof FullyMatureStage);
    }

    /**
     * Computes the harvest value based on the current stage
     *
     * @return harvest value
     */
    protected int computeHarvestValue() {

        GrowthStage stage = getCurrentStage();

        if (stage instanceof LowProductiveStage) {
            return yield * cropPrice;
        }
        else if (stage instanceof HighProductiveStage) {
            return (yield * 2) * cropPrice;
        }
        else if (stage instanceof FullyMatureStage) {
            return (yield * 2) * cropPrice;
        }

        return 0;
    }

    /**
     * Gets display information about the plant
     *
     * @return formatted plant info string
     */
    public String getInfo() {
        return name + " | Prefers: " + preferredSoil +
                " | Seed: " + seedPrice +
                " | Sell: " + cropPrice;
    }

    /**
     * Harvests the plant
     *
     * @return earnings from harvest
     */
    public int harvest() {

        if (!canHarvest()) {
            return 0;
        }

        return computeHarvestValue();
    }
}