public class Potato extends Plant {

    public Potato() {
        this.name = "Potato";
        this.seedPrice = 15;
        this.cropPrice = 4;
        this.yield = 2;
        this.preferredSoil = "clay";

        this.lifecycle = new GrowthStage[] {
                new SeedlingStage(),
                new SeedlingStage(),
                new SeedlingStage(),
                new SeedlingStage(),
                new SeedlingStage(),

                new EnergizingStage(),
                new DormantStage(),
                new LowProductiveStage(),

                new EnergizingStage(),
                new DormantStage(),
                new LowProductiveStage(),

                new EnergizingStage(),
                new DormantStage(),
                new LowProductiveStage(),

                new EnergizingStage(),
                new DormantStage(),
                new LowProductiveStage(),

                new EnergizingStage(),
                new DormantStage(),
                new HighProductiveStage(),

                new EnergizingStage(),
                new DormantStage(),
                new HighProductiveStage(),

                new EnergizingStage(),
                new DormantStage(),
                new HighProductiveStage(),

                new EnergizingStage(),
                new DormantStage(),
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
            return yield * cropPrice;
        }

        if (stage instanceof HighProductiveStage ||
                stage instanceof FullyMatureStage) {
            return yield * 8;
        }

        return 0;
    }
}