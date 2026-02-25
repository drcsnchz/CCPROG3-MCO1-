/**
 * Represents a fertilizer that can be applied to soil
 *
 * Fertilizer increases plant growth by +1 stage while active and it lasts for a limited number of effect days
 */
public class Fertilizer {

    private String name;
    private int price;
    private int effectDays;

    /**
     * Constructs a Fertilizer.
     *
     * @param name the name of the fertilizer
     * @param price the cost to apply the fertilizer
     * @param effectDays the number of days the fertilizer remains effective
     */
    public Fertilizer(String name, int price, int effectDays) {
        this.name = name;
        this.price = price;
        this.effectDays = effectDays;
    }

    /**
     * Copy constructor (used when applying fertilizer to soil).
     *
     * @param other the fertilizer template to copy
     */
    public Fertilizer(Fertilizer other) {
        this.name = other.name;
        this.price = other.price;
        this.effectDays = other.effectDays;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getEffectDays() {
        return effectDays;
    }

    /**
     * Decreases effect days by 1.
     *
     * Pre-condition: effectDays must be greater than 0.
     * Post-condition: effectDays decreases by 1.
     */
    public void decrementEffect() {
        if (effectDays > 0) {
            effectDays--;
        }
    }

    public boolean isExpired() {
        return effectDays <= 0;
    }

}
