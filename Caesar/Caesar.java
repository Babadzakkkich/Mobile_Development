import java.io.*;
import java.util.Scanner;

public class Caesar {
    // Алфавиты для русского и английского языков (в нижнем регистре)
    private static final String RUS_ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String ENG_ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Режимы работы:");
            System.out.println("1. Шифровать текст");
            System.out.println("2. Расшифровать текст с ключом");
            System.out.println("3. Расшифровать методом Brute Force");
            System.out.print("Выберите режим (1/2/3): ");
            String choice = scanner.nextLine();

            // Обработка выбора пользователя
            switch (choice) {
                case "1" -> encryptMode(scanner);
                case "2" -> decryptWithKey(scanner);
                case "3" -> bruteForceMode(scanner);
                default -> System.out.println("Некорректный выбор.");
            }
            scanner.close();
        }
    }

    /**
     * Определяет алфавит на основе текста.
     * Проверяет каждый символ: если найден в русском алфавите — возвращает его,
     * иначе проверяет английский. По умолчанию — русский.
     */
    private static String detectAlphabet(String text) {
        text = text.toLowerCase();
        for (char c : text.toCharArray()) {
            if (RUS_ALPHABET.indexOf(c) != -1) return RUS_ALPHABET;
            if (ENG_ALPHABET.indexOf(c) != -1) return ENG_ALPHABET;
        }
        return RUS_ALPHABET; // Если текст пуст или нет букв
    }

    /**
     * Шифрует или расшифровывает текст.
     * @param text Исходный текст
     * @param shift Сдвиг (ключ)
     * @param mode Режим: "encrypt" (шифрование) или "decrypt" (расшифровка)
     * @return Обработанный текст
     */
    private static String processText(String text, int shift, String mode) {
        String alphabet = detectAlphabet(text);
        int len = alphabet.length();
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            // Если символ не в алфавите — оставляем как есть (пробелы, знаки препинания)
            if (alphabet.indexOf(Character.toLowerCase(c)) == -1) {
                result.append(c);
                continue;
            }

            // Сохраняем регистр символа
            boolean isUpper = Character.isUpperCase(c);
            int index = alphabet.indexOf(Character.toLowerCase(c));

            // Вычисляем новый индекс с учётом сдвига
            int newIndex = switch (mode) {
                case "encrypt" -> (index + shift) % len; // Шифрование: сдвиг вправо
                case "decrypt" -> (index - shift + len) % len; // Расшифровка: сдвиг влево
                default -> index; // На случай ошибки
            };

            // Восстанавливаем регистр и добавляем символ в результат
            char newChar = alphabet.charAt(newIndex);
            result.append(isUpper ? Character.toUpperCase(newChar) : newChar);
        }
        return result.toString();
    }

    /**
     * Обрабатывает файл: читает, применяет шифр, сохраняет результат.
     * @param inputPath Путь к исходному файлу
     * @param outputPath Путь для сохранения
     * @param shift Ключ
     * @param mode Режим работы
     */
    private static void processFile(String inputPath, String outputPath, int shift, String mode) {
        try {
            String text = readFile(inputPath);
            String processedText = processText(text, shift, mode);
            writeFile(outputPath, processedText);
            System.out.println("Операция выполнена успешно!");
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // Режим шифрования
    private static void encryptMode(Scanner scanner) {
        System.out.print("Введите путь к исходному файлу: ");
        String inputPath = scanner.nextLine();
        System.out.print("Введите путь для зашифрованного файла: ");
        String outputPath = scanner.nextLine();
        System.out.print("Введите ключ (число): ");
        int shift = Integer.parseInt(scanner.nextLine());
        processFile(inputPath, outputPath, shift, "encrypt");
    }

    // Режим расшифровки с ключом
    private static void decryptWithKey(Scanner scanner) {
        System.out.print("Введите путь к зашифрованному файлу: ");
        String inputPath = scanner.nextLine();
        System.out.print("Введите путь для расшифрованного файла: ");
        String outputPath = scanner.nextLine();
        System.out.print("Введите ключ: ");
        int shift = Integer.parseInt(scanner.nextLine());
        processFile(inputPath, outputPath, shift, "decrypt");
    }

    /**
     * Режим Brute Force: перебирает все возможные ключи и сохраняет результаты.
     * Для каждого ключа создаётся отдельный файл в указанной директории.
     */
    private static void bruteForceMode(Scanner scanner) {
        System.out.print("Введите путь к зашифрованному файлу: ");
        String inputPath = scanner.nextLine();
        System.out.print("Введите директорию для сохранения вариантов: ");
        String outputDir = scanner.nextLine();

        try {
            String text = readFile(inputPath);
            String alphabet = detectAlphabet(text);
            int maxShift = alphabet.length();

            // Создаём директорию, если её нет
            File dir = new File(outputDir);
            if (!dir.exists() && !dir.mkdirs()) {
                throw new IOException("Не удалось создать директорию");
            }

            // Перебор всех возможных сдвигов
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

    /**
     * Чтение файла в строку с обработкой исключений.
     * Используется BufferedReader для эффективного чтения больших файлов.
     */
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

    /**
     * Запись строки в файл.
     * Используется BufferedWriter для эффективной записи.
     */
    private static void writeFile(String path, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(content);
        }
    }
}