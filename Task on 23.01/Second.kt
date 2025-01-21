import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

// Исключение для слабого пароля
class WeakPasswordException(message: String) : Exception(message)

fun main() {
    val tasks = mapOf(
        1 to { findMax() },
        2 to { divideNumbers() },
        3 to { convertStringToInt() },
        4 to { checkAge() },
        5 to { findSquareRoot() },
        6 to { calculateFactorial() },
        7 to { checkArrayForZeros() },
        8 to { powerCalculator() },
        9 to { trimString() },
        10 to { findElementInArray() },
        11 to { convertToBinary() },
        12 to { checkDivisibility() },
        13 to { getListElement() },
        14 to { checkPasswordStrength() },
        15 to { validateDate() },
        16 to { concatenateStrings() },
        17 to { findRemainder() },
        18 to { calculateSquareRoot() },
        19 to { convertTemperature() },
        20 to { checkStringEmpty() }
    )

    while (true) {
        println("Выберите задачу (1-20) или введите 0 для выхода:")
        val choice = readLine()?.toIntOrNull() ?: 0

        if (choice == 0) {
            println("Выход из программы.")
            break
        }

        val task = tasks[choice]
        if (task != null) {
            try {
                task()
            } catch (e: Exception) {
                println("Ошибка: ${e.message}")
            }
        } else {
            println("Неверный выбор. Попробуйте снова.")
        }
    }
}

// 1. Функция для нахождения максимума
fun findMax() {
    println("Введите два числа:")
    val a = readLine()?.toIntOrNull() ?: throw NumberFormatException("Некорректный ввод числа")
    val b = readLine()?.toIntOrNull() ?: throw NumberFormatException("Некорректный ввод числа")

    if (a == b) {
        throw IllegalArgumentException("Числа равны")
    }
    println("Максимальное число: ${maxOf(a, b)}")
}

// 2. Калькулятор деления
fun divideNumbers() {
    println("Введите делимое и делитель:")
    val a = readLine()?.toDoubleOrNull() ?: throw NumberFormatException("Некорректный ввод числа")
    val b = readLine()?.toDoubleOrNull() ?: throw NumberFormatException("Некорректный ввод числа")

    if (b == 0.0) {
        throw ArithmeticException("Деление на ноль недопустимо")
    }
    println("Результат деления: ${a / b}")
}

// 3. Конвертер строк в числа
fun convertStringToInt() {
    println("Введите строку для конвертации в число:")
    val input = readLine() ?: throw NumberFormatException("Пустой ввод")
    val number = input.toIntOrNull() ?: throw NumberFormatException("Некорректный формат числа")
    println("Конвертированное число: $number")
}

// 4. Проверка возраста
fun checkAge() {
    println("Введите возраст:")
    val age = readLine()?.toIntOrNull() ?: throw NumberFormatException("Некорректный ввод возраста")

    if (age < 0 || age > 150) {
        throw IllegalArgumentException("Возраст должен быть от 0 до 150")
    }
    println("Возраст корректен: $age")
}

// 5. Нахождение корня
fun findSquareRoot() {
    println("Введите число для нахождения квадратного корня:")
    val number = readLine()?.toDoubleOrNull() ?: throw NumberFormatException("Некорректный ввод числа")

    if (number < 0) {
        throw IllegalArgumentException("Число не может быть отрицательным")
    }
    println("Квадратный корень: ${Math.sqrt(number)}")
}

// 6. Факториал
fun calculateFactorial() {
    println("Введите число для вычисления факториала:")
    val number = readLine()?.toIntOrNull() ?: throw NumberFormatException("Некорректный ввод числа")

    if (number < 0) {
        throw IllegalArgumentException("Число не может быть отрицательным")
    }
    println("Факториал: ${factorial(number)}")
}

fun factorial(n: Int): Long {
    return if (n <= 1) 1 else n * factorial(n - 1)
}

// 7. Проверка массива на нули
fun checkArrayForZeros() {
    println("Введите элементы массива через пробел:")
    val input = readLine() ?: throw IllegalArgumentException("Пустой ввод")
    val array = input.split(" ").map { it.toInt() }

    if (array.contains(0)) {
        throw IllegalArgumentException("Массив содержит нули")
    }
    println("Массив не содержит нулей")
}

// 8. Калькулятор возведения в степень
fun powerCalculator() {
    println("Введите число и степень:")
    val number = readLine()?.toDoubleOrNull() ?: throw NumberFormatException("Некорректный ввод числа")
    val power = readLine()?.toIntOrNull() ?: throw NumberFormatException("Некорректный ввод степени")

    if (power < 0) {
        throw IllegalArgumentException("Степень не может быть отрицательной")
    }
    println("Результат: ${Math.pow(number, power.toDouble())}")
}

// 9. Обрезка строки
fun trimString() {
    println("Введите строку и количество символов для обрезки:")
    val input = readLine() ?: throw IllegalArgumentException("Пустой ввод")
    val length = readLine()?.toIntOrNull() ?: throw NumberFormatException("Некорректный ввод длины")

    if (length > input.length) {
        throw IllegalArgumentException("Длина обрезки больше длины строки")
    }
    println("Обрезанная строка: ${input.substring(0, length)}")
}

