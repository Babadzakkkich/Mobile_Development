// Predator.java
package abstract_animals;

import island.Cell;
import island.Island;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Predator extends Animal {
    protected Map<Class<?>, Double> eatProbabilities;

    public Predator(int x, int y, double weight, int maxPerCell, int speed, double foodRequired, Island island, String symbol) {
        super(x, y, weight, maxPerCell, speed, foodRequired, island, symbol);
        this.eatProbabilities = new HashMap<>();
    }

    public Predator(int x, int y, double weight, int maxPerCell, int speed, double foodRequired, Island island, String symbol, double initialSatiety) {
        super(x, y, weight, maxPerCell, speed, foodRequired, island, symbol, initialSatiety);
        this.eatProbabilities = new HashMap<>();
    }

    public void setEatProbabilities(Map<Class<?>, Double> probabilities) {
        this.eatProbabilities = probabilities;
    }

    @Override
    public void eat(Cell cell) {
        for (Class<?> preyClass : eatProbabilities.keySet()) {
            Optional<Animal> prey = cell.getAnimals().stream()
                    .filter(preyClass::isInstance)
                    .findFirst();
            if (prey.isPresent() && random.nextDouble() < eatProbabilities.get(preyClass)) {
                satiety += prey.get().weight;
                cell.removeAnimal(prey.get());
                break;
            }
        }
    }
}