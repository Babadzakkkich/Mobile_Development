package predators;

import abstract_animals.Predator;
import island.Island;
import herbivores.*;

public class Python extends Predator {
    public Python(int x, int y, Island island) {
        super(x, y, 15, 30, 1, 3, island, "P");
        eatProbabilities.put(Rabbit.class, 0.2);
        eatProbabilities.put(Mouse.class, 0.4);
    }
}