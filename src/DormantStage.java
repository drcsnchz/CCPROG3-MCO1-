public class DormantStage implements GrowthStage {

    @Override
    public boolean canAdvance(boolean isWatered) {
        return true; // always progresses
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