package predators;

import java.util.Map;

import abstract_animals.Predator;
import herbivores.*;
import island.Island;

public class Wolf extends Predator {
    public Wolf(int x, int y, Island island) {
        super(x, y, 50, 30, 3, 8, island, "W");
        setEatProbabilities(Map.of(
            Rabbit.class, 0.6,
            Mouse.class, 0.8
        ));
    }
}