package predators;

import java.util.Map;

import abstract_animals.Predator;
import island.Island;
import herbivores.*;

public class Python extends Predator {
    public Python(int x, int y, Island island) {
        super(x, y, 15, 30, 1, 3, island, "P");
        setEatProbabilities(Map.of(
            Fox.class, 0.15,
            Rabbit.class, 0.2,
            Mouse.class, 0.4
        ));
    }

    public Python(int x, int y, Island island, double initialSatiety) {
    super(x, y, 15, 30, 1, 3, island, "P", initialSatiety);
        setEatProbabilities(Map.of(
            Fox.class, 0.15,
            Rabbit.class, 0.2,
            Mouse.class, 0.4
        ));
    }
}