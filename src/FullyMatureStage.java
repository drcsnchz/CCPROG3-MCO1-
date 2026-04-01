public class FullyMatureStage implements GrowthStage {

    @Override
    public boolean canAdvance(boolean isWatered) {
        return false; // no more growth
    }

    @Override
    public int getGrowthSteps(boolean isPreferredSoil, boolean hasFertilizer) {
        return 0;
    }

    @Override
    public boolean isHarvestable() {
        return true;
    }
}