import java.io.*;
import java.util.*;

public class Caesar {
    // Константы для русского и английского алфавитов
    private static final String RUS_LOWER = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String RUS_UPPER = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final String ENG_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String ENG_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    // Частоты букв для русского и английского языков
    private static final Map<Character, Double> RUS_FREQUENCIES = Map.ofEntries(
        Map.entry('о', 0.1097), Map.entry('е', 0.0845), Map.entry('а', 0.0801),
        Map.entry('и', 0.0735), Map.entry('н', 0.0670), Map.entry('т', 0.0626),
        Map.entry('с', 0.0547), Map.entry('р', 0.0473), Map.entry('в', 0.0454),
        Map.entry('л', 0.0440), Map.entry('к', 0.0349), Map.entry('м', 0.0321),
        Map.entry('д', 0.0298), Map.entry('п', 0.0281), Map.entry('у', 0.0262),
        Map.entry('я', 0.0201), Map.entry('ы', 0.0190), Map.entry('ь', 0.0174),
        Map.entry('г', 0.0169), Map.entry('з', 0.0165), Map.entry('б', 0.0159),
        Map.entry('ч', 0.0144), Map.entry('й', 0.0121), Map.entry('х', 0.0097),
        Map.entry('ж', 0.0094), Map.entry('ш', 0.0073), Map.entry('ю', 0.0064),
        Map.entry('ц', 0.0048), Map.entry('щ', 0.0036), Map.entry('э', 0.0032),
        Map.entry('ф', 0.0026), Map.entry('ъ', 0.0004)
    );

    private static final Map<Character, Double> ENG_FREQUENCIES = Map.ofEntries(
        Map.entry('e', 0.1270), Map.entry('t', 0.0906), Map.entry('a', 0.0817),
        Map.entry('o', 0.0751), Map.entry('i', 0.0697), Map.entry('n', 0.0675),
        Map.entry('s', 0.0633), Map.entry('h', 0.0609), Map.entry('r', 0.0599),
        Map.entry('d', 0.0425), Map.entry('l', 0.0403), Map.entry('c', 0.0278),
        Map.entry('u', 0.0276), Map.entry('m', 0.0241), Map.entry('w', 0.0236),
        Map.entry('f', 0.0223), Map.entry('g', 0.0202), Map.entry('y', 0.0197),
        Map.entry('p', 0.0193), Map.entry('b', 0.0149), Map.entry('v', 0.0098),
        Map.entry('k', 0.0077), Map.entry('j', 0.0015), Map.entry('x', 0.0015),
        Map.entry('q', 0.0010), Map.entry('z', 0.0007)
    );

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Режимы работы:");
            System.out.println("1. Шифровать");
            System.out.println("2. Расшифровать с ключом");
            System.out.println("3. Brute Force");
            System.out.println("4. Статистический анализ");
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
                case "4" -> statisticalAnalysisMode(scanner);
                default -> System.out.println("Некорректный выбор.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // Основной метод шифрования/расшифровки текста
    private static String processText(String text, int shift, String mode) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (RUS_LOWER.indexOf(c) != -1) {
                // Обработка русских строчных букв
                int index = RUS_LOWER.indexOf(c);
                int newIndex = switch (mode) {
                    case "encrypt" -> (index + shift) % RUS_LOWER.length();
                    case "decrypt" -> (index - shift + RUS_LOWER.length()) % RUS_LOWER.length();
                    default -> index;
                };
                result.append(RUS_LOWER.charAt(newIndex));
            } else if (RUS_UPPER.indexOf(c) != -1) {
                // Обработка русских заглавных букв
                int index = RUS_UPPER.indexOf(c);
                int newIndex = switch (mode) {
                    case "encrypt" -> (index + shift) % RUS_UPPER.length();
                    case "decrypt" -> (index - shift + RUS_UPPER.length()) % RUS_UPPER.length();
                    default -> index;
                };
                result.append(RUS_UPPER.charAt(newIndex));
            } else if (ENG_LOWER.indexOf(c) != -1) {
                // Обработка английских строчных букв
                int index = ENG_LOWER.indexOf(c);
                int newIndex = switch (mode) {
                    case "encrypt" -> (index + shift) % ENG_LOWER.length();
                    case "decrypt" -> (index - shift + ENG_LOWER.length()) % ENG_LOWER.length();
                    default -> index;
                };
                result.append(ENG_LOWER.charAt(newIndex));
            } else if (ENG_UPPER.indexOf(c) != -1) {
                // Обработка английских заглавных букв
                int index = ENG_UPPER.indexOf(c);
                int newIndex = switch (mode) {
                    case "encrypt" -> (index + shift) % ENG_UPPER.length();
                    case "decrypt" -> (index - shift + ENG_UPPER.length()) % ENG_UPPER.length();
                    default -> index;
                };
                result.append(ENG_UPPER.charAt(newIndex));
            } else {
                // Символы, которые не входят в алфавит (пробелы, знаки препинания и т.д.)
                result.append(c);
            }
        }
        return result.toString();
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

