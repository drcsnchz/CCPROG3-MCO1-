public class Thyme extends Plant {

    public Thyme() {
        this.name = "Thyme";
        this.seedPrice = 30;
        this.cropPrice = 7;
        this.yield = 2;
        this.preferredSoil = "loam";

        this.lifecycle = new GrowthStage[] {

                new SeedlingStage(),
                new SeedlingStage(),
                new SeedlingStage(),
                new SeedlingStage(),
                new SeedlingStage(),
                new SeedlingStage(),
                new SeedlingStage(),
                new SeedlingStage(),
                new SeedlingStage(),
                new SeedlingStage(),
                new SeedlingStage(),
                new SeedlingStage(),

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