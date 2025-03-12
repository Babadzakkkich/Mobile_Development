package herbivores;

import abstract_animals.Herbivore;
import island.Island;

public class Buffalo extends Herbivore {
    public Buffalo(int x, int y, Island island) {
        super(x, y, 700, 10, 3, 100, island, "U");
    }

    public Buffalo(int x, int y, Island island, double initialSatiety) {
        super(x, y, 700, 10, 3, 100, island, "U", initialSatiety);
    }
}