import 'dart:io';
import 'dart:math';

// 1. Класс "Человек"
class Person {
  String name;
  int age;
  String gender;

  Person(this.name, this.age, this.gender);

  void displayInfo() {
    print('Имя: $name, Возраст: $age, Пол: $gender');
  }

  void increaseAge() => age++;
  void changeName(String newName) => name = newName;
}

// 2. Наследование: Работник и Менеджер
class Worker extends Person {
  double salary;

  Worker(String name, int age, String gender, this.salary)
      : super(name, age, gender);

  @override
  void displayInfo() {
    super.displayInfo();
    print('Зарплата: $salary');
  }
}

class Manager extends Worker {
  List<String> subordinates;

  Manager(String name, int age, String gender, double salary, this.subordinates)
      : super(name, age, gender, salary);

  @override
  void displayInfo() {
    super.displayInfo();
    print('Подчиненные: $subordinates');
  }
}

// 3. Полиморфизм: Животные
abstract class Animal {
  void makeSound();
}

class Dog implements Animal {
  @override
  void makeSound() => print('Гав-гав!');
}

class Cat implements Animal {
  @override
  void makeSound() => print('Мяу!');
}

class Cow implements Animal {
  @override
  void makeSound() => print('Мууу!');
}

// 4. Абстрактный транспорт
abstract class Transport {
  void move();
}

class Car implements Transport {
  @override
  void move() => print('Машина едет по дороге');
}

class Bike implements Transport {
  @override
  void move() => print('Велосипед едет по тропинке');
}

// 5. Библиотека и книги
class Book {
  String title;
  String author;
  int year;

  Book(this.title, this.author, this.year);
}

class Library {
  List<Book> books = [];

  void addBook(Book book) => books.add(book);
  
  void findBooksByAuthor(String author) {
    final result = books.where((b) => b.author == author);
    if (result.isEmpty) {
      print('Книги автора $author не найдены');
      return;
    }
    print('Найдены книги автора $author:');
    result.forEach((b) => print('${b.title} (${b.year})'));
  }
}

// 6. Инкапсуляция: Банк
class BankAccount {
  String _accountNumber;
  double _balance;

  BankAccount(this._accountNumber, this._balance);

  String get accountNumber => _accountNumber;

  void deposit(double amount) {
    if (amount > 0) {
      _balance += amount;
      print('Успешное пополнение. Баланс: $_balance');
    }
  }

  void withdraw(double amount) {
    if (amount > 0 && amount <= _balance) {
      _balance -= amount;
      print('Успешное снятие. Баланс: $_balance');
    }
  }
}

// 7. Счетчик объектов
class Counter {
  static int _count = 0;

  Counter() {
    _count++;
  }

  static int get count => _count;
}

// 8. Площадь фигур
abstract class Shape {
  double getArea();
}

class Circle implements Shape {
  double radius;

  Circle(this.radius);

  @override
  double getArea() => pi * radius * radius;
}

class Rectangle implements Shape {
  double width;
  double height;

  Rectangle(this.width, this.height);

  @override
  double getArea() => width * height;
}

// 9. Движения животных
class BaseAnimal {
  void move() => print('Животное двигается');
}

class Fish extends BaseAnimal {
  @override
  void move() => print('Рыба плавает');
}

class Bird extends BaseAnimal {
  @override
  void move() => print('Птица летает');
}

class DogAnimal extends BaseAnimal {
  @override
  void move() => print('Собака бегает');
}

// 10. Университет
class Student {
  String name;
  String group;
  double grade;

  Student(this.name, this.group, this.grade);
}

class University {
  List<Student> students = [];

  void addStudent(Student student) => students.add(student);
  
  void sortByName() => students.sort((a, b) => a.name.compareTo(b.name));
  
  void filterByGrade(double minGrade) {
    final result = students.where((s) => s.grade >= minGrade);
    if (result.isEmpty) {
      print('Студенты с оценкой выше $minGrade не найдены');
      return;
    }
    print('Студенты с оценкой выше $minGrade:');
    result.forEach((s) => print('${s.name} (${s.group}): ${s.grade}'));
  }
}

