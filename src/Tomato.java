public class Tomato extends Plant {

    public Tomato() {
        this.name = "Tomato";
        this.seedPrice = 20;
        this.cropPrice = 5;
        this.yield = 2;
        this.preferredSoil = "loam";

        this.lifecycle = new GrowthStage[] {
                new SeedlingStage(),

                new DormantStage(),
                new DormantStage(),
                new DormantStage(),
                new DormantStage(),
                new DormantStage(),
                new DormantStage(),
                new DormantStage(),
                new DormantStage(),
                new DormantStage(),
                new DormantStage(),
                new DormantStage(),
                new DormantStage(),

                new EnergizingStage(),
                new EnergizingStage(),
                new EnergizingStage(),
                new EnergizingStage(),
                new EnergizingStage(),
                new EnergizingStage(),
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