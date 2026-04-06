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

    public boolean applyFertilizer(Fertilizer f) {
        if (fertilizer == null) {
            fertilizer = f;
            return true;
        }
        return false;
    }

    public boolean waterPlant() {
        if (plant != null) {
            plant.water();
            return true;
        }
        return false;
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