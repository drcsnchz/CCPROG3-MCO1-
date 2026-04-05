public interface GrowthStage {

    boolean canAdvance(boolean isWatered);

    int getGrowthSteps(boolean isPreferredSoil, boolean hasFertilizer);

    boolean isHarvestable();
}