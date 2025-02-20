package Multithreading;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class Multithreading {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            do {
                System.out.println("Выберите задачу (1-12) или введите 0 для выхода:");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> task1();
                    case 2 -> task2();
                    case 3 -> task3();
                    case 4 -> task4();
                    case 5 -> task5();
                    case 6 -> task6();
                    case 7 -> task7();
                    case 8 -> task8();
                    case 9 -> task9();
                    case 10 -> task10();
                    case 11 -> task11();
                    case 12 -> task12();
                    case 0 -> System.out.println("Выход из программы.");
                    default -> System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } while (choice != 0);
        }
    }

    // Задача 1: Общий счётчик (Counter)
    public static void task1() {
        class Counter {
            private int count = 0;
            private final Lock lock = new ReentrantLock();

            public void increment() {
                lock.lock();
                try {
                    count++;
                } finally {
                    lock.unlock();
                }
            }

            public int getCount() {
                return count;
            }
        }

        Counter counter = new Counter();
        Thread[] threads = new Thread[5];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    counter.increment();
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Поток был прерван.");
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Финальное значение счётчика: " + counter.getCount());
    }

    // Задача 2: Генерация последовательности чисел
    public static void task2() {
        List<Integer> numbers = new CopyOnWriteArrayList<>();
        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 1; j <= 100; j++) {
                    numbers.add(j);
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Поток был прерван.");
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("Количество чисел в списке: " + numbers.size());
    }

    // Задача 3: Распределение задач с использованием пула потоков
    public static void task3() {
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (int i = 1; i <= 20; i++) {
            int taskId = i;
            executor.submit(() -> {
                System.out.println("Поток " + Thread.currentThread().getName() + " выполняет задачу " + taskId);
            });
        }

        executor.shutdown();
    }

    // Задача 4: Симуляция работы банка
    public static void task4() {
        class Account {
            private int balance;
            private final Lock lock = new ReentrantLock();

            public Account(int balance) {
                this.balance = balance;
            }

            public void deposit(int amount) {
                lock.lock();
                try {
                    balance += amount;
                } finally {
                    lock.unlock();
                }
            }

            public void withdraw(int amount) {
                lock.lock();
                try {
                    balance -= amount;
                } finally {
                    lock.unlock();
                }
            }

            public int getBalance() {
                return balance;
            }
        }

        Account account1 = new Account(1000);
        Account account2 = new Account(1000);

        Runnable transferTask = () -> {
            for (int i = 0; i < 100; i++) {
                synchronized (account1) {
                    synchronized (account2) {
                        account1.withdraw(10);
                        account2.deposit(10);
                    }
                }
            }
        };

        Thread t1 = new Thread(transferTask);
        Thread t2 = new Thread(transferTask);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.err.println("Поток был прерван.");
            Thread.currentThread().interrupt();
        }

        System.out.println("Баланс аккаунта 1: " + account1.getBalance());
        System.out.println("Баланс аккаунта 2: " + account2.getBalance());
    }

    // Задача 5: Барьер синхронизации
    public static void task5() {
        int threadCount = 5;
        CyclicBarrier barrier = new CyclicBarrier(threadCount, () -> {
            System.out.println("Все потоки завершили первую фазу!");
        });

        for (int i = 1; i <= threadCount; i++) {
            int threadId = i;
            new Thread(() -> {
                try {
                    System.out.println("Поток " + threadId + " выполняет задачу...");
                    Thread.sleep(1000); // Имитация работы
                    barrier.await();
                    System.out.println("Поток " + threadId + " перешёл ко второй фазе.");
                } catch (InterruptedException | BrokenBarrierException e) {
                    System.err.println("Ошибка в потоке " + threadId + ": " + e.getMessage());
                }
            }).start();
        }
    }

    // Задача 6: Ограниченный доступ к ресурсу
    public static void task6() {
        Semaphore semaphore = new Semaphore(2); // Только 2 потока могут получить доступ

        Runnable task = () -> {
            try {
                System.out.println(Thread.currentThread().getName() + " пытается получить доступ...");
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " получил доступ!");
                Thread.sleep(2000); // Имитация работы
            } catch (InterruptedException e) {
                System.err.println("Поток был прерван.");
                Thread.currentThread().interrupt();
            } finally {
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + " освободил доступ.");
            }
        };

        for (int i = 0; i < 5; i++) {
            new Thread(task).start();
        }
    }

    // Задача 7: Обработка результатов задач
    public static void task7() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            int number = i;
            Callable<Integer> callable = () -> {
                int factorial = 1;
                for (int j = 1; j <= number; j++) {
                    factorial *= j;
                }
                return factorial;
            };
            futures.add(executor.submit(callable));
        }

        for (int i = 0; i < futures.size(); i++) {
            try {
                System.out.println("Факториал " + (i + 1) + ": " + futures.get(i).get());
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Ошибка при выполнении задачи: " + e.getMessage());
            }
        }

        executor.shutdown();
    }

    // Задача 8: Симуляция производственной линии
    public static void task8() {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 20; i++) {
                    queue.put(i);
                    System.out.println("Производитель добавил: " + i);
                    Thread.sleep(500); // Имитация работы
                }
            } catch (InterruptedException e) {
                System.err.println("Поток был прерван.");
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    int data = queue.take();
                    System.out.println("Потребитель обработал: " + data);
                    Thread.sleep(1000); // Имитация работы
                }
            } catch (InterruptedException e) {
                System.err.println("Поток был прерван.");
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }

    // Задача 9: Многопоточная сортировка
    public static void task9() {
        int[] array = {5, 3, 8, 6, 2, 7, 4, 1};
        try (ForkJoinPool pool = new ForkJoinPool()) {
            try {
                pool.invoke(new SortTask(array, 0, array.length - 1));
            } finally {
                pool.shutdown();
            }
        }
        System.out.println(Arrays.toString(array));
    }

    private static class SortTask extends RecursiveAction {
        private final int[] array;
        private final int left;
        private final int right;

        public SortTask(int[] array, int left, int right) {
            this.array = array;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if (left < right) {
                int mid = (left + right) / 2;
                invokeAll(new SortTask(array, left, mid), new SortTask(array, mid + 1, right));
                merge(array, left, mid, right);
            }
        }

        private void merge(int[] array, int left, int mid, int right) {
            int[] temp = new int[right - left + 1];
            int i = left, j = mid + 1, k = 0;

            while (i <= mid && j <= right) {
                temp[k++] = (array[i] <= array[j]) ? array[i++] : array[j++];
            }

            while (i <= mid) {
                temp[k++] = array[i++];
            }

            while (j <= right) {
                temp[k++] = array[j++];
            }

            System.arraycopy(temp, 0, array, left, temp.length);
        }
    }

    // Задача 10: Обед философов
    public static void task10() {
        class Philosopher implements Runnable {
            private final int id;
            private final Lock leftFork;
            private final Lock rightFork;

            public Philosopher(int id, Lock leftFork, Lock rightFork) {
                this.id = id;
                this.leftFork = leftFork;
                this.rightFork = rightFork;
            }

            @Override
            public void run() {
                try {
                    while (true) {
                        think();
                        eat();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            private void think() throws InterruptedException {
                System.out.println("Философ " + id + " думает.");
                Thread.sleep(1000); // Имитация работы
            }

            private void eat() throws InterruptedException {
                leftFork.lock();
                try {
                    rightFork.lock();
                    try {
                        System.out.println("Философ " + id + " ест.");
                        Thread.sleep(1000); // Имитация работы
                    } finally {
                        rightFork.unlock();
                    }
                } finally {
                    leftFork.unlock();
                }
            }
        }

        int philosophersCount = 5;
        Lock[] forks = new Lock[philosophersCount];
        for (int i = 0; i < philosophersCount; i++) {
            forks[i] = new ReentrantLock();
        }

        for (int i = 0; i < philosophersCount; i++) {
            new Thread(new Philosopher(i, forks[i], forks[(i + 1) % philosophersCount])).start();
        }
    }

    // Задача 11: Умножение матриц в параллельных потоках
    public static void task11() {
        int[][] matrixA = {{1, 2}, {3, 4}};
        int[][] matrixB = {{5, 6}, {7, 8}};
        int[][] result = new int[2][2];

        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 2; i++) {
            int row = i;
            executor.submit(() -> {
                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < 2; k++) {
                        result[row][j] += matrixA[row][k] * matrixB[k][j];
                    }
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("Поток был прерван.");
            Thread.currentThread().interrupt();
        }

        System.out.println("Результат умножения матриц:");
        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }

    // Задача 12: Таймер с многопоточностью
    public static void task12() {
        Thread timerThread = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    System.out.println("Текущее время: " + i + " сек.");
                    Thread.sleep(1000); // Имитация работы
                }
            } catch (InterruptedException e) {
                System.out.println("Таймер остановлен.");
                Thread.currentThread().interrupt();
            }
        });

        Thread stopperThread = new Thread(() -> {
            try {
                Thread.sleep(10000); // Ожидание 10 секунд
                timerThread.interrupt(); // Прерывание таймера
            } catch (InterruptedException e) {
                System.err.println("Поток был прерван.");
                Thread.currentThread().interrupt();
            }
        });

        timerThread.start();
        stopperThread.start();
    }
}