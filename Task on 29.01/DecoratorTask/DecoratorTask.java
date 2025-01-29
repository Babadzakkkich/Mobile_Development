// Интерфейс для текстового процессора
interface TextProcessor {
    String process(String text);
}

// Базовый класс текстового процессора
class SimpleTextProcessor implements TextProcessor {
    @Override
    public String process(String text) {
        return text;
    }
}

// Декоратор для преобразования текста в верхний регистр
class UpperCaseDecorator implements TextProcessor {
    private final TextProcessor wrapped;

    public UpperCaseDecorator(TextProcessor wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String process(String text) {
        return wrapped.process(text).toUpperCase();
    }
}

// Декоратор для удаления пробелов в начале и конце строки
class TrimDecorator implements TextProcessor {
    private final TextProcessor wrapped;

    public TrimDecorator(TextProcessor wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String process(String text) {
        return wrapped.process(text).trim();
    }
}

// Декоратор для замены всех пробелов символом подчеркивания (_)
class ReplaceDecorator implements TextProcessor {
    private final TextProcessor wrapped;

    public ReplaceDecorator(TextProcessor wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String process(String text) {
        return wrapped.process(text).replace(' ', '_');
    }
}

// Пример использования декораторов
public class DecoratorTask {
    public static void main(String[] args) {
        TextProcessor processor = new ReplaceDecorator(
            new UpperCaseDecorator(
                new TrimDecorator(new SimpleTextProcessor())
            )
        );

        String result = processor.process(" Hello world ");
        System.out.println(result); // Вывод: HELLO_WORLD
    }
}