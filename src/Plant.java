/**
 * Abstract base class for all plants in the Verdant Sun simulator
 * Instead, they progress through a sequence of GrowthStage objects
 *
 * Each subclass defines:
 * - its lifecycle
 * - its own harvest behavior
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
     * Default constructor
     * Initializes plant as not watered and at first stage
     */
    public Plant() {
        this.watered = false;
        this.currentStageIndex = 0;
    }

    /**
     * Copy constructor
     * Ensures planted instances have independent state
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

    public String getName() {
        return name;
    }

    public int getSeedPrice() {
        return seedPrice;
    }

    public int getCropPrice() {
        return cropPrice;
    }

    public int getYield() {
        return yield;
    }

    public String getPreferredSoil() {
        return preferredSoil;
    }

    public boolean isWatered() {
        return watered;
    }

    public GrowthStage getCurrentStage() {
        return lifecycle[currentStageIndex];
    }

    public int getStageIndex() {
        return currentStageIndex;
    }

    public void water() {
        if (!watered) {
            watered = true;
        }
    }

    public void resetWatered() {
        watered = false;
    }

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
     * Determines if the plant can be harvested based on its stage
     */
    public boolean canHarvest() {

        GrowthStage stage = getCurrentStage();

        return (stage instanceof LowProductiveStage ||
                stage instanceof HighProductiveStage ||
                stage instanceof FullyMatureStage);
    }

    /**
     * Computes earnings based on stage multipliers
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
     * Provides display info for UI
     */
    public String getInfo() {
        return name + " | Prefers: " + preferredSoil +
                " | Seed: " + seedPrice +
                " | Sell: " + cropPrice;
    }

    /**
     * Harvest behavior (shared logic)
     */
    public int harvest() {

        if (!canHarvest()) {
            return 0;
        }

        return computeHarvestValue();
    }
}