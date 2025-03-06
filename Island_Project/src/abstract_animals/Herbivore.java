// Herbivore.java
package abstract_animals;

import island.Cell;
import island.Island;
import java.util.List;
import plants.Plant;

public abstract class Herbivore extends Animal {
    public Herbivore(int x, int y, double weight, int maxPerCell, int speed, 
                    double foodRequired, Island island, String symbol) {
        super(x, y, weight, maxPerCell, speed, foodRequired, island, symbol);
    }

    @Override
    public void eat(Cell cell) {
        List<Plant> plants = cell.getPlants(); // Получаем список растений
        if (!plants.isEmpty()) { // Проверяем, что список НЕ пуст
            Plant plant = plants.get(0); // Берем первое растение
            satiety += plant.getWeight();
            cell.removePlant(plant); // Удаляем конкретное растение
        }
    }
}