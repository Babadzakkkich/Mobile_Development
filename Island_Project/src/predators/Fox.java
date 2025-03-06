package predators;

import abstract_animals.Predator;
import herbivores.*;
import island.Island;
import java.util.Map;

public class Fox extends Predator {
    public Fox(int x, int y, Island island) {
        super(x, y, 8, 30, 2, 2, island, "F");
        setEatProbabilities(Map.of(
            Rabbit.class, 0.7,
            Mouse.class, 0.9
        ));
    }
}