// 11. Класс "Магазин"
class Product {
  String name;
  double price;
  int quantity;

  Product(this.name, this.price, this.quantity);
}

class Store {
  List<Product> products = [];

  void addProduct(Product product) => products.add(product);

  void removeProduct(String name) {
    products.removeWhere((p) => p.name == name);
  }

  void findProduct(String name) {
    final result = products.where((p) => p.name == name);
    if (result.isEmpty) {
      print('Товар "$name" не найден');
      return;
    }
    print('Найден товар "$name":');
    result.forEach((p) => print('${p.name} - Цена: ${p.price}, Количество: ${p.quantity}'));
  }
}

// 12. Интерфейс "Платежная система"
abstract class PaymentSystem {
  void pay(double amount);
  void refund(double amount);
}

class CreditCard implements PaymentSystem {
  @override
  void pay(double amount) => print('Оплата $amount через кредитную карту');

  @override
  void refund(double amount) => print('Возврат $amount на кредитную карту');
}

class PayPal implements PaymentSystem {
  @override
  void pay(double amount) => print('Оплата $amount через PayPal');

  @override
  void refund(double amount) => print('Возврат $amount на PayPal');
}

// 13. Генерация уникальных идентификаторов
class UniqueID {
  static int _nextID = 1;

  static int generate() => _nextID++;
}

// 14. Класс "Точка" и "Прямоугольник"
class Point {
  double x;
  double y;

  Point(this.x, this.y);
}

class RectangleWithPoints {
  Point topLeft;
  Point bottomRight;

  RectangleWithPoints(this.topLeft, this.bottomRight);

  double getArea() {
    final width = (bottomRight.x - topLeft.x).abs();
    final height = (bottomRight.y - topLeft.y).abs();
    return width * height;
  }
}

// 15. Комплексные числа
class ComplexNumber {
  double real;
  double imaginary;

  ComplexNumber(this.real, this.imaginary);

  ComplexNumber add(ComplexNumber other) {
    return ComplexNumber(real + other.real, imaginary + other.imaginary);
  }

  ComplexNumber subtract(ComplexNumber other) {
    return ComplexNumber(real - other.real, imaginary - other.imaginary);
  }

  ComplexNumber multiply(ComplexNumber other) {
    return ComplexNumber(
      real * other.real - imaginary * other.imaginary,
      real * other.imaginary + imaginary * other.real,
    );
  }

  ComplexNumber divide(ComplexNumber other) {
    final denominator = other.real * other.real + other.imaginary * other.imaginary;
    return ComplexNumber(
      (real * other.real + imaginary * other.imaginary) / denominator,
      (imaginary * other.real - real * other.imaginary) / denominator,
    );
  }

  @override
  String toString() => '$real + ${imaginary}i';
}

// 16. Перегрузка операторов: Матрица
class Matrix {
  List<List<int>> data;

  Matrix(this.data);

  Matrix operator +(Matrix other) {
    final result = <List<int>>[];
    for (var i = 0; i < data.length; i++) {
      final row = <int>[];
      for (var j = 0; j < data[i].length; j++) {
        row.add(data[i][j] + other.data[i][j]);
      }
      result.add(row);
    }
    return Matrix(result);
  }

  Matrix operator *(Matrix other) {
    final result = <List<int>>[];
    for (var i = 0; i < data.length; i++) {
      final row = <int>[];
      for (var j = 0; j < other.data[0].length; j++) {
        var sum = 0;
        for (var k = 0; k < data[i].length; k++) {
          sum += data[i][k] * other.data[k][j];
        }
        row.add(sum);
      }
      result.add(row);
    }
    return Matrix(result);
  }

  @override
  String toString() => data.map((row) => row.join(' ')).join('\n');
}

// 17. Создание игры с использованием ООП
class Player {
  String name;
  int health;

  Player(this.name, this.health);

  void attack(Enemy enemy) {
    print('$name атакует ${enemy.name}');
    enemy.takeDamage(10);
  }
}

class Enemy {
  String name;
  int health;

  Enemy(this.name, this.health);

