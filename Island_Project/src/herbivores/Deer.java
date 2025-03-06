package herbivores;

import abstract_animals.Herbivore;
import island.Island;

public class Deer extends Herbivore {
    public Deer(int x, int y, Island island) {
        super(x, y, 300, 20, 4, 50, island, "D");
    }
}