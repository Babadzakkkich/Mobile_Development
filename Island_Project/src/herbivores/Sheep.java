package herbivores;

import abstract_animals.Herbivore;
import island.Island;

public class Sheep extends Herbivore {
    public Sheep(int x, int y, Island island) {
        super(x, y, 70, 140, 3, 15, island, "S");
    }
}
