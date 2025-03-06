package predators;

import abstract_animals.Predator;
import island.Island;
import herbivores.*;
import java.util.Map;

public class Bear extends Predator {
    public Bear(int x, int y, Island island) {
        super(x, y, 500, 5, 2, 80, island, "B");
        setEatProbabilities(Map.of(
            Wolf.class, 0.8,
            Python.class, 0.8,
            Fox.class, 0.8,
            Rabbit.class, 0.8,
            Mouse.class, 0.9
        ));
    }
}