  void takeDamage(int damage) {
    health -= damage;
    print('$name получает урон $damage. Осталось здоровья: $health');
  }
}

class Weapon {
  String name;
  int damage;

  Weapon(this.name, this.damage);
}

// 18. Автоматизированная система заказов
class Order {
  String id;
  List<Product> products;

  Order(this.id, this.products);

  double getTotalCost() => products.fold(0, (sum, p) => sum + p.price * p.quantity);
}

class Customer {
  String name;
  List<Order> orders = [];

  Customer(this.name);

  void addOrder(Order order) => orders.add(order);

  void displayOrderHistory() {
    print('История заказов для $name:');
    for (var order in orders) {
      print('Заказ ${order.id}, Общая стоимость: ${order.getTotalCost()}');
    }
  }
}

// 19. Наследование: Электроника
class Device {
  String brand;

  Device(this.brand);

  void turnOn() => print('Устройство $brand включено');
  void turnOff() => print('Устройство $brand выключено');
}

class Smartphone extends Device {
  Smartphone(String brand) : super(brand);

  void takePhoto() => print('Смартфон $brand делает фото');
}

class Laptop extends Device {
  Laptop(String brand) : super(brand);

  void openLid() => print('Ноутбук $brand открывает крышку');
}

// 20. Игра "Крестики-нолики"
class TicTacToe {
  List<List<String>> board = List.generate(3, (_) => List.filled(3, ' '));

  void displayBoard() {
    for (var row in board) {
      print(row.join(' | '));
    }
  }

  bool makeMove(int row, int col, String player) {
    if (board[row][col] == ' ') {
      board[row][col] = player;
      return true;
    }
    return false;
  }

  bool checkWin(String player) {
    for (var i = 0; i < 3; i++) {
      if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
      if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
    }
    if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
    if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
    return false;
  }
}

// Функции для выполнения задач
void task1() {
  print('Введите имя:');
  final name = stdin.readLineSync() ?? 'Неизвестно';
  print('Введите возраст:');
  final age = int.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  print('Введите пол:');
  final gender = stdin.readLineSync() ?? 'Неизвестно';

  final person = Person(name, age, gender);
  person.displayInfo();
  person.increaseAge();
  print('Введите новое имя:');
  final newName = stdin.readLineSync() ?? name;
  person.changeName(newName);
  person.displayInfo();
}

void task2() {
  print('Введите имя работника:');
  final name = stdin.readLineSync() ?? 'Неизвестно';
  print('Введите возраст работника:');
  final age = int.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  print('Введите пол работника:');
  final gender = stdin.readLineSync() ?? 'Неизвестно';
  print('Введите зарплату работника:');
  final salary = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;

  final worker = Worker(name, age, gender, salary);
  worker.displayInfo();

  print('Введите количество подчиненных менеджера:');
  final count = int.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  final subordinates = <String>[];
  for (var i = 0; i < count; i++) {
    print('Введите имя подчиненного ${i + 1}:');
    subordinates.add(stdin.readLineSync() ?? 'Неизвестно');
  }

  final manager = Manager(name, age, gender, salary, subordinates);
  manager.displayInfo();
}

void task3() {
  final animals = [Dog(), Cat(), Cow()];
  animals.forEach((a) => a.makeSound());
}

void task4() {
  final transports = [Car(), Bike()];
  transports.forEach((t) => t.move());
}

void task5() {
  final library = Library();
  print('Введите количество книг:');
  final count = int.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  for (var i = 0; i < count; i++) {
    print('Введите название книги ${i + 1}:');
    final title = stdin.readLineSync() ?? 'Неизвестно';
    print('Введите автора книги ${i + 1}:');
    final author = stdin.readLineSync() ?? 'Неизвестно';
    print('Введите год издания книги ${i + 1}:');
    final year = int.tryParse(stdin.readLineSync() ?? '0') ?? 0;
    library.addBook(Book(title, author, year));
  }

  print('Введите автора для поиска:');
  final author = stdin.readLineSync() ?? '';
  library.findBooksByAuthor(author);
}

