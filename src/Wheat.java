public class Wheat extends Plant {

    public Wheat() {
        this.name = "Wheat";
        this.seedPrice = 40;
        this.cropPrice = 4;
        this.yield = 3;
        this.preferredSoil = "sand";

        this.lifecycle = new GrowthStage[] {
                new SeedlingStage(),
                new DormantStage(),
                new EnergizingStage(),
                new LowProductiveStage(),
                new HighProductiveStage(),
                new FullyMatureStage()
        };

        this.currentStageIndex = 0;
    }

    @Override
    public int harvest() {
        GrowthStage stage = getCurrentStage();

        if (stage instanceof LowProductiveStage) {
            return yield * cropPrice;
        }

        if (stage instanceof HighProductiveStage ||
                stage instanceof FullyMatureStage) {
            return yield * 2 * cropPrice;
        }

        return 0;
    }
}