public class EnergizingStage implements GrowthStage {

    @Override
    public boolean canAdvance(boolean isWatered) {
        return !isWatered; // watering STOPS growth
    }

    @Override
    public int getGrowthSteps(boolean isPreferredSoil, boolean hasFertilizer) {
        return 1;
    }

    @Override
    public boolean isHarvestable() {
        return false;
    }
}