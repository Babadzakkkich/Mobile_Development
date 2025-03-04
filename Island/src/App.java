import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

abstract class Animal {
    protected static final double REPRODUCTION_THRESHOLD = 0.5;
    protected static final double NEWBORN_SATIETY = 0.1;
    
    protected int x;
    protected int y;
    protected double weight;
    protected int maxPerCell;
    protected int speed;
    protected double foodRequired;
    protected double satiety;
    protected Island island;
    protected static final Random random = new Random();
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

    public Animal(int x, int y, double weight, int maxPerCell, int speed, double foodRequired, Island island, String symbol, double initialSatiety) {
        this(x, y, weight, maxPerCell, speed, foodRequired, island, symbol);
        this.satiety = initialSatiety;
    }

    public abstract void eat(Cell cell);
    
    public void reproduce(Cell cell) {
        if (satiety >= foodRequired * REPRODUCTION_THRESHOLD && 
            random.nextDouble() < 0.5 &&
            cell.getAnimals().size() < maxPerCell) {
            
            try {
                Animal child = this.getClass()
                    .getDeclaredConstructor(int.class, int.class, Island.class, double.class)
                    .newInstance(x, y, island, NEWBORN_SATIETY * foodRequired);
                cell.addAnimal(child);
            } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
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
        if (satiety <= 0) die();
    }

    public void die() {
        Cell cell = island.getCell(x, y);
        if (cell != null) cell.removeAnimal(this);
    }

    public String getSymbol() {
        return symbol;
    }
}

// Хищники
class Wolf extends Animal {
    private static final Map<Class<?>, Double> EAT_PROBABILITIES = new HashMap<>();
    static { EAT_PROBABILITIES.put(Rabbit.class, 0.6); EAT_PROBABILITIES.put(Mouse.class, 0.8); }

    public Wolf(int x, int y, Island island) { super(x, y, 50, 30, 3, 8, island, "W"); }
    public Wolf(int x, int y, Island island, double initialSatiety) { super(x, y, 50, 30, 3, 8, island, "W", initialSatiety); }

    @Override
    public void eat(Cell cell) {
        for (Class<?> preyClass : EAT_PROBABILITIES.keySet()) {
            Optional<Animal> prey = cell.getAnimals().stream()
                    .filter(preyClass::isInstance)
                    .findFirst();
            if (prey.isPresent() && random.nextDouble() < EAT_PROBABILITIES.get(preyClass)) {
                satiety += prey.get().weight;
                cell.removeAnimal(prey.get());
                break;
            }
        }
    }
}

class Python extends Animal {
    private static final Map<Class<?>, Double> EAT_PROBABILITIES = new HashMap<>();
    static { EAT_PROBABILITIES.put(Rabbit.class, 0.2); EAT_PROBABILITIES.put(Mouse.class, 0.4); }

    public Python(int x, int y, Island island) { super(x, y, 15, 30, 1, 3, island, "P"); }
    public Python(int x, int y, Island island, double initialSatiety) { super(x, y, 15, 30, 1, 3, island, "P", initialSatiety); }

    @Override
    public void eat(Cell cell) {
        for (Class<?> preyClass : EAT_PROBABILITIES.keySet()) {
            Optional<Animal> prey = cell.getAnimals().stream()
                    .filter(preyClass::isInstance)
                    .findFirst();
            if (prey.isPresent() && random.nextDouble() < EAT_PROBABILITIES.get(preyClass)) {
                satiety += prey.get().weight;
                cell.removeAnimal(prey.get());
                break;
            }
        }
    }
}

class Fox extends Animal {
    private static final Map<Class<?>, Double> EAT_PROBABILITIES = new HashMap<>();
    static { EAT_PROBABILITIES.put(Rabbit.class, 0.7); EAT_PROBABILITIES.put(Mouse.class, 0.9); }

    public Fox(int x, int y, Island island) { super(x, y, 8, 30, 2, 2, island, "F"); }
    public Fox(int x, int y, Island island, double initialSatiety) { super(x, y, 8, 30, 2, 2, island, "F", initialSatiety); }

    @Override
    public void eat(Cell cell) {
        for (Class<?> preyClass : EAT_PROBABILITIES.keySet()) {
            Optional<Animal> prey = cell.getAnimals().stream()
                    .filter(preyClass::isInstance)
                    .findFirst();
            if (prey.isPresent() && random.nextDouble() < EAT_PROBABILITIES.get(preyClass)) {
                satiety += prey.get().weight;
                cell.removeAnimal(prey.get());
                break;
            }
        }
    }
}

class Bear extends Animal {
    private static final Map<Class<?>, Double> EAT_PROBABILITIES = new HashMap<>();
    static { 
        EAT_PROBABILITIES.put(Wolf.class, 0.8);
        EAT_PROBABILITIES.put(Python.class, 0.8);
        EAT_PROBABILITIES.put(Fox.class, 0.8);
        EAT_PROBABILITIES.put(Rabbit.class, 0.8);
        EAT_PROBABILITIES.put(Mouse.class, 0.9);
    }

    public Bear(int x, int y, Island island) { super(x, y, 500, 5, 2, 80, island, "B"); }
    public Bear(int x, int y, Island island, double initialSatiety) { super(x, y, 500, 5, 2, 80, island, "B", initialSatiety); }

    @Override
    public void eat(Cell cell) {
        for (Class<?> preyClass : EAT_PROBABILITIES.keySet()) {
            Optional<Animal> prey = cell.getAnimals().stream()
                    .filter(preyClass::isInstance)
                    .findFirst();
            if (prey.isPresent() && random.nextDouble() < EAT_PROBABILITIES.get(preyClass)) {
                satiety += prey.get().weight;
                cell.removeAnimal(prey.get());
                break;
            }
        }
    }
}

class Eagle extends Animal {
    private static final Map<Class<?>, Double> EAT_PROBABILITIES = new HashMap<>();
    static { EAT_PROBABILITIES.put(Rabbit.class, 0.9); EAT_PROBABILITIES.put(Mouse.class, 0.9); }

    public Eagle(int x, int y, Island island) { super(x, y, 6, 20, 3, 1, island, "E"); }
    public Eagle(int x, int y, Island island, double initialSatiety) { super(x, y, 6, 20, 3, 1, island, "E", initialSatiety); }

    @Override
    public void eat(Cell cell) {
        for (Class<?> preyClass : EAT_PROBABILITIES.keySet()) {
            Optional<Animal> prey = cell.getAnimals().stream()
                    .filter(preyClass::isInstance)
                    .findFirst();
            if (prey.isPresent() && random.nextDouble() < EAT_PROBABILITIES.get(preyClass)) {
                satiety += prey.get().weight;
                cell.removeAnimal(prey.get());
                break;
            }
        }
    }
}

// Травоядные
class Horse extends Animal {
    public Horse(int x, int y, Island island) { super(x, y, 400, 20, 4, 60, island, "H"); }
    public Horse(int x, int y, Island island, double initialSatiety) { super(x, y, 400, 20, 4, 60, island, "H", initialSatiety); }

    @Override
    public void eat(Cell cell) {
        if (cell.getPlants() > 0) {
            cell.consumePlant();
            satiety += 60;
        }
    }
}

class Deer extends Animal {
    public Deer(int x, int y, Island island) { super(x, y, 300, 20, 4, 50, island, "D"); }
    public Deer(int x, int y, Island island, double initialSatiety) { super(x, y, 300, 20, 4, 50, island, "D", initialSatiety); }

    @Override
    public void eat(Cell cell) {
        if (cell.getPlants() > 0) {
            cell.consumePlant();
            satiety += 50;
        }
    }
}

class Rabbit extends Animal {
    public Rabbit(int x, int y, Island island) { super(x, y, 2, 150, 2, 0.45, island, "R"); }
    public Rabbit(int x, int y, Island island, double initialSatiety) { super(x, y, 2, 150, 2, 0.45, island, "R", initialSatiety); }

    @Override
    public void eat(Cell cell) {
        if (cell.getPlants() > 0) {
            cell.consumePlant();
            satiety += 0.45;
        }
    }
}

class Mouse extends Animal {
    public Mouse(int x, int y, Island island) { super(x, y, 0.05, 500, 1, 0.01, island, "M"); }
    public Mouse(int x, int y, Island island, double initialSatiety) { super(x, y, 0.05, 500, 1, 0.01, island, "M", initialSatiety); }

    @Override
    public void eat(Cell cell) {
        if (cell.getPlants() > 0) {
            cell.consumePlant();
            satiety += 0.01;
        }
    }
}

class Goat extends Animal {
    public Goat(int x, int y, Island island) { super(x, y, 60, 140, 3, 10, island, "G"); }
    public Goat(int x, int y, Island island, double initialSatiety) { super(x, y, 60, 140, 3, 10, island, "G", initialSatiety); }

    @Override
    public void eat(Cell cell) {
        if (cell.getPlants() > 0) {
            cell.consumePlant();
            satiety += 10;
        }
    }
}

class Sheep extends Animal {
    public Sheep(int x, int y, Island island) { super(x, y, 70, 140, 3, 15, island, "S"); }
    public Sheep(int x, int y, Island island, double initialSatiety) { super(x, y, 70, 140, 3, 15, island, "S", initialSatiety); }

    @Override
    public void eat(Cell cell) {
        if (cell.getPlants() > 0) {
            cell.consumePlant();
            satiety += 15;
        }
    }
}

class Boar extends Animal {
    public Boar(int x, int y, Island island) { super(x, y, 400, 50, 2, 50, island, "O"); }
    public Boar(int x, int y, Island island, double initialSatiety) { super(x, y, 400, 50, 2, 50, island, "O", initialSatiety); }

    @Override
    public void eat(Cell cell) {
        if (cell.getPlants() > 0) {
            cell.consumePlant();
            satiety += 50;
        }
    }
}

class Buffalo extends Animal {
    public Buffalo(int x, int y, Island island) { super(x, y, 700, 10, 3, 100, island, "U"); }
    public Buffalo(int x, int y, Island island, double initialSatiety) { super(x, y, 700, 10, 3, 100, island, "U", initialSatiety); }

    @Override
    public void eat(Cell cell) {
        if (cell.getPlants() > 0) {
            cell.consumePlant();
            satiety += 100;
        }
    }
}

class Duck extends Animal {
    private static final Map<Class<?>, Double> EAT_PROBABILITIES = new HashMap<>();
    static { EAT_PROBABILITIES.put(Caterpillar.class, 0.9); }

    public Duck(int x, int y, Island island) { super(x, y, 1, 200, 4, 0.15, island, "K"); }
    public Duck(int x, int y, Island island, double initialSatiety) { super(x, y, 1, 200, 4, 0.15, island, "K", initialSatiety); }

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

class Caterpillar extends Animal {
    public Caterpillar(int x, int y, Island island) { super(x, y, 0.01, 1000, 0, 0, island, "C"); }
    public Caterpillar(int x, int y, Island island, double initialSatiety) { super(x, y, 0.01, 1000, 0, 0, island, "C", initialSatiety); }

    @Override
    public void eat(Cell cell) {
        if (cell.getPlants() > 0) {
            cell.consumePlant();
            satiety += 0.01;
        }
    }
}

class Cell {
    private List<Animal> animals = new CopyOnWriteArrayList<>();
    private int plants;
    private Random random = new Random();

    public void addAnimal(Animal animal) {
        if (animals.size() < animal.maxPerCell) {
            animals.add(animal);
        }
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public synchronized void consumePlant() {
        if (plants > 0) plants--;
    }

    public synchronized void growPlants() {
        if (random.nextInt(100) < 20) plants += 10;
    }

    public int getPlants() {
        return plants;
    }
}

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
        ExecutorService animalThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        scheduler.scheduleAtFixedRate(() -> {
            // Рост растений
            for (Cell[] row : grid) {
                for (Cell cell : row) {
                    cell.growPlants();
                }
            }

            // Обработка животных
            List<Future<?>> futures = new ArrayList<>();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Cell cell = grid[i][j];
                    for (Animal animal : cell.getAnimals()) {
                        futures.add(animalThreadPool.submit(() -> {
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
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            // Вывод состояния
            drawIsland();
            printStatistics();

        }, 0, 1, TimeUnit.SECONDS);
    }

    private void printStatistics() {
        Map<Class<?>, Integer> counts = new HashMap<>();
        int totalPlants = 0;

        // Собираем статистику
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Cell cell = grid[i][j];
                totalPlants += cell.getPlants();
                for (Animal animal : cell.getAnimals()) {
                    counts.put(animal.getClass(), counts.getOrDefault(animal.getClass(), 0) + 1);
                }
            }
        }

        // Формируем вывод
        System.out.println("\nСтатистика:");
        System.out.println("Растений: " + totalPlants);
        System.out.println("Хищники:");
        printCount(counts, Wolf.class, "Волки");
        printCount(counts, Python.class, "Удавы");
        printCount(counts, Fox.class, "Лисы");
        printCount(counts, Bear.class, "Медведи");
        printCount(counts, Eagle.class, "Орлы");
        
        System.out.println("\nТравоядные:");
        printCount(counts, Horse.class, "Лошади");
        printCount(counts, Deer.class, "Олени");
        printCount(counts, Rabbit.class, "Кролики");
        printCount(counts, Mouse.class, "Мыши");
        printCount(counts, Goat.class, "Козы");
        printCount(counts, Sheep.class, "Овцы");
        printCount(counts, Boar.class, "Кабаны");
        printCount(counts, Buffalo.class, "Буйволы");
        printCount(counts, Duck.class, "Утки");
        printCount(counts, Caterpillar.class, "Гусеницы");
    }

    private void printCount(Map<Class<?>, Integer> counts, Class<?> clazz, String name) {
        System.out.printf("%-10s: %d\n", name, counts.getOrDefault(clazz, 0));
    }

    public void drawIsland() {
        System.out.println("\nОстров (" + width + "x" + height + "):");
        System.out.println("=".repeat(width * 2 + 2));
        
        for (int j = 0; j < height; j++) {
            System.out.print("|");
            for (int i = 0; i < width; i++) {
                Cell cell = grid[i][j];
                List<Animal> animals = cell.getAnimals();
                String cellSymbol = animals.isEmpty() 
                    ? (cell.getPlants() > 0 ? "* " : ". ")
                    : animals.get(0).getSymbol() + " ";
                System.out.print(cellSymbol);
            }
            System.out.println("|");
        }
        System.out.println("=".repeat(width * 2 + 2));
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Cell getCell(int x, int y) { return grid[x][y]; }
}

public class App {
    public static void main(String[] args) {
        Island island = new Island(50, 20); // Уменьшил размер для примера

        // Инициализация популяции
        Random rnd = new Random();
        addAnimals(island, Wolf.class, 20, rnd);
        addAnimals(island, Python.class, 10, rnd);
        addAnimals(island, Fox.class, 10, rnd);
        addAnimals(island, Bear.class, 5, rnd);
        addAnimals(island, Eagle.class, 10, rnd);
        addAnimals(island, Horse.class, 20, rnd);
        addAnimals(island, Deer.class, 20, rnd);
        addAnimals(island, Rabbit.class, 30, rnd);
        addAnimals(island, Mouse.class, 50, rnd);
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