// 10. Поиск элемента в массиве
fun findElementInArray() {
    println("Введите элементы массива через пробел:")
    val input = readLine() ?: throw IllegalArgumentException("Пустой ввод")
    val array = input.split(" ").map { it.toInt() }

    println("Введите элемент для поиска:")
    val element = readLine()?.toIntOrNull() ?: throw NumberFormatException("Некорректный ввод элемента")

    if (!array.contains(element)) {
        throw IllegalArgumentException("Элемент не найден в массиве")
    }
    println("Элемент найден в массиве")
}

// 11. Конвертация в двоичную систему
fun convertToBinary() {
    println("Введите целое число для конвертации в двоичную систему:")
    val number = readLine()?.toIntOrNull() ?: throw NumberFormatException("Некорректный ввод числа")

    if (number < 0) {
        throw IllegalArgumentException("Число не может быть отрицательным")
    }
    println("Двоичное представление: ${Integer.toBinaryString(number)}")
}

// 12. Проверка делимости
fun checkDivisibility() {
    println("Введите два числа для проверки делимости:")
    val a = readLine()?.toIntOrNull() ?: throw NumberFormatException("Некорректный ввод числа")
    val b = readLine()?.toIntOrNull() ?: throw NumberFormatException("Некорректный ввод числа")

    if (b == 0) {
        throw ArithmeticException("Делитель не может быть нулем")
    }
    if (a % b == 0) {
        println("$a делится на $b")
    } else {
        println("$a не делится на $b")
    }
}

// 13. Чтение элемента списка
fun getListElement() {
    println("Введите элементы списка через пробел:")
    val input = readLine() ?: throw IllegalArgumentException("Пустой ввод")
    val list = input.split(" ").map { it }

    println("Введите индекс элемента:")
    val index = readLine()?.toIntOrNull() ?: throw NumberFormatException("Некорректный ввод индекса")

    if (index < 0 || index >= list.size) {
        throw IndexOutOfBoundsException("Индекс выходит за пределы списка")
    }
    println("Элемент с индексом $index: ${list[index]}")
}

// 14. Парольная проверка
fun checkPasswordStrength() {
    println("Введите пароль:")
    val password = readLine() ?: throw IllegalArgumentException("Пустой ввод")

    if (password.length < 8) {
        throw WeakPasswordException("Пароль должен содержать не менее 8 символов")
    }
    println("Пароль корректен")
}

// 15. Проверка даты
fun validateDate() {
    println("Введите дату в формате dd.MM.yyyy:")
    val input = readLine() ?: throw IllegalArgumentException("Пустой ввод")
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    try {
        val date = java.time.LocalDate.parse(input, formatter)
        println("Дата корректна: $date")
    } catch (e: DateTimeParseException) {
        throw DateTimeParseException("Некорректный формат даты", input, 0)
    }
}

// 16. Конкатенация строк
fun concatenateStrings() {
    println("Введите две строки:")
    val str1 = readLine() ?: throw NullPointerException("Первая строка равна null")
    val str2 = readLine() ?: throw NullPointerException("Вторая строка равна null")

    println("Результат конкатенации: $str1$str2")
}

// 17. Остаток от деления
fun findRemainder() {
    println("Введите два числа для нахождения остатка от деления:")
    val a = readLine()?.toIntOrNull() ?: throw NumberFormatException("Некорректный ввод числа")
    val b = readLine()?.toIntOrNull() ?: throw NumberFormatException("Некорректный ввод числа")

    if (b == 0) {
        throw ArithmeticException("Делитель не может быть нулем")
    }
    println("Остаток от деления: ${a % b}")
}

// 18. Квадратный корень
fun calculateSquareRoot() {
    println("Введите число для нахождения квадратного корня:")
    val number = readLine()?.toDoubleOrNull() ?: throw NumberFormatException("Некорректный ввод числа")

    if (number < 0) {
        throw IllegalArgumentException("Число не может быть отрицательным")
    }
    println("Квадратный корень: ${Math.sqrt(number)}")
}

// 19. Конвертер температуры
fun convertTemperature() {
    println("Введите температуру в Цельсиях:")
    val celsius = readLine()?.toDoubleOrNull() ?: throw NumberFormatException("Некорректный ввод температуры")

    if (celsius < -273.15) {
        throw IllegalArgumentException("Температура не может быть ниже абсолютного нуля (-273.15°C)")
    }
    val fahrenheit = celsius * 9 / 5 + 32
    println("Температура в Фаренгейтах: $fahrenheit")
}

// 20. Проверка строки на пустоту
fun checkStringEmpty() {
    println("Введите строку:")
    val input = readLine()

    if (input.isNullOrEmpty()) {
        throw IllegalArgumentException("Строка пустая или равна null")
    }
    println("Строка не пустая: $input")
}