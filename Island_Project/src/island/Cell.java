package island;

import abstract_animals.Animal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import plants.Plant;

public class Cell {
    private List<Animal> animals = new CopyOnWriteArrayList<>();
    private List<Plant> plants = new CopyOnWriteArrayList<>();
    private static final Random random = new Random();

    public synchronized List<Animal> getAnimals() {
        return animals;
    }

    public synchronized void addAnimal(Animal animal) {
        if (animals.size() < animal.maxPerCell) {
            animals.add(animal);
        }
    }

    public synchronized void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public synchronized List<Plant> getPlants() {
        return new ArrayList<>(plants);
    }

    public synchronized void addPlant(Plant plant) {
        plants.add(plant);
    }

    public synchronized void removePlant(Plant plant) {
        plants.remove(plant);
    }

    public synchronized void consumePlant() {
        if (!plants.isEmpty()) {
            plants.remove(0);
        }
    }

    public synchronized void growPlants() {
        if (plants.size() < Plant.MAX_PER_CELL && random.nextInt(100) < 30) {
            plants.add(new Plant());
        }
    }
}
