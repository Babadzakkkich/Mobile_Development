package herbivores;

import java.util.List;
import java.util.stream.Collectors;

import abstract_animals.Animal;
import abstract_animals.Herbivore;
import island.Cell;
import island.Island;

public class Boar extends Herbivore {
    public Boar(int x, int y, Island island) {
        super(x, y, 400, 50, 2, 50, island, "O");
    }

    public Boar(int x, int y, Island island, double initialSatiety) {
        super(x, y, 400, 50, 2, 50, island, "O", initialSatiety);
    }

    @Override
    public void eat(Cell cell) {
        List<Animal> mice = cell.getAnimals().stream()
                .filter(a -> a instanceof Mouse)
                .collect(Collectors.toList());
        if (!mice.isEmpty() && getRandom().nextDouble() < 0.5) {
            Animal mouse = mice.get(getRandom().nextInt(mice.size()));
            satiety += mouse.weight;
            cell.removeAnimal(mouse);
            return;
        }

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
