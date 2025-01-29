import java.util.ArrayList;
import java.util.List;

public class SingletonEnum {

    // Задача 1: Класс базы данных как Singleton
    public static class DatabaseConnection {
        private static DatabaseConnection instance;

        private DatabaseConnection() {
            System.out.println("Создано подключение к базе данных.");
        }

        public static synchronized DatabaseConnection getInstance() {
            if (instance == null) {
                instance = new DatabaseConnection();
            }
            return instance;
        }
    }

    // Задача 2: Логирование в системе
    public static class Logger {
        private static Logger instance;
        private final List<String> logs;

        private Logger() {
            logs = new ArrayList<>();
        }

        public static synchronized Logger getInstance() {
            if (instance == null) {
                instance = new Logger();
            }
            return instance;
        }

        public void log(String message) {
            logs.add(message);
        }

        public void printLogs() {
            for (String log : logs) {
                System.out.println(log);
            }
        }
    }

    // Задача 3: Реализация статусов заказа
    public enum OrderStatus {
        NEW,
        IN_PROGRESS,
        DELIVERED,
        CANCELLED
    }

    public static class Order {
        private final String orderId;
        private OrderStatus status;

        public Order(String orderId) {
            this.orderId = orderId;
            this.status = OrderStatus.NEW;
        }

        public String getOrderId() {
            return orderId;
        }

        public OrderStatus getStatus() {
            return status;
        }

        public void changeStatus(OrderStatus newStatus) {
            if (!isValidTransition(newStatus)) {
                System.out.println("Недопустимый переход статуса.");
                return;
            }
            this.status = newStatus;
            System.out.println("Статус заказа " + orderId + " изменен на " + status);
        }

        private boolean isValidTransition(OrderStatus newStatus) {
            return switch (status) {
                case NEW -> newStatus == OrderStatus.IN_PROGRESS || newStatus == OrderStatus.CANCELLED;
                case IN_PROGRESS -> newStatus == OrderStatus.DELIVERED || newStatus == OrderStatus.CANCELLED;
                case DELIVERED -> false;
                case CANCELLED -> false;
                default -> false;
            }; 
        }
    }

    // Задача 4: Сезоны года
    public enum Season {
        WINTER,
        SPRING,
        SUMMER,
        AUTUMN
    }

    public static class SeasonUtils {
        public static String getSeasonName(Season season) {
            return switch (season) {
                case WINTER -> "Зима";
                case SPRING -> "Весна";
                case SUMMER -> "Лето";
                case AUTUMN -> "Осень";
                default -> "Неизвестно";
            };
        }
    }

    public static void main(String[] args) {
        // Задача 1: Singleton для базы данных
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        System.out.println(db1 == db2); // true, так как это один и тот же объект

        // Задача 2: Логирование
        Logger logger = Logger.getInstance();
        logger.log("Это первое сообщение лога.");
        logger.log("Это второе сообщение лога.");
        logger.printLogs();

        // Задача 3: Статусы заказа
        Order order = new Order("001");
        System.out.println("Текущий статус заказа: " + order.getStatus());
        order.changeStatus(OrderStatus.IN_PROGRESS);
        order.changeStatus(OrderStatus.DELIVERED);
        order.changeStatus(OrderStatus.CANCELLED); // Попытка отменить доставленный заказ

        // Задача 4: Сезоны года
        System.out.println("Русское название сезона: " + SeasonUtils.getSeasonName(Season.WINTER));
        System.out.println("Русское название сезона: " + SeasonUtils.getSeasonName(Season.SPRING));
        System.out.println("Русское название сезона: " + SeasonUtils.getSeasonName(Season.SUMMER));
        System.out.println("Русское название сезона: " + SeasonUtils.getSeasonName(Season.AUTUMN));
    }
}