package herbivores;

import abstract_animals.Herbivore;
import island.Island;

public class Boar extends Herbivore {
    public Boar(int x, int y, Island island) {
        super(x, y, 400, 50, 2, 50, island, "O");
    }
}
