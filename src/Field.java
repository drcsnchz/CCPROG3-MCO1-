/**
 * Represents the 10x10 field of soil tiles
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
     * Constructs a Field using a predefined soil layout
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
     * Gets a Soil tile at given coordinates
     *
     * @param row row index (0–9)
     * @param col column index (0–9)
     * @return Soil object
     */
    public Soil getSoil(int row, int col) {
        return grid[row][col];
    }

    /**
     * Processes next-day logic for all tiles
     * Resets daily excavation counter
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
     * Applies meteorite event to predefined tiles
     */
    public void applyMeteoriteEvent() {

        int centerRow = 3;
        int centerCol = 3;

        int[][] pattern = {

                {-2, -2}, {-2, 1}, {-2, 2}, {-2, 5},

                {-1, -1}, {-1, 0}, {-1, 1}, {-1, 2}, {-1, 3}, {-1, 4},

                {0, 0}, {0, 1}, {0, 2}, {0, 3},
                {1, 0}, {1, 1}, {1, 2}, {1, 3},
                {2, 0}, {2, 1}, {2, 2}, {2, 3},
                {3, 0}, {3, 1}, {3, 2}, {3, 3},

                {4, -1}, {4, 0}, {4, 1}, {4, 2}, {4, 3}, {4, 4},

                {5, -2}, {5, 1}, {5, 2}, {5, 5}
        };

        for (int[] offset : pattern) {
            int r = centerRow + offset[0];
            int c = centerCol + offset[1];

            if (isValid(r, c)) {
                grid[r][c].applyMeteorite();
            }
        }
    }

    /**
     * Attempts to excavate a tile
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
     * Displays a simple text-based view of the field
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

    private boolean isValid(int r, int c) {
        return r >= 0 && r < SIZE && c >= 0 && c < SIZE;
    }
}