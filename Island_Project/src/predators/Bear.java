package predators;

import abstract_animals.Predator;
import island.Island;
import herbivores.*;
import java.util.Map;

public class Bear extends Predator {
    public Bear(int x, int y, Island island) {
        super(x, y, 500, 5, 2, 80, island, "B");
        setEatProbabilities(Map.of(
            Python.class, 0.8,
            Horse.class, 0.4,
            Deer.class, 0.8,
            Rabbit.class, 0.8,
            Mouse.class, 0.9,
            Goat.class, 0.7,
            Sheep.class, 0.7,
            Boar.class, 0.5,
            Buffalo.class, 0.2,
            Duck.class, 0.1
        ));
    }

    public Bear(int x, int y, Island island, double initialSatiety) {
        super(x, y, 500, 5, 2, 80, island, "B", initialSatiety);
        setEatProbabilities(Map.of(
            Python.class, 0.8,
            Horse.class, 0.4,
            Deer.class, 0.8,
            Rabbit.class, 0.8,
            Mouse.class, 0.9,
            Goat.class, 0.7,
            Sheep.class, 0.7,
            Boar.class, 0.5,
            Buffalo.class, 0.2,
            Duck.class, 0.1
        ));
    }
}