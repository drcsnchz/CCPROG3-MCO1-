/**
 * Represents the 10x10 field of soil tiles.
 *
 * The Field manages:
 * - The grid of Soil tiles
 * - Daily updates
 * - Meteorite event
 * - Excavation limits
 */
public class Field {

    private static final int SIZE = 10;

    private Soil[][] grid;
    private int excavationsToday;

    /**
     * Constructs a Field using a predefined soil layout.
     *
     * @param soilLayout a 10x10 array of soil type strings
     */
    public Field(String[][] soilLayout) {
        grid = new Soil[SIZE][SIZE];

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                grid[row][col] = new Soil(soilLayout[row][col]);
            }
        }

        excavationsToday = 0;
    }

    /**
     * Gets a Soil tile at given coordinates.
     *
     * @param row row index (0–9)
     * @param col column index (0–9)
     * @return Soil object
     */
    public Soil getSoil(int row, int col) {
        return grid[row][col];
    }

    /**
     * Processes next-day logic for all tiles.
     * Resets daily excavation counter.
     */
    public void nextDay() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                grid[row][col].nextDay();
            }
        }

        excavationsToday = 0;
    }

    /**
     * Applies meteorite event to predefined tiles.
     */
    public void applyMeteoriteEvent() {


        for (int row = 3; row <= 6; row++) {
            for (int col = 3; col <= 6; col++) {
                grid[row][col].applyMeteorite();
            }
        }
    }

    /**
     * Attempts to excavate a tile.
     *
     * Pre-condition:
     * - Tile must be meteorite affected
     * - Max 5 excavations per day
     *
     * @param row row index
     * @param col column index
     * @return true if excavation successful
     */
    public boolean excavateTile(int row, int col) {

        if (excavationsToday >= 5) {
            return false;
        }

        Soil soil = grid[row][col];

        if (soil.isMeteoriteAffected()) {
            soil.excavate();
            excavationsToday++;
            return true;
        }

        return false;
    }

    /**
     * Displays a simple text-based view of the field.
     *
     * P = plant
     * M = meteorite
     * . = empty
     */
    public void displayField() {

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {

                Soil soil = grid[row][col];

                if (soil.isMeteoriteAffected()) {
                    System.out.print(" M ");
                } else if (soil.hasPlant()) {
                    System.out.print(" P ");
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
    }
}