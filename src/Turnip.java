public class Turnip extends Plant {

    public Turnip() {
        this.name = "Turnip";
        this.seedPrice = 50;
        this.cropPrice = 6; // base price (for tuber)
        this.yield = 3;
        this.preferredSoil = "loam";

        this.lifecycle = new GrowthStage[] {
                new SeedlingStage(),
                new SeedlingStage(),
                new DormantStage(),
                new LowProductiveStage(),
                new HighProductiveStage(),
                new FullyMatureStage()
        };

        this.currentStageIndex = 0;
    }

    @Override
    public int harvest() {
        GrowthStage stage = getCurrentStage();

        // LOW PRODUCTIVE → Turnip Tops (5 gold)
        if (stage instanceof LowProductiveStage) {
            return yield * 5;
        }

        // HIGH PRODUCTIVE → Turnip Tuber (6 gold + 50%)
        if (stage instanceof HighProductiveStage) {
            return (int)(yield * cropPrice * 1.5);
        }

        // FULLY MATURE → NO 50% BONUS
        if (stage instanceof FullyMatureStage) {
            return yield * cropPrice;
        }

        return 0;
    }
}