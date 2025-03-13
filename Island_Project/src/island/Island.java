package island;

import abstract_animals.Animal;
import herbivores.*;
import java.util.*;
import java.util.concurrent.*;
import predators.*;

public class Island {
    private Cell[][] grid;
    private int width;
    private int height;

    public Island(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Cell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public void simulate() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        scheduler.scheduleAtFixedRate(() -> {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    grid[i][j].growPlants();
                }
            }

            List<Future<?>> futures = new ArrayList<>();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Cell cell = grid[i][j];
                    for (Animal animal : cell.getAnimals()) {
                        futures.add(pool.submit(() -> {
                            animal.eat(cell);
                            animal.reproduce(cell);
                            animal.move(this);
                            animal.loseEnergy();
                        }));
                    }
                }
            }

            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            drawIsland();
            printStatistics();

        }, 0, 1, TimeUnit.SECONDS);
    }

    private void printStatistics() {
        Map<Class<?>, Integer> counts = new HashMap<>();
        int totalPlants = 0;
    
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Cell cell = grid[i][j];
                totalPlants += cell.getPlants().size();
                for (Animal animal : cell.getAnimals()) {
                    counts.put(animal.getClass(), counts.getOrDefault(animal.getClass(), 0) + 1);
                }
            }
        }
    
        System.out.println("\nСтатистика:");
        printGroup("Хищники", counts, Wolf.class, Python.class, Fox.class, Bear.class, Eagle.class);
        printGroup("Травоядные", counts, Horse.class, Deer.class, Rabbit.class, Mouse.class, 
            Goat.class, Sheep.class, Boar.class, Buffalo.class, Duck.class, Caterpillar.class);
        System.out.println("Растений: " + totalPlants);
    }
    
    private void printGroup(String groupName, Map<Class<?>, Integer> counts, Class<?>... classes) {
        System.out.println(groupName + ":");
        for (Class<?> clazz : classes) {
            try {
                Animal instance = (Animal) clazz.getDeclaredConstructor(int.class, int.class, Island.class).newInstance(0, 0, null);
                String symbol = instance.getSymbol();
                System.out.printf("%-10s(%s): %d\n", clazz.getSimpleName(), symbol, counts.getOrDefault(clazz, 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void drawIsland() {
        System.out.println("\nОстров (" + width + "x" + height + "):");
        System.out.println("=".repeat(width * 2 + 2));
        
        for (int j = 0; j < height; j++) {
            System.out.print("|");
            for (int i = 0; i < width; i++) {
                Cell cell = grid[i][j];
                List<Animal> animals = cell.getAnimals();
                String symbol = animals.isEmpty()
                    ? (cell.getPlants().isEmpty() ? ". " : "* ")
                    : animals.get(0).getSymbol() + " ";
                System.out.print(symbol);
            }
            System.out.println("|");
        }
        System.out.println("=".repeat(width * 2 + 2));
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Cell getCell(int x, int y) { return grid[x][y]; }
}