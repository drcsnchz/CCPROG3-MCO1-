/**
 * Represents a single soil tile in the field
 *
 * A soil tile:
 * - May contain a plant
 * - May contain fertilizer
 * - May be affected by a meteorite
 */
public class Soil {

    private String soilType;
    private Plant plant;
    private Fertilizer fertilizer;
    private boolean isMeteorite;

    /**
     * Constructs a Soil tile
     *
     * @param soilType the type of soil
     */
    public Soil(String soilType) {
        this.soilType = soilType;
        this.plant = null;
        this.fertilizer = null;
        this.isMeteorite = false;
    }

    /**
     * Gets the soil type
     *
     * @return soil type
     */
    public String getSoilType() {
        return soilType;
    }

    /**
     * Checks if the soil has a plant
     *
     * @return true if a plant exists, false otherwise
     */
    public boolean hasPlant() {
        return plant != null;
    }

    /**
     * Gets the plant in the soil
     *
     * @return Plant object
     */
    public Plant getPlant() {
        return plant;
    }

    /**
     * Sets a plant in the soil
     *
     * @param plant the plant to place
     */
    public void setPlant(Plant plant) {
        if (!isMeteorite) {
            this.plant = plant;
        }
    }

    /**
     * Removes the plant from the soil
     */
    public void removePlant() {
        plant = null;
    }

    /**
     * Checks if fertilizer is present
     *
     * @return true if fertilized, false otherwise
     */
    public boolean hasFertilizer() {
        return fertilizer != null || isMeteorite;
    }

    /**
     * Applies fertilizer to the soil
     *
     * @param f fertilizer to apply
     * @return true if applied successfully
     */
    public boolean applyFertilizer(Fertilizer f) {
        if (fertilizer == null) {
            fertilizer = f;
            return true;
        }
        return false;
    }

    /**
     * Waters the plant in the soil
     *
     * @return true if watering succeeded
     */
    public boolean waterPlant() {
        if (plant != null) {
            plant.water();
            return true;
        }
        return false;
    }

    /**
     * Checks if the soil is affected by a meteorite
     *
     * @return true if affected
     */
    public boolean isMeteoriteAffected() {
        return isMeteorite;
    }

    /**
     * Applies meteorite effect to the soil
     */
    public void applyMeteorite() {
        isMeteorite = true;
        plant = null;
        fertilizer = null;
    }

    /**
     * Excavates the soil, removing meteorite effect
     */
    public void excavate() {
        isMeteorite = false;
    }

    /**
     * Processes next day updates
     */
    public void nextDay() {

        if (plant != null) {

            plant.advanceStage(this);

            if (fertilizer != null && !isMeteorite) {
                fertilizer.decrementEffect();

                if (fertilizer.isExpired()) {
                    fertilizer = null;
                }
            }

            plant.resetWatered();
        }
    }
}