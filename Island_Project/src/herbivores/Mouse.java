package herbivores;

import java.util.List;
import java.util.stream.Collectors;

import abstract_animals.Animal;
import abstract_animals.Herbivore;
import island.Cell;
import island.Island;

public class Mouse extends Herbivore {
    public Mouse(int x, int y, Island island) {
        super(x, y, 0.05, 500, 1, 0.01, island, "M");
    }

    public Mouse(int x, int y, Island island, double initialSatiety) {
        super(x, y, 0.05, 500, 1, 0.01, island, "M", initialSatiety);
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
            return;
        }

        super.eat(cell);
    }
}