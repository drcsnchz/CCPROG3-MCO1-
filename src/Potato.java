public class Potato extends Plant {

    public Potato() {
        this.name = "Potato";
        this.seedPrice = 60;
        this.cropPrice = 8; // base for large potato
        this.yield = 3;
        this.preferredSoil = "loam";

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

        // LOW → small potatoes (4 gold)
        if (stage instanceof LowProductiveStage) {
            return yield * 4;
        }

        // HIGH → large potatoes +50%
        if (stage instanceof HighProductiveStage) {
            return (int)(yield * cropPrice * 1.5);
        }

        // FULLY MATURE → no bonus
        if (stage instanceof FullyMatureStage) {
            return yield * cropPrice;
        }

        return 0;
    }
}