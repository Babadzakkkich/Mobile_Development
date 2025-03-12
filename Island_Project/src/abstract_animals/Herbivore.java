// Herbivore.java
package abstract_animals;

import island.Cell;
import island.Island;
import java.util.List;
import plants.Plant;

public abstract class Herbivore extends Animal {
    public Herbivore(int x, int y, double weight, int maxPerCell, int speed, double foodRequired, Island island, String symbol) {
        super(x, y, weight, maxPerCell, speed, foodRequired, island, symbol);
    }

    public Herbivore(int x, int y, double weight, int maxPerCell, int speed, double foodRequired, Island island, String symbol, double initialSatiety) {
        super(x, y, weight, maxPerCell, speed, foodRequired, island, symbol, initialSatiety);
    }

    @Override
    public void eat(Cell cell) {
        List<Plant> plants = cell.getPlants();
        if (!plants.isEmpty()) {
            Plant plant = plants.get(0);
            satiety += plant.getWeight();
            cell.removePlant(plant);
        }
    }
}