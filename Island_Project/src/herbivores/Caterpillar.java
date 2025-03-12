package herbivores;

import abstract_animals.Herbivore;
import island.Island;

public class Caterpillar extends Herbivore {
    public Caterpillar(int x, int y, Island island) {
        super(x, y, 0.01, 1000, 0, 0, island, "C");
    }

    public Caterpillar(int x, int y, Island island, double initialSatiety) {
        super(x, y, 0.01, 1000, 0, 0, island, "C", initialSatiety);
    }
}