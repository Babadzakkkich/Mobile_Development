import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

// Абстрактный класс Animal
abstract class Animal {
    protected static final double REPRODUCTION_THRESHOLD = 0.5;
    protected static final double NEWBORN_SATIETY = 0.1;
    protected static final Random random = new Random();

    protected int x;
    protected int y;
    protected double weight;
    protected int maxPerCell;
    protected int speed;
    protected double foodRequired;
    protected double satiety;
    protected Island island;
    protected String symbol;

    public Animal(int x, int y, double weight, int maxPerCell, int speed, double foodRequired, Island island, String symbol) {
        this.x = x;
        this.y = y;
        this.weight = weight;
        this.maxPerCell = maxPerCell;
        this.speed = speed;
        this.foodRequired = foodRequired;
        this.satiety = foodRequired;
        this.island = island;
        this.symbol = symbol;
    }

    public abstract void eat(Cell cell);

    public void reproduce(Cell cell) {
        if (satiety >= foodRequired * REPRODUCTION_THRESHOLD && random.nextDouble() < 0.5 && cell.getAnimals().size() < maxPerCell) {
            try {
                Animal child = this.getClass()
                        .getDeclaredConstructor(int.class, int.class, Island.class)
                        .newInstance(x, y, island);
                cell.addAnimal(child);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void move(Island island) {
        int dx = random.nextInt(2 * speed + 1) - speed;
        int dy = random.nextInt(2 * speed + 1) - speed;
        int newX = Math.max(0, Math.min(island.getWidth() - 1, x + dx));
        int newY = Math.max(0, Math.min(island.getHeight() - 1, y + dy));
        Cell currentCell = island.getCell(x, y);
        Cell newCell = island.getCell(newX, newY);

        if (newCell.getAnimals().size() < maxPerCell) {
            currentCell.removeAnimal(this);
            newCell.addAnimal(this);
            x = newX;
            y = newY;
        }
    }

    public void loseEnergy() {
        satiety -= 0.1 * foodRequired;
        if (satiety <= 0) {
            die();
        }
    }

    public void die() {
        island.getCell(x, y).removeAnimal(this);
    }

    public String getSymbol() {
        return symbol;
    }
}

// Классы хищников
abstract class Predator extends Animal {
    protected Map<Class<?>, Double> eatProbabilities = new HashMap<>();

    public Predator(int x, int y, double weight, int maxPerCell, int speed, double foodRequired, Island island, String symbol) {
        super(x, y, weight, maxPerCell, speed, foodRequired, island, symbol);
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

class Wolf extends Predator {
    public Wolf(int x, int y, Island island) {
        super(x, y, 50, 30, 3, 8, island, "W");
        eatProbabilities.put(Rabbit.class, 0.6);
        eatProbabilities.put(Mouse.class, 0.8);
    }
}

class Python extends Predator {
    public Python(int x, int y, Island island) {
        super(x, y, 15, 30, 1, 3, island, "P");
        eatProbabilities.put(Rabbit.class, 0.2);
        eatProbabilities.put(Mouse.class, 0.4);
    }
}

class Fox extends Predator {
    public Fox(int x, int y, Island island) {
        super(x, y, 8, 30, 2, 2, island, "F");
        eatProbabilities.put(Rabbit.class, 0.7);
        eatProbabilities.put(Mouse.class, 0.9);
    }
}

class Bear extends Predator {
    public Bear(int x, int y, Island island) {
        super(x, y, 500, 5, 2, 80, island, "B");
        eatProbabilities.put(Wolf.class, 0.8);
        eatProbabilities.put(Python.class, 0.8);
        eatProbabilities.put(Fox.class, 0.8);
        eatProbabilities.put(Rabbit.class, 0.8);
        eatProbabilities.put(Mouse.class, 0.9);
    }
}

class Eagle extends Predator {
    public Eagle(int x, int y, Island island) {
        super(x, y, 6, 20, 3, 1, island, "E");
        eatProbabilities.put(Rabbit.class, 0.9);
        eatProbabilities.put(Mouse.class, 0.9);
    }
}

// Классы травоядных
abstract class Herbivore extends Animal {
    public Herbivore(int x, int y, double weight, int maxPerCell, int speed, double foodRequired, Island island, String symbol) {
        super(x, y, weight, maxPerCell, speed, foodRequired, island, symbol);
    }

    @Override
    public void eat(Cell cell) {
        if (cell.getPlants() > 0) {
            cell.consumePlant();
            satiety += 1.0;
        }
    }
}

class Horse extends Herbivore {
    public Horse(int x, int y, Island island) {
        super(x, y, 400, 20, 4, 60, island, "H");
    }
}

class Deer extends Herbivore {
    public Deer(int x, int y, Island island) {
        super(x, y, 300, 20, 4, 50, island, "D");
    }
}

class Rabbit extends Herbivore {
    public Rabbit(int x, int y, Island island) {
        super(x, y, 2, 150, 2, 0.45, island, "R");
    }
}

class Mouse extends Herbivore {
    public Mouse(int x, int y, Island island) {
        super(x, y, 0.05, 500, 1, 0.01, island, "M");
    }
}

class Goat extends Herbivore {
    public Goat(int x, int y, Island island) {
        super(x, y, 60, 140, 3, 10, island, "G");
    }
}

class Sheep extends Herbivore {
    public Sheep(int x, int y, Island island) {
        super(x, y, 70, 140, 3, 15, island, "S");
    }
}

class Boar extends Herbivore {
    public Boar(int x, int y, Island island) {
        super(x, y, 400, 50, 2, 50, island, "O");
    }
}

class Buffalo extends Herbivore {
    public Buffalo(int x, int y, Island island) {
        super(x, y, 700, 10, 3, 100, island, "U");
    }
}

class Duck extends Herbivore {
    public Duck(int x, int y, Island island) {
        super(x, y, 1, 200, 4, 0.15, island, "K");
    }

    @Override
    public void eat(Cell cell) {
        List<Animal> caterpillars = cell.getAnimals().stream()
                .filter(a -> a instanceof Caterpillar)
                .collect(Collectors.toList());
        if (!caterpillars.isEmpty() && random.nextDouble() < 0.9) {
            Animal caterpillar = caterpillars.get(random.nextInt(caterpillars.size()));
            satiety += caterpillar.weight;
            cell.removeAnimal(caterpillar);
        }
    }
}

class Caterpillar extends Herbivore {
    public Caterpillar(int x, int y, Island island) {
        super(x, y, 0.01, 1000, 0, 0, island, "C");
    }
}

// Класс клетки
class Cell {
    private List<Animal> animals = new CopyOnWriteArrayList<>();
    private int plants = 0;
    private Random random = new Random();

    public synchronized void addAnimal(Animal animal) {
        if (animals.size() < animal.maxPerCell) {
            animals.add(animal);
        }
    }

    public synchronized void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public synchronized void consumePlant() {
        if (plants > 0) plants--;
    }

    public synchronized void growPlants() {
        if (random.nextInt(100) < 20) {
            plants += 10;
        }
    }

    public synchronized int getPlants() {
        return plants;
    }
}

// Класс острова
class Island {
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
            // Рост растений
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    grid[i][j].growPlants();
                }
            }

            // Обработка животных
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

            // Ожидание завершения задач
            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Вывод состояния
            drawIsland();
            printStatistics();

        }, 0, 1, TimeUnit.SECONDS);
    }

