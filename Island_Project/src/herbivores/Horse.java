package herbivores;

import abstract_animals.Herbivore;
import island.Island;

public class Horse extends Herbivore {
    public Horse(int x, int y, Island island) {
        super(x, y, 400, 20, 4, 60, island, "H");
    }
}