/**
 * Represents a single tile of soil in the field.
 *
 * A soil tile may contain:
 * - A plant
 * - A fertilizer
 * - Meteorite damage
 */
public class Soil {

    private String soilType;
    private Plant plant;
    private Fertilizer fertilizer;
    private boolean meteoriteAffected;
    private String originalSoilType;

    /**
     * Constructs a Soil tile with a given soil type.
     *
     * @param soilType the type of soil (loam, sand, gravel)
     */
    public Soil(String soilType) {
        this.soilType = soilType;
        this.originalSoilType = soilType;
        this.plant = null;
        this.fertilizer = null;
        this.meteoriteAffected = false;
    }


    public String getSoilType() {
        return soilType;
    }


    public boolean hasPlant() {
        return plant != null;
    }


    public boolean hasFertilizer() {
        return fertilizer != null;
    }


    public boolean isMeteoriteAffected() {
        return meteoriteAffected;
    }

    /**
     * Plants a seed on this tile.
     *
     * Pre-condition:
     * - No existing plant
     * - Not meteorite affected
     *
     * @param plant the plant to place
     * @return true if planting successful, false otherwise
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
     * Harvests the plant if mature.
     *
     * @return money earned from harvest (0 if not mature or no plant)
     */
    public int harvestPlant() {
        if (plant != null && plant.isMature()) {
            int earnings = plant.harvest();
            plant = null;
            return earnings;
        }
        return 0;
    }

    /**
     * Applies fertilizer to this tile.
     *
     * Pre-condition: No fertilizer currently applied.
     *
     * @param fertilizer fertilizer to apply
     * @return true if applied successfully, false otherwise
     */
    public boolean applyFertilizer(Fertilizer fertilizer) {
        if (this.fertilizer == null) {
            this.fertilizer = fertilizer;
            return true;
        }
        return false;
    }

    /**
     * Waters the plant on this tile.
     *
     * @return true if watering successful, false otherwise
     */
    public boolean waterPlant() {
        if (plant != null && !plant.isWatered()) {
            plant.water();
            return true;
        }
        return false;
    }

    /**
     * Processes next-day growth logic for this tile.
     *
     * - Plant grows only if watered
     * - Fertilizer decreases only if plant grows
     * - Watered state resets
     */
    public void nextDay() {

        if (plant != null) {

            boolean grew = plant.grow(soilType, fertilizer != null);

            if (grew && fertilizer != null) {
                fertilizer.decrementEffect();

                if (fertilizer.isExpired()) {
                    fertilizer = null;
                }
            }

            plant.resetWatered();
        }
    }


    public void applyMeteorite() {
        meteoriteAffected = true;
        plant = null;
    }

    /**
     * Excavates the meteorite damage.
     * Restores soil and permanently fertilizes the tile.
     */
    public void excavate() {
        if (meteoriteAffected) {
            meteoriteAffected = false;
            soilType = originalSoilType;

            // Permanent fertilizer
            fertilizer = new Fertilizer("Permanent Fertilizer", 0, Integer.MAX_VALUE);
        }
    }
}