    // Восстановленный метод отрисовки
    public void drawIsland() {
        System.out.println("\nОстров (" + width + "x" + height + "):");
        System.out.println("=".repeat(width * 2 + 2));
        
        for (int j = 0; j < height; j++) {
            System.out.print("|");
            for (int i = 0; i < width; i++) {
                Cell cell = grid[i][j];
                List<Animal> animals = cell.getAnimals();
                String symbol = animals.isEmpty()
                    ? (cell.getPlants() > 0 ? "* " : ". ")
                    : animals.get(0).getSymbol() + " ";
                System.out.print(symbol);
            }
            System.out.println("|");
        }
        System.out.println("=".repeat(width * 2 + 2));
    }

    // Восстановленный метод статистики
    private void printStatistics() {
        Map<Class<?>, Integer> counts = new HashMap<>();
        int totalPlants = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Cell cell = grid[i][j];
                totalPlants += cell.getPlants();
                for (Animal animal : cell.getAnimals()) {
                    counts.put(animal.getClass(), counts.getOrDefault(animal.getClass(), 0) + 1);
                }
            }
        }

        System.out.println("\nСтатистика:");
        System.out.println("Растений: " + totalPlants);
        printGroup("Хищники", counts, Wolf.class, Python.class, Fox.class, Bear.class, Eagle.class);
        printGroup("Травоядные", counts, Horse.class, Deer.class, Rabbit.class, Mouse.class, 
            Goat.class, Sheep.class, Boar.class, Buffalo.class, Duck.class, Caterpillar.class);
    }

    private void printGroup(String groupName, Map<Class<?>, Integer> counts, Class<?>... classes) {
        System.out.println(groupName + ":");
        for (Class<?> clazz : classes) {
            System.out.printf("%-10s: %d\n", clazz.getSimpleName(), counts.getOrDefault(clazz, 0));
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell getCell(int x, int y) {
        return grid[x][y];
    }
}

// Главный класс
public class App {
    public static void main(String[] args) {
        Island island = new Island(50, 20);
        Random rnd = new Random();

        addAnimals(island, Wolf.class, 20, rnd);
        addAnimals(island, Python.class, 10, rnd);
        addAnimals(island, Fox.class, 10, rnd);
        addAnimals(island, Bear.class, 5, rnd);
        addAnimals(island, Eagle.class, 10, rnd);
        addAnimals(island, Horse.class, 20, rnd);
        addAnimals(island, Deer.class, 20, rnd);
        addAnimals(island, Rabbit.class, 30, rnd);
        //addAnimals(island, Mouse.class, 50, rnd);
        addAnimals(island, Goat.class, 20, rnd);
        addAnimals(island, Sheep.class, 20, rnd);
        addAnimals(island, Boar.class, 10, rnd);
        addAnimals(island, Buffalo.class, 5, rnd);
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