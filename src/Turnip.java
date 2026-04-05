public class Turnip extends Plant {

    public Turnip() {
        this.name = "Turnip";
        this.seedPrice = 5;
        this.cropPrice = 6;
        this.yield = 1;
        this.preferredSoil = "loam";

        this.lifecycle = new GrowthStage[] {
                new SeedlingStage(),
                new SeedlingStage(),
                new SeedlingStage(),

                new DormantStage(),

                new LowProductiveStage(),
                new LowProductiveStage(),
                new LowProductiveStage(),
                new LowProductiveStage(),

                new HighProductiveStage(),

                new FullyMatureStage()
        };

        this.currentStageIndex = 0;
    }

    @Override
    public int harvest() {

        if (!canHarvest()) return 0;

        GrowthStage stage = getCurrentStage();

        if (stage instanceof LowProductiveStage) {
            return yield * 5;
        }

        if (stage instanceof HighProductiveStage) {
            return (int)(yield * cropPrice * 1.5);
        }

        if (stage instanceof FullyMatureStage) {
            return yield * cropPrice;
        }

        return 0;
    }
}