
 //Represents a plant in the Verdant Sun Farming Simulator.
 // A plant grows over time when watered and may grow faster depending on soil type and fertilizer
 // Can generate money for the player
public class Plant {

    private String name;
    private int seedPrice;
    private int cropPrice;
    private int yield;
    private int maxGrowth;
    private int currentGrowth;
    private String preferredSoil;
    private boolean watered;


     // Constructs a Plant template loaded from external data
     // Growth starts at 0 and the plant is initially not watered

     /*
     * @param name the name of the plant
     * @param seedPrice the cost to plant the seed
     * @param cropPrice the selling price per crop unit
     * @param yield the number of crops produced when harvested
     * @param maxGrowth the growth stage required for maturity
     * @param preferredSoil the soil type where the plant grows faster
     */
    public Plant(String name, int seedPrice, int cropPrice,
                 int yield, int maxGrowth, String preferredSoil) {

        this.name = name;
        this.seedPrice = seedPrice;
        this.cropPrice = cropPrice;
        this.yield = yield;
        this.maxGrowth = maxGrowth;
        this.currentGrowth = 0;
        this.preferredSoil = preferredSoil;
        this.watered = false;
    }


     // Copy constructor used when planting from a template and insures each planted instance has independent growth state

     /*
     * @param other the template plant to copy
     */
    public Plant(Plant other) {
        this.name = other.name;
        this.seedPrice = other.seedPrice;
        this.cropPrice = other.cropPrice;
        this.yield = other.yield;
        this.maxGrowth = other.maxGrowth;
        this.currentGrowth = 0;
        this.preferredSoil = other.preferredSoil;
        this.watered = false;
    }


    public String getName() {
        return name;
    }

    public int getSeedPrice() {
        return seedPrice;
    }


    public int getCropPrice() {
        return cropPrice;
    }


    public int getYield() {
        return yield;
    }


    public int getMaxGrowth() {
        return maxGrowth;
    }


    public int getCurrentGrowth() {
        return currentGrowth;
    }

    public String getPreferredSoil() {
        return preferredSoil;
    }

    public boolean isWatered() {
        return watered;
    }


    public boolean isMature() {
        return currentGrowth >= maxGrowth;
    }


    public void water() {
        if (!watered) {
            watered = true;
        }
    }

     // Processes plant growth for the next day
     // Growth occurs only if the plant was watered and is not already mature

     /*
     * Growth increases by:
     * 1 normally
     * +1 if planted on preferred soil
     * +1 if fertilizer is present
     /

     /*
     * @param soilType the soil type of the tile
     * @param hasFertilizer true if fertilizer is applied
     * @return true if the plant grew, false otherwise
     */
    public boolean grow(String soilType, boolean hasFertilizer) {

        if (!watered || isMature()) {
            return false;
        }

        int growthIncrease = 1;

        if (soilType.equalsIgnoreCase(preferredSoil)) {
            growthIncrease++;
        }

        if (hasFertilizer) {
            growthIncrease++;
        }

        currentGrowth += growthIncrease;

        if (currentGrowth > maxGrowth) {
            currentGrowth = maxGrowth;
        }

        return true;
    }


    public void resetWatered() {
        watered = false;
    }


    public int harvest() {
        if (isMature()) {
            return yield * cropPrice;
        }
        return 0;
    }
}