void task6() {
  print('Введите номер счета:');
  final accountNumber = stdin.readLineSync() ?? 'Неизвестно';
  print('Введите начальный баланс:');
  final balance = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;

  final account = BankAccount(accountNumber, balance);
  print('Введите сумму для пополнения:');
  final depositAmount = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  account.deposit(depositAmount);

  print('Введите сумму для снятия:');
  final withdrawAmount = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  account.withdraw(withdrawAmount);

  print('Номер счета: ${account.accountNumber}');
}

void task7() {
  Counter();
  Counter();
  print('Создано объектов: ${Counter.count}');
}

void task8() {
  print('Введите радиус круга:');
  final radius = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  final circle = Circle(radius);
  print('Площадь круга: ${circle.getArea()}');

  print('Введите ширину прямоугольника:');
  final width = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  print('Введите высоту прямоугольника:');
  final height = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  final rectangle = Rectangle(width, height);
  print('Площадь прямоугольника: ${rectangle.getArea()}');
}

void task9() {
  final animals = [Fish(), Bird(), DogAnimal()];
  animals.forEach((a) => a.move());
}

void task10() {
  final university = University();
  print('Введите количество студентов:');
  final count = int.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  for (var i = 0; i < count; i++) {
    print('Введите имя студента ${i + 1}:');
    final name = stdin.readLineSync() ?? 'Неизвестно';
    print('Введите группу студента ${i + 1}:');
    final group = stdin.readLineSync() ?? 'Неизвестно';
    print('Введите оценку студента ${i + 1}:');
    final grade = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
    university.addStudent(Student(name, group, grade));
  }

  university.sortByName();
  print('Введите минимальную оценку для фильтрации:');
  final minGrade = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  university.filterByGrade(minGrade);
}

void task11() {
  final store = Store();
  print('Введите количество товаров:');
  final count = int.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  for (var i = 0; i < count; i++) {
    print('Введите название товара ${i + 1}:');
    final name = stdin.readLineSync() ?? 'Неизвестно';
    print('Введите цену товара ${i + 1}:');
    final price = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
    print('Введите количество товара ${i + 1}:');
    final quantity = int.tryParse(stdin.readLineSync() ?? '0') ?? 0;
    store.addProduct(Product(name, price, quantity));
  }

  print('Введите название товара для поиска:');
  final productName = stdin.readLineSync() ?? '';
  store.findProduct(productName);
}

void task12() {
  final paymentSystem = CreditCard();
  print('Введите сумму для оплаты:');
  final amount = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  paymentSystem.pay(amount);

  print('Введите сумму для возврата:');
  final refundAmount = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  paymentSystem.refund(refundAmount);
}

void task13() {
  print('Сгенерированный ID: ${UniqueID.generate()}');
}

void task14() {
  print('Введите координаты верхнего левого угла (x y):');
  final topLeftInput = stdin.readLineSync()?.split(' ') ?? ['0', '0'];
  final topLeft = Point(double.parse(topLeftInput[0]), double.parse(topLeftInput[1]));

  print('Введите координаты нижнего правого угла (x y):');
  final bottomRightInput = stdin.readLineSync()?.split(' ') ?? ['0', '0'];
  final bottomRight = Point(double.parse(bottomRightInput[0]), double.parse(bottomRightInput[1]));

  final rectangle = RectangleWithPoints(topLeft, bottomRight);
  print('Площадь прямоугольника: ${rectangle.getArea()}');
}

void task15() {
  print('Введите действительную часть первого числа:');
  final real1 = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  print('Введите мнимую часть первого числа:');
  final imaginary1 = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  final num1 = ComplexNumber(real1, imaginary1);

  print('Введите действительную часть второго числа:');
  final real2 = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  print('Введите мнимую часть второго числа:');
  final imaginary2 = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  final num2 = ComplexNumber(real2, imaginary2);

  print('Сумма: ${num1.add(num2)}');
  print('Разность: ${num1.subtract(num2)}');
  print('Произведение: ${num1.multiply(num2)}');
  print('Частное: ${num1.divide(num2)}');
}

