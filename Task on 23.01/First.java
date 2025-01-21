import java.util.Scanner;

public class First {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            do { 
                System.out.println("Выберите задачу (1-20) или введите 0 для выхода:");
                choice = scanner.nextInt();
                
                switch (choice) {
                    case 1 -> task1(scanner);
                    case 2 -> task2(scanner);
                    case 3 -> task3();
                    case 4 -> task4(scanner);
                    case 5 -> task5(scanner);
                    case 6 -> task6(scanner);
                    case 7 -> task7(scanner);
                    case 8 -> task8(scanner);
                    case 9 -> task9(scanner);
                    case 10 -> task10(scanner);
                    case 11 -> task11(scanner);
                    case 12 -> task12(scanner);
                    case 13 -> task13(scanner);
                    case 14 -> task14(scanner);
                    case 15 -> task15(scanner);
                    case 16 -> task16(scanner);
                    case 17 -> task17(scanner);
                    case 18 -> task18(scanner);
                    case 19 -> task19(scanner);
                    case 20 -> task20(scanner);
                    case 0 -> System.out.println("Выход из программы.");
                    default -> System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } while (choice != 0);
        }
    }

    public static void task1(Scanner scanner) {
        System.out.println("Введите целое число:");
        int number = scanner.nextInt();
        if (number % 2 == 0) {
            System.out.println("Число четное.");
        } else {
            System.out.println("Число нечетное.");
        }
    }

    public static void task2(Scanner scanner) {
        System.out.println("Введите три целых числа:");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        int min = Math.min(a, Math.min(b, c));
        System.out.println("Минимальное число: " + min);
    }

    public static void task3() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("5 * " + i + " = " + (5 * i));
        }
    }

    public static void task4(Scanner scanner) {
        System.out.println("Введите целое число N:");
        int n = scanner.nextInt();
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        System.out.println("Сумма чисел от 1 до " + n + " равна " + sum);
    }

    public static void task5(Scanner scanner) {
        System.out.println("Введите целое число N:");
        int n = scanner.nextInt();
        int a = 0, b = 1;
        System.out.println("Первые " + n + " чисел Фибоначчи:");
        for (int i = 0; i < n; i++) {
            System.out.print(a + " ");
            int temp = a + b;
            a = b;
            b = temp;
        }
        System.out.println();
    }

    public static void task6(Scanner scanner) {
        System.out.println("Введите целое число:");
        int number = scanner.nextInt();
        boolean isPrime = true;
        if (number <= 1) {
            isPrime = false;
        } else {
            for (int i = 2; i <= Math.sqrt(number); i++) {
                if (number % i == 0) {
                    isPrime = false;
                    break;
                }
            }
        }
        if (isPrime) {
            System.out.println("Число простое.");
        } else {
            System.out.println("Число не простое.");
        }
    }

    public static void task7(Scanner scanner) {
        System.out.println("Введите целое число N:");
        int n = scanner.nextInt();
        System.out.println("Числа от " + n + " до 1 в обратном порядке:");
        for (int i = n; i >= 1; i--) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void task8(Scanner scanner) {
        System.out.println("Введите два целых числа A и B:");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int sum = 0;
        for (int i = a; i <= b; i++) {
            if (i % 2 == 0) {
                sum += i;
            }
        }
        System.out.println("Сумма четных чисел от " + a + " до " + b + " равна " + sum);
    }

    public static void task9(Scanner scanner) {
        System.out.println("Введите строку:");
        scanner.nextLine(); // Очистка буфера
        String input = scanner.nextLine();
        String reversed = new StringBuilder(input).reverse().toString();
        System.out.println("Реверс строки: " + reversed);
    }

    public static void task10(Scanner scanner) {
        System.out.println("Введите целое число:");
        int number = scanner.nextInt();
        int count = 0;
        while (number != 0) {
            number /= 10;
            count++;
        }
        System.out.println("Количество цифр в числе: " + count);
    }

    public static void task11(Scanner scanner) {
        System.out.println("Введите целое число N:");
        int n = scanner.nextInt();
        long factorial = 1;
        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }
        System.out.println("Факториал числа " + n + " равен " + factorial);
    }

    public static void task12(Scanner scanner) {
        System.out.println("Введите целое число:");
        int number = scanner.nextInt();
        int sum = 0;
        while (number != 0) {
            sum += number % 10;
            number /= 10;
        }
        System.out.println("Сумма цифр числа: " + sum);
    }

    public static void task13(Scanner scanner) {
        System.out.println("Введите строку:");
        scanner.nextLine(); // Очистка буфера
        String input = scanner.nextLine();
        String reversed = new StringBuilder(input).reverse().toString();
        if (input.equals(reversed)) {
            System.out.println("Строка является палиндромом.");
        } else {
            System.out.println("Строка не является палиндромом.");
        }
    }

    public static void task14(Scanner scanner) {
        System.out.println("Введите размер массива:");
        int size = scanner.nextInt();
        int[] array = new int[size];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        int max = array[0];
        for (int i = 1; i < size; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        System.out.println("Максимальное число в массиве: " + max);
    }

    public static void task15(Scanner scanner) {
        System.out.println("Введите размер массива:");
        int size = scanner.nextInt();
        int[] array = new int[size];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += array[i];
        }
        System.out.println("Сумма всех элементов массива: " + sum);
    }

    public static void task16(Scanner scanner) {
        System.out.println("Введите размер массива:");
        int size = scanner.nextInt();
        int[] array = new int[size];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        int positiveCount = 0, negativeCount = 0;
        for (int i = 0; i < size; i++) {
            if (array[i] > 0) {
                positiveCount++;
            } else if (array[i] < 0) {
                negativeCount++;
            }
        }
        System.out.println("Количество положительных чисел: " + positiveCount);
        System.out.println("Количество отрицательных чисел: " + negativeCount);
    }

    public static void task17(Scanner scanner) {
        System.out.println("Введите два целых числа A и B:");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        System.out.println("Простые числа в диапазоне от " + a + " до " + b + ":");
        for (int i = a; i <= b; i++) {
            boolean isPrime = true;
            if (i <= 1) {
                isPrime = false;
            } else {
                for (int j = 2; j <= Math.sqrt(i); j++) {
                    if (i % j == 0) {
                        isPrime = false;
                        break;
                    }
                }
            }
            if (isPrime) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
    }

    public static void task18(Scanner scanner) {
        System.out.println("Введите строку:");
        scanner.nextLine(); // Очистка буфера
        String input = scanner.nextLine().toLowerCase();
        int vowels = 0, consonants = 0;
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                    vowels++;
                } else {
                    consonants++;
                }
            }
        }
        System.out.println("Количество гласных: " + vowels);
        System.out.println("Количество согласных: " + consonants);
    }

    public static void task19(Scanner scanner) {
        System.out.println("Введите строку из нескольких слов:");
        scanner.nextLine(); // Очистка буфера
        String input = scanner.nextLine();
        String[] words = input.split(" ");
        System.out.println("Слова в обратном порядке:");
        for (int i = words.length - 1; i >= 0; i--) {
            System.out.print(words[i] + " ");
        }
        System.out.println();
    }

    public static void task20(Scanner scanner) {
        System.out.println("Введите целое число:");
        int number = scanner.nextInt();
        int originalNumber = number;
        int sum = 0;
        int digits = String.valueOf(number).length();
        while (number != 0) {
            int digit = number % 10;
            sum += Math.pow(digit, digits);
            number /= 10;
        }
        if (sum == originalNumber) {
            System.out.println("Число является числом Армстронга.");
        } else {
            System.out.println("Число не является числом Армстронга.");
        }
    }
}