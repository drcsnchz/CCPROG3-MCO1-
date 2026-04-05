public abstract class Plant {

    protected String name;
    protected int seedPrice;
    protected int cropPrice;
    protected int yield;
    protected String preferredSoil;

    protected boolean watered;

    protected GrowthStage[] lifecycle;
    protected int currentStageIndex;

    public Plant() {
        this.watered = false;
        this.currentStageIndex = 0;
    }

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

    public String getInfo() {
        return name + " | Prefers: " + preferredSoil +
                " | Seed: " + seedPrice +
                " | Sell: " + cropPrice;
    }

    public abstract int harvest();
}