package herbivores;

import abstract_animals.Herbivore;
import island.Island;

public class Rabbit extends Herbivore {
    public Rabbit(int x, int y, Island island) {
        super(x, y, 2, 150, 2, 0.45, island, "R");
    }
}