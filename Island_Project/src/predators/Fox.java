package predators;

import abstract_animals.Predator;
import herbivores.*;
import island.Island;
import java.util.Map;

public class Fox extends Predator {
    public Fox(int x, int y, Island island) {
        super(x, y, 8, 30, 2, 2, island, "F");
        setEatProbabilities(Map.of(
            Duck.class, 0.6,
            Caterpillar.class, 0.4,
            Rabbit.class, 0.7,
            Mouse.class, 0.9
        ));
    }

    public Fox(int x, int y, Island island, double initialSatiety) {
        super(x, y, 8, 30, 2, 2, island, "F", initialSatiety);
        setEatProbabilities(Map.of(
            Duck.class, 0.6,
            Caterpillar.class, 0.4,
            Rabbit.class, 0.7,
            Mouse.class, 0.9
        ));
    }
}