            boolean isRussian = isRussian(text);
            String lowerAlphabet = isRussian ? RUS_LOWER : ENG_LOWER;

            int maxShift = lowerAlphabet.length();
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

    // Режим статистического анализа
    private static void statisticalAnalysisMode(Scanner scanner) {
        System.out.print("Путь к зашифрованному файлу: ");
        String inputPath = scanner.nextLine();
        System.out.print("Путь для расшифрованного файла: ");
        String outputPath = scanner.nextLine();
        try {
            if (!new File(inputPath).exists()) throw new FileNotFoundException("Файл не найден.");
            String encryptedText = readFile(inputPath);
            if (encryptedText.trim().isEmpty()) throw new IOException("Файл пустой.");

            boolean isRussian = isRussian(encryptedText);
            Map<Character, Double> frequencies = isRussian ? RUS_FREQUENCIES : ENG_FREQUENCIES;
            String lowerAlphabet = isRussian ? RUS_LOWER : ENG_LOWER;

            // Подсчет частот букв в зашифрованном тексте
            Map<Character, Integer> letterCounts = new HashMap<>();
            for (char c : encryptedText.toLowerCase().toCharArray()) {
                if (lowerAlphabet.indexOf(c) != -1) {
                    letterCounts.put(c, letterCounts.getOrDefault(c, 0) + 1);
                }
            }

            // Вычисление частот букв в зашифрованном тексте
            Map<Character, Double> encryptedFrequencies = new HashMap<>();
            int totalLetters = letterCounts.values().stream().mapToInt(Integer::intValue).sum();
            for (Map.Entry<Character, Integer> entry : letterCounts.entrySet()) {
                encryptedFrequencies.put(entry.getKey(), entry.getValue() / (double) totalLetters);
            }

            // Нахождение наиболее вероятного сдвига
            int bestShift = 0;
            double bestMatch = Double.MAX_VALUE;
            for (int shift = 0; shift < lowerAlphabet.length(); shift++) {
                double match = 0.0;
                for (Map.Entry<Character, Double> entry : encryptedFrequencies.entrySet()) {
                    char shiftedChar = lowerAlphabet.charAt((lowerAlphabet.indexOf(entry.getKey()) - shift + lowerAlphabet.length()) % lowerAlphabet.length());
                    match += Math.abs(entry.getValue() - frequencies.getOrDefault(shiftedChar, 0.0));
                }
                if (match < bestMatch) {
                    bestMatch = match;
                    bestShift = shift;
                }
            }

            // Расшифровка текста с найденным сдвигом
            String decryptedText = processText(encryptedText, bestShift, "decrypt");
            writeFile(outputPath, decryptedText);
            System.out.println("Расшифровка выполнена успешно! Ключ: " + bestShift);
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

    // Обработка файла: чтение, шифрование/расшифровка, запись
    private static void processFile(String inputPath, String outputPath, int shift, String mode) {
        try {
            if (!new File(inputPath).exists()) throw new FileNotFoundException("Файл не найден.");
            String text = readFile(inputPath);
            if (text.trim().isEmpty()) throw new IOException("Файл пустой.");
            String processedText = processText(text, shift, mode);
            writeFile(outputPath, processedText);
            System.out.println("Операция выполнена успешно.");
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    // Метод для определения, является ли текст русским
    private static boolean isRussian(String text) {
        for (char c : text.toCharArray()) {
            if (RUS_LOWER.indexOf(c) != -1 || RUS_UPPER.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }
}