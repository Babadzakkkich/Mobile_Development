package predators;

import java.util.Map;

import abstract_animals.Predator;
import herbivores.*;
import island.Island;

public class Wolf extends Predator {
    public Wolf(int x, int y, Island island) {
        super(x, y, 50, 30, 3, 8, island, "🐺");
        setEatProbabilities(Map.of(
            Horse.class, 0.1,
            Deer.class, 0.15,
            Rabbit.class, 0.6,
            Mouse.class, 0.8,
            Goat.class, 0.6,
            Sheep.class, 0.7,
            Boar.class, 0.15,
            Buffalo.class, 0.1,
            Duck.class, 0.4
        ));
    }

    public Wolf(int x, int y, Island island, double initialSatiety) {
        super(x, y, 50, 30, 3, 8, island, "🐺", initialSatiety);
        setEatProbabilities(Map.of(
            Horse.class, 0.1,
            Deer.class, 0.15,
            Rabbit.class, 0.6,
            Mouse.class, 0.8,
            Goat.class, 0.6,
            Sheep.class, 0.7,
            Boar.class, 0.15,
            Buffalo.class, 0.1
        ));
    }
}