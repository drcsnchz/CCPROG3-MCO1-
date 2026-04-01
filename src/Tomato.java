public class Tomato extends Plant {

    public Tomato() {
        this.name = "Tomato";
        this.seedPrice = 70;
        this.cropPrice = 5;
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