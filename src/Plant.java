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

    // Stage-based system
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

    // =====================================================
    // GETTERS
    // =====================================================

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

    /**
     * Returns the current growth stage object
     */
    public GrowthStage getCurrentStage() {
        return lifecycle[currentStageIndex];
    }

    // =====================================================
    // WATERING
    // =====================================================

    /**
     * Waters the plant (only if not already watered)
     */
    public void water() {
        if (!watered) {
            watered = true;
        }
    }

    /**
     * Resets watered state at end of day
     */
    public void resetWatered() {
        watered = false;
    }

    // =====================================================
    // STAGE PROGRESSION
    // =====================================================

    /**
     * Advances the plant through its lifecycle based on stage rules
     *
     * @param soil the soil the plant is planted on
     */
    public void advanceStage(Soil soil) {

        GrowthStage stage = getCurrentStage();

        boolean preferred =
                soil.getSoilType().equalsIgnoreCase(preferredSoil);
        boolean fertilized = soil.hasFertilizer();

        // Check if stage allows progression
        if (!stage.canAdvance(watered)) {
            return;
        }

        // Determine how many stages to move
        int steps = stage.getGrowthSteps(preferred, fertilized);

        currentStageIndex += steps;

        // Prevent overflow beyond final stage
        if (currentStageIndex >= lifecycle.length) {
            currentStageIndex = lifecycle.length - 1;
        }
    }

    // =====================================================
    // HARVEST (ABSTRACT)
    // =====================================================

    /**
     * Harvest behavior depends on plant type
     * (e.g., root crops vs normal crops)
     *
     * @return money earned from harvest
     */
    public abstract int harvest();

    protected boolean canHarvest() {
        return getCurrentStage().isHarvestable();
    }
}