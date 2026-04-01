public class HighProductiveStage implements GrowthStage {

    @Override
    public boolean canAdvance(boolean isWatered) {
        return isWatered;
    }

    @Override
    public int getGrowthSteps(boolean isPreferredSoil, boolean hasFertilizer) {
        int steps = 1;

        if (isPreferredSoil) steps++;
        if (hasFertilizer) steps++;

        return steps;
    }

    @Override
    public boolean isHarvestable() {
        return true;
    }
}