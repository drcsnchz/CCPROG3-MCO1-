public class Wheat extends Plant {

    public Wheat() {
        this.name = "Wheat";
        this.seedPrice = 10;
        this.cropPrice = 4;
        this.yield = 1;
        this.preferredSoil = "clay";

        this.lifecycle = new GrowthStage[] {
                new SeedlingStage(),

                new LowProductiveStage(),
                new LowProductiveStage(),
                new LowProductiveStage(),
                new LowProductiveStage(),
                new LowProductiveStage(),
                new LowProductiveStage(),

                new EnergizingStage(),
                new EnergizingStage(),
                new EnergizingStage(),

                new HighProductiveStage(),
                new HighProductiveStage(),

                new EnergizingStage(),
                new EnergizingStage(),
                new EnergizingStage(),

                new HighProductiveStage(),
                new HighProductiveStage(),

                new EnergizingStage(),
                new EnergizingStage(),
                new EnergizingStage(),

                new FullyMatureStage()
        };

        this.currentStageIndex = 0;
    }

    @Override
    public int harvest() {

        if (!canHarvest()) return 0;

        return yield * cropPrice;
    }
}