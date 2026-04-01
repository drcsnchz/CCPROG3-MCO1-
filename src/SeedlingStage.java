public class SeedlingStage implements GrowthStage {

    @Override
    public boolean canAdvance(boolean isWatered) {
        return isWatered;
    }

    @Override
    public int getGrowthSteps(boolean isPreferredSoil, boolean hasFertilizer) {
        int steps = 1;

        if (isPreferredSoil) steps += 2; // doubled
        if (hasFertilizer) steps += 2;   // doubled

        return steps;
    }

    @Override
    public boolean isHarvestable() {
        return false;
    }
}