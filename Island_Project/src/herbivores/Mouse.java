package herbivores;

import abstract_animals.Herbivore;
import island.Island;

public class Mouse extends Herbivore {
    public Mouse(int x, int y, Island island) {
        super(x, y, 0.05, 500, 1, 0.01, island, "M");
    }
}