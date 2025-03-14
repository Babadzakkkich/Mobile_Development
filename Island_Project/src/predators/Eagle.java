package predators;

import abstract_animals.Predator;
import herbivores.*;
import island.Island;
import java.util.Map;

public class Eagle extends Predator {
    public Eagle(int x, int y, Island island) {
        super(x, y, 6, 20, 3, 1, island, "E");
        setEatProbabilities(Map.of(
            Fox.class, 0.1,
            Rabbit.class, 0.9,
            Mouse.class, 0.9
        ));
    }

    public Eagle(int x, int y, Island island, double initialSatiety) {
        super(x, y, 6, 20, 3, 1, island, "E", initialSatiety);
        setEatProbabilities(Map.of(
            Fox.class, 0.1,
            Rabbit.class, 0.9,
            Mouse.class, 0.9
        ));
    }
}