void task16() {
  print('Введите размер матрицы (строки столбцы):');
  final sizeInput = stdin.readLineSync()?.split(' ') ?? ['2', '2'];
  final rows = int.parse(sizeInput[0]);
  final cols = int.parse(sizeInput[1]);

  print('Введите элементы первой матрицы:');
  final matrix1 = _readMatrix(rows, cols);

  print('Введите элементы второй матрицы:');
  final matrix2 = _readMatrix(rows, cols);

  final resultAdd = matrix1 + matrix2;
  print('Результат сложения:\n$resultAdd');

  final resultMul = matrix1 * matrix2;
  print('Результат умножения:\n$resultMul');
}

Matrix _readMatrix(int rows, int cols) {
  final data = <List<int>>[];
  for (var i = 0; i < rows; i++) {
    print('Введите строку ${i + 1}:');
    final rowInput = stdin.readLineSync()?.split(' ') ?? List.filled(cols, '0');
    final row = rowInput.map(int.parse).toList();
    data.add(row);
  }
  return Matrix(data);
}

void task17() {
  final player = Player('Игрок', 100);
  final enemy = Enemy('Враг', 50);
  final weapon = Weapon('Меч', 15);

  print('${player.name} атакует ${enemy.name} с помощью ${weapon.name}');
  player.attack(enemy);
}

void task18() {
  final customer = Customer('Покупатель');
  print('Введите количество заказов:');
  final orderCount = int.tryParse(stdin.readLineSync() ?? '0') ?? 0;
  for (var i = 0; i < orderCount; i++) {
    print('Введите количество товаров в заказе ${i + 1}:');
    final productCount = int.tryParse(stdin.readLineSync() ?? '0') ?? 0;
    final products = <Product>[];
    for (var j = 0; j < productCount; j++) {
      print('Введите название товара ${j + 1}:');
      final name = stdin.readLineSync() ?? 'Неизвестно';
      print('Введите цену товара ${j + 1}:');
      final price = double.tryParse(stdin.readLineSync() ?? '0') ?? 0;
      print('Введите количество товара ${j + 1}:');
      final quantity = int.tryParse(stdin.readLineSync() ?? '0') ?? 0;
      products.add(Product(name, price, quantity));
    }
    customer.addOrder(Order('Заказ ${i + 1}', products));
  }
  customer.displayOrderHistory();
}

void task19() {
  final smartphone = Smartphone('Samsung');
  smartphone.turnOn();
  smartphone.takePhoto();
  smartphone.turnOff();

  final laptop = Laptop('Lenovo');
  laptop.turnOn();
  laptop.openLid();
  laptop.turnOff();
}

void task20() {
  final game = TicTacToe();
  var currentPlayer = 'X';

  while (true) {
    game.displayBoard();
    print('Ход игрока $currentPlayer');
    print('Введите строку и столбец (0-2):');
    final moveInput = stdin.readLineSync()?.split(' ') ?? ['0', '0'];
    final row = int.parse(moveInput[0]);
    final col = int.parse(moveInput[1]);

    if (game.makeMove(row, col, currentPlayer)) {
      if (game.checkWin(currentPlayer)) {
        print('Игрок $currentPlayer выиграл!');
        break;
      }
      currentPlayer = currentPlayer == 'X' ? 'O' : 'X';
    } else {
      print('Недопустимый ход!');
    }
  }
}

// Главное меню
void main() {
  final tasks = {
    1: task1,
    2: task2,
    3: task3,
    4: task4,
    5: task5,
    6: task6,
    7: task7,
    8: task8,
    9: task9,
    10: task10,
    11: task11,
    12: task12,
    13: task13,
    14: task14,
    15: task15,
    16: task16,
    17: task17,
    18: task18,
    19: task19,
    20: task20,
  };

  while (true) {
    print('\n=== Меню задач ООП ===');
    print('Выберите задачу (1-20) или 0 для выхода:');
    
    final input = stdin.readLineSync();
    final choice = int.tryParse(input ?? '') ?? 0;

    if (choice == 0) {
      print('Выход из программы');
      break;
    }

    final task = tasks[choice];
    if (task != null) {
      print('\n=== Задача $choice ===');
      task();
    } else {
      print('Неверный выбор!');
    }
  }
}