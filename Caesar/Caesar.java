import java.io.*;
import java.util.Scanner;

public class Caesar {
    // Константы для русского и английского алфавитов
    private static final String RUS_ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String ENG_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Меню выбора режима работы
            System.out.println("Режимы работы: 1. Шифровать, 2. Расшифровать с ключом, 3. Brute Force");
            System.out.print("Выберите режим: ");
            if (!scanner.hasNextLine()) {
                System.out.println("Некорректный выбор.");
                return;
            }
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> encryptMode(scanner);
                case "2" -> decryptWithKey(scanner);
                case "3" -> bruteForceMode(scanner);
                default -> System.out.println("Некорректный выбор.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // Определение алфавита на основе текста
    private static String detectAlphabet(String text) {
        for (char c : text.toCharArray()) {
            if (RUS_ALPHABET.indexOf(c) != -1) return RUS_ALPHABET;
            if (ENG_ALPHABET.indexOf(c) != -1) return ENG_ALPHABET;
        }
        return RUS_ALPHABET; // По умолчанию русский алфавит
    }

    // Основной метод шифрования/расшифровки текста
    private static String processText(String text, int shift, String mode) {
        String alphabet = detectAlphabet(text);
        int len = alphabet.length();
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (alphabet.indexOf(c) == -1) {
                result.append(c);
                continue;
            }
            int index = alphabet.indexOf(c);
            int newIndex = switch (mode) {
                case "encrypt" -> (index + shift) % len;
                case "decrypt" -> (index - shift + len) % len;
                default -> index;
            };
            result.append(alphabet.charAt(newIndex));
        }
        return result.toString();
    }

    // Обработка файла: чтение, шифрование/расшифровка, запись
    private static void processFile(String inputPath, String outputPath, int shift, String mode) {
        try {
            if (!new File(inputPath).exists()) throw new FileNotFoundException("Файл не найден.");
            String text = readFile(inputPath);
            if (text.trim().isEmpty()) throw new IOException("Файл пустой.");
            String processedText = processText(text, shift, mode);
            writeFile(outputPath, processedText);
            System.out.println("Операция выполнена успешно!");
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // Режим шифрования текста
    private static void encryptMode(Scanner scanner) {
        System.out.print("Путь к исходному файлу: ");
        String inputPath = scanner.nextLine();
        System.out.print("Путь для зашифрованного файла: ");
        String outputPath = scanner.nextLine();
        System.out.print("Ключ (число): ");
        int shift = readValidShift(scanner);
        processFile(inputPath, outputPath, shift, "encrypt");
    }

    // Режим расшифровки с ключом
    private static void decryptWithKey(Scanner scanner) {
        System.out.print("Путь к зашифрованному файлу: ");
        String inputPath = scanner.nextLine();
        System.out.print("Путь для расшифрованного файла: ");
        String outputPath = scanner.nextLine();
        System.out.print("Ключ: ");
        int shift = readValidShift(scanner);
        processFile(inputPath, outputPath, shift, "decrypt");
    }

    // Режим brute force для расшифровки
    private static void bruteForceMode(Scanner scanner) {
        System.out.print("Путь к зашифрованному файлу: ");
        String inputPath = scanner.nextLine();
        System.out.print("Директория для сохранения вариантов: ");
        String outputDir = scanner.nextLine();
        try {
            if (!new File(inputPath).exists()) throw new FileNotFoundException("Файл не найден.");
            String text = readFile(inputPath);
            if (text.trim().isEmpty()) throw new IOException("Файл пустой.");
            String alphabet = detectAlphabet(text);
            int maxShift = alphabet.length();
            File dir = new File(outputDir);
            if (!dir.exists() && !dir.mkdirs()) throw new IOException("Не удалось создать директорию.");
            for (int shift = 0; shift < maxShift; shift++) {
                String decrypted = processText(text, shift, "decrypt");
                String outputPath = outputDir + File.separator + "shift_" + shift + ".txt";
                writeFile(outputPath, decrypted);
            }
            System.out.println("Все варианты сохранены в: " + outputDir);
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // Валидация ключа (проверка корректности ввода)
    private static int readValidShift(Scanner scanner) {
        while (true) {
            try {
                int shift = Integer.parseInt(scanner.nextLine());
                if (shift < 0) {
                    System.out.println("Ключ должен быть положительным числом. Попробуйте снова.");
                    continue;
                }
                return shift;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Введите целое число.");
            }
        }
    }

    // Чтение файла
    private static String readFile(String path) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    // Запись в файл
    private static void writeFile(String path, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(content);
        }
    }
}