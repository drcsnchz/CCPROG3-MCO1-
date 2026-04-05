/**
 * Represents a single tile of soil in the field
 *
 * Updates:
 * - Uses stage-based plant system
 * - Delegates growth to Plant.advanceStage()
 * - Handles fertilizer reduction rules per stage
 */

public class Soil {

    private String soilType;
    private Plant plant;
    private Fertilizer fertilizer;
    private boolean meteoriteAffected;
    private String originalSoilType;
    private boolean fertilized;
    private int fertilizerDays;z

    /**
     * Constructs a Soil tile with a given soil type
     */
    public Soil(String soilType) {
        this.soilType = soilType;
        this.originalSoilType = soilType;
        this.plant = null;
        this.fertilizer = null;
        this.meteoriteAffected = false;
    }

    // =====================================================
    // GETTERS
    // =====================================================

    public String getSoilType() {
        return soilType;
    }

    public boolean hasPlant() {
        return plant != null;
    }

    public boolean hasFertilizer() {
        return fertilizer != null || fertilized;
    }

    public boolean isMeteoriteAffected() {
        return meteoriteAffected;
    }

    public Plant getPlant() {
        return plant;
    }

    // =====================================================
    // PLANT ACTIONS
    // =====================================================

    /**
     * Plants a seed on this tile
     */
    public boolean plantSeed(Plant plant) {
        if (this.plant == null && !meteoriteAffected) {
            this.plant = plant;
            return true;
        }
        return false;
    }

    public void removePlant() {
        plant = null;
    }

    /**
     * Harvests plant if stage allows it
     */
    public int harvestPlant() {
        if (meteoriteAffected) return 0;

        if (plant != null) {
            int earnings = plant.harvest();

            if (earnings > 0) {
                plant = null;
            }

            return earnings;
        }
        return 0;
    }

    // =====================================================
    // FERTILIZER
    // =====================================================

    public boolean applyFertilizer(Fertilizer fertilizer) {
        if (this.fertilizer == null) {
            this.fertilizer = fertilizer;
            return true;
        }
        return false;
    }

    // =====================================================
    // WATERING
    // =====================================================

    public boolean waterPlant() {
        if (meteoriteAffected) return false;

        if (plant != null && !plant.isWatered()) {
            plant.water();
            return true;
        }
        return false;
    }

    // =====================================================
    // NEXT DAY LOGIC
    // =====================================================

    /**
     * Processes next-day behavior for this tile
     * - Growth depends on plant stage
     * - Fertilizer decreases differently depending on stage
     * - Water resets after processing
     */
    public void nextDay() {

        if (plant != null) {

            // Get current stage BEFORE advancing
            GrowthStage stage = plant.getCurrentStage();

            boolean isEnergizing = stage instanceof EnergizingStage;

            // Advance plant stage
            plant.advanceStage(this);

            // Handle fertilizer reduction
            if (fertilizer != null) {

                if (isEnergizing) {
                    // Energizing stage → -2 days
                    fertilizer.decrementEffect();
                    fertilizer.decrementEffect();
                } else {
                    // Normal → -1 day
                    fertilizer.decrementEffect();
                }

                // Remove fertilizer if expired
                if (fertilizer.isExpired()) {
                    fertilizer = null;
                }
            }

            // Reset watering state
            plant.resetWatered();
        }
    }

    // =====================================================
    // METEORITE
    // =====================================================

    public void applyMeteorite() {
        meteoriteAffected = true;
        plant = null;
    }

    /**
     * Excavates meteorite tile and makes it permanently fertilized
     */
    public void excavate() {
        meteoriteAffected = false;
        fertilized = true;
        fertilizerDays = -1;
    }
}