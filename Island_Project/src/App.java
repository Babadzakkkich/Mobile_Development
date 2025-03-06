import abstract_animals.Animal;
import herbivores.*;
import island.Island;
import java.util.Random;
import predators.*;

// Главный класс
public class App {
    public static void main(String[] args) {
        Island island = new Island(50, 20);
        Random rnd = new Random();

        addAnimals(island, Wolf.class, 50, rnd);
        addAnimals(island, Python.class, 30, rnd);
        addAnimals(island, Fox.class, 25, rnd);
        addAnimals(island, Bear.class, 15, rnd);
        addAnimals(island, Eagle.class, 20, rnd);
        addAnimals(island, Horse.class, 40, rnd);
        addAnimals(island, Deer.class, 30, rnd);
        addAnimals(island, Rabbit.class, 30, rnd);
        addAnimals(island, Mouse.class, 20, rnd);
        addAnimals(island, Goat.class, 30, rnd);
        addAnimals(island, Sheep.class, 30, rnd);
        addAnimals(island, Boar.class, 30, rnd);
        addAnimals(island, Buffalo.class, 20, rnd);
        addAnimals(island, Duck.class, 20, rnd);
        addAnimals(island, Caterpillar.class, 50, rnd);

        island.simulate();
    }

    private static void addAnimals(Island island, Class<? extends Animal> clazz, int count, Random rnd) {
        for (int i = 0; i < count; i++) {
            int x = rnd.nextInt(island.getWidth());
            int y = rnd.nextInt(island.getHeight());
            try {
                Animal animal = clazz.getDeclaredConstructor(int.class, int.class, Island.class)
                        .newInstance(x, y, island);
                island.getCell(x, y).addAnimal(animal);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}