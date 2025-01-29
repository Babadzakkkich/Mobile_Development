import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class DateTime{
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int choice;
            do { 
                System.out.println("Выберите задачу (1-15) или введите 0 для выхода:");
                choice = scanner.nextInt();
                switch (choice) {
                    case 1 -> task1();
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
                    case 0 -> System.out.println("Выход из программы.");
                    default -> System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } while (choice != 0);
        }
    }

    // Task 1: Основы LocalDate и LocalTime
    public static void task1() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalDateTime currentDateTime = LocalDateTime.of(currentDate, currentTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        System.out.println("Текущая дата и время: " + formattedDateTime);
    }

    // Task 2: Сравнение дат
    public static void task2(Scanner scanner) {
        System.out.println("Введите первую дату (yyyy-MM-dd):");
        String dateStr1 = scanner.next();
        LocalDate date1 = LocalDate.parse(dateStr1);

        System.out.println("Введите вторую дату (yyyy-MM-dd):");
        String dateStr2 = scanner.next();
        LocalDate date2 = LocalDate.parse(dateStr2);

        if (date1.isAfter(date2)) {
            System.out.println("Первая дата позже второй.");
        } else if (date1.isBefore(date2)) {
            System.out.println("Первая дата раньше второй.");
        } else {
            System.out.println("Даты равны.");
        }
    }

    // Task 3: Сколько дней до Нового года?
    public static void task3() {
        LocalDate today = LocalDate.now();
        LocalDate newYear = LocalDate.of(today.getYear(), 12, 31).plusDays(1); // 1 января следующего года
        if (today.isAfter(newYear.minusDays(1))) {
            newYear = newYear.plusYears(1);
        }
        long daysUntilNewYear = ChronoUnit.DAYS.between(today, newYear);
        System.out.println("До Нового года осталось " + daysUntilNewYear + " дней.");
    }

    // Task 4: Проверка високосного года
    public static void task4(Scanner scanner) {
        System.out.println("Введите год:");
        int year = scanner.nextInt();
        boolean isLeap = Year.isLeap(year);
        System.out.println("Год " + year + " " + (isLeap ? "високосный" : "не високосный") + ".");
    }

    // Task 5: Подсчет выходных за месяц
    public static void task5(Scanner scanner) {
        System.out.println("Введите год:");
        int year = scanner.nextInt();
        System.out.println("Введите месяц (1-12):");
        int month = scanner.nextInt();

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        long weekendsCount = startOfMonth.datesUntil(endOfMonth.plusDays(1))
                                        .filter(date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
                                        .count();

        System.out.println("Количество выходных в этом месяце: " + weekendsCount);
    }

    // Task 6: Расчет времени выполнения метода
    public static void task6(Scanner scanner) {
        System.out.println("Измерение времени выполнения цикла из 1 миллиона итераций...");
        long startTime = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            // Простой цикл без действий внутри
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  // В наносекундах
        System.out.println("Время выполнения: " + duration + " наносекунд.");
    }

    // Task 7: Форматирование и парсинг даты
    public static void task7(Scanner scanner) {
        System.out.println("Введите дату в формате dd-MM-yyyy:");
        String inputDate = scanner.next();
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(inputDate, inputFormatter);
        LocalDate newDate = date.plusDays(10);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedDate = newDate.format(outputFormatter);
        System.out.println("Дата через 10 дней: " + formattedDate);
    }

    // Task 8: Конвертация между часовыми поясами
    public static void task8(Scanner scanner) {
        System.out.println("Введите дату и время в UTC (yyyy-MM-dd HH:mm):");
        String dateTimeStr = scanner.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of("UTC"));
        ZonedDateTime utcDateTime = ZonedDateTime.parse(dateTimeStr + ":00", formatter);
        ZonedDateTime moscowDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Europe/Moscow"));
        System.out.println("Дата и время в Europe/Moscow: " + moscowDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    // Task 9: Вычисление возраста по дате рождения
    public static void task9(Scanner scanner) {
        System.out.println("Введите дату рождения (yyyy-MM-dd):");
        String birthDateString = scanner.next();
        LocalDate birthDate = LocalDate.parse(birthDateString);
        LocalDate now = LocalDate.now();
        Period age = Period.between(birthDate, now);
        System.out.println("Возраст: " + age.getYears() + " лет, " + age.getMonths() + " месяцев, " + age.getDays() + " дней.");
    }

    // Task 10: Создание календаря на месяц
    public static void task10(Scanner scanner) {
        System.out.println("Введите год:");
        int year = scanner.nextInt();
        System.out.println("Введите месяц (1-12):");
        int month = scanner.nextInt();

        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        startOfMonth.datesUntil(endOfMonth.plusDays(1))
                    .forEach(date -> {
                        String dayType = (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) ? "выходной" : "рабочий";
                        System.out.println(date + " - " + dayType);
                    });
    }

    // Task 11: Генерация случайной даты в диапазоне
    public static void task11(Scanner scanner) {
        System.out.println("Введите первую дату (yyyy-MM-dd):");
        String dateStr1 = scanner.next();
        LocalDate startDate = LocalDate.parse(dateStr1);

        System.out.println("Введите вторую дату (yyyy-MM-dd):");
        String dateStr2 = scanner.next();
        LocalDate endDate = LocalDate.parse(dateStr2);

        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);

        System.out.println("Случайная дата: " + randomDate);
    }

    // Task 12: Расчет времени до заданной даты
    public static void task12(Scanner scanner) {
        System.out.println("Введите дату и время события (yyyy-MM-dd HH:mm):");
        String eventDateTimeStr = scanner.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime eventDateTime = LocalDateTime.parse(eventDateTimeStr, formatter);

        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, eventDateTime);

        if (duration.isNegative()) {
            System.out.println("Событие уже прошло.");
        } else {
            long hours = duration.toHours();
            long minutes = duration.minusHours(hours).toMinutes();
            long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();
            System.out.println("До события осталось: " + hours + " часов, " + minutes + " минут, " + seconds + " секунд.");
        }
    }

    // Task 13: Вычисление количества рабочих часов
    public static void task13(Scanner scanner) {
        System.out.println("Введите начало рабочего дня (yyyy-MM-dd HH:mm):");
        String startDateTimeStr = scanner.next();
        LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        System.out.println("Введите конец рабочего дня (yyyy-MM-dd HH:mm):");
        String endDateTimeStr = scanner.next();
        LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        long totalWorkingHours = ChronoUnit.HOURS.between(startDateTime, endDateTime);
        for (LocalDateTime date = startDateTime; !date.isAfter(endDateTime); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                totalWorkingHours -= 24;
            }
        }
        System.out.println("Количество рабочих часов: " + totalWorkingHours);
    }

    // Task 14: Конвертация даты в строку с учетом локали
    public static void task14(Scanner scanner) {
        System.out.println("Введите дату (yyyy-MM-dd):");
        String dateStr = scanner.next();
        LocalDate date = LocalDate.parse(dateStr);
        
        System.out.println("Введите локаль (например, ru или en):");
        String localeStr = scanner.next();
        Locale locale = Locale.forLanguageTag(localeStr);

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(java.time.format.FormatStyle.FULL).withLocale(locale);
        String formattedDate = date.format(formatter);
        System.out.println("Дата в локали " + localeStr + ": " + formattedDate);
    }

    // Task 15: Определение дня недели по дате
    public static void task15(Scanner scanner) {
        System.out.println("Введите дату (yyyy-MM-dd):");
        String dateStr = scanner.next();
        LocalDate date = LocalDate.parse(dateStr);
        String dayOfWeek = switch (date.getDayOfWeek()) {
            case MONDAY -> "понедельник";
            case TUESDAY -> "вторник";
            case WEDNESDAY -> "среда";
            case THURSDAY -> "четверг";
            case FRIDAY -> "пятница";
            case SATURDAY -> "суббота";
            case SUNDAY -> "воскресенье";
            default -> "";
        };
        System.out.println("День недели: " + dayOfWeek);
    }
}