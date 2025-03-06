package herbivores;

import abstract_animals.Animal;
import abstract_animals.Herbivore;
import island.Cell;
import island.Island;
import java.util.List;
import java.util.stream.Collectors;

public class Duck extends Herbivore {
    public Duck(int x, int y, Island island) {
        super(x, y, 1, 200, 4, 0.15, island, "K");
    }

    @Override
    public void eat(Cell cell) {
        List<Animal> caterpillars = cell.getAnimals().stream()
                .filter(a -> a instanceof Caterpillar)
                .collect(Collectors.toList());
        if (!caterpillars.isEmpty() && getRandom().nextDouble() < 0.9) {
            Animal caterpillar = caterpillars.get(getRandom().nextInt(caterpillars.size()));
            satiety += caterpillar.weight;
            cell.removeAnimal(caterpillar);
        } else {
            super.eat(cell);
        }
    }
}