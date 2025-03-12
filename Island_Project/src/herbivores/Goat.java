package herbivores;

import abstract_animals.Herbivore;
import island.Island;

public class Goat extends Herbivore {
    public Goat(int x, int y, Island island) {
        super(x, y, 60, 140, 3, 10, island, "G");
    }

    public Goat(int x, int y, Island island, double initialSatiety) {
        super(x, y, 60, 140, 3, 10, island, "G", initialSatiety);
    }
}