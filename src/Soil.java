/**
 * Represents a soil tile in the field
 *
 * A soil tile contains:
 * - A type (loam, sand, gravel)
 * - A plant (optional)
 * - A fertilizer (optional)
 * - Meteorite state
 */

public class Soil {

    private String soilType;
    private Plant plant;
    private Fertilizer fertilizer;
    private boolean isMeteorite;

    public Soil(String soilType) {
        this.soilType = soilType;
        this.plant = null;
        this.fertilizer = null;
        this.isMeteorite = false;
    }

    public String getSoilType() {
        return soilType;
    }

    public boolean hasPlant() {
        return plant != null;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        if (!isMeteorite) {
            this.plant = plant;
        }
    }

    public void removePlant() {
        plant = null;
    }

    public boolean hasFertilizer() {
        return fertilizer != null || isMeteorite;
    }

    public void applyFertilizer(Fertilizer f) {
        if (fertilizer == null && !isMeteorite) {
            fertilizer = new Fertilizer(f);
        }
    }

    public boolean isMeteoriteAffected() {
        return isMeteorite;
    }

    public void applyMeteorite() {
        isMeteorite = true;
        plant = null;
        fertilizer = null;
    }

    public void excavate() {
        isMeteorite = false;
    }

    public void nextDay() {

        if (plant != null) {

            int before = plant.getStageIndex();

            plant.advanceStage(this);

            int after = plant.getStageIndex();

            boolean grew = after > before;

            if (grew && fertilizer != null && !isMeteorite) {

                fertilizer.decrementEffect();

                if (plant.getCurrentStage() instanceof EnergizingStage) {
                    fertilizer.decrementEffect();
                }

                if (fertilizer.isExpired()) {
                    fertilizer = null;
                }
            }

            plant.resetWatered();
        }
    }
}