// Animal.java
package abstract_animals;

import island.Cell;
import island.Island;
import java.util.Random;

public abstract class Animal {
    public static final double REPRODUCTION_THRESHOLD = 0.5;
    public static final double NEWBORN_SATIETY = 0.5;
    public static final Random random = new Random();

    public int x;
    public int y;
    public double weight;
    public int maxPerCell;
    public int speed;
    public double foodRequired;
    public double satiety;
    public Island island;
    public String symbol;

    protected Random getRandom() {
        return random;
    }

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
    
    public Animal(int x, int y, double weight, int maxPerCell, int speed, 
                  double foodRequired, Island island, String symbol, double initialSatiety) {
        this(x, y, weight, maxPerCell, speed, foodRequired, island, symbol);
        this.satiety = initialSatiety;
    }

    public abstract void eat(Cell cell);

    public void reproduce(Cell cell) {
            synchronized (cell) {
                long sameSpeciesCount = cell.getAnimals().stream()
                        .filter(a -> a.getClass() == this.getClass())
                        .count();

                if (satiety >= foodRequired * REPRODUCTION_THRESHOLD &&
                    sameSpeciesCount >= 2 &&
                    random.nextDouble() < 0.5 &&
                    cell.getAnimals().size() < maxPerCell) {

                    try {
                        Animal child = this.getClass()
                                .getDeclaredConstructor(
                                    int.class, 
                                    int.class, 
                                    Island.class,
                                    double.class
                                )
                                .newInstance(
                                    x, 
                                    y, 
                                    island,
                                    NEWBORN_SATIETY * foodRequired
                                );
                        cell.addAnimal(child);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
        satiety -= 0.07 * foodRequired;
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