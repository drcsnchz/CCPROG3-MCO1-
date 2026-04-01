public class Thyme extends Plant {

    public Thyme() {
        this.name = "Thyme";
        this.seedPrice = 80;
        this.cropPrice = 7;
        this.yield = 3;
        this.preferredSoil = "gravel";

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