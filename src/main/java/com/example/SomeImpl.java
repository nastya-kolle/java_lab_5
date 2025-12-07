package com.example;

/**
 * Конкретная реализация интерфейса {@link SomeInterface}.
 * 
 * <p>Используется в качестве зависимости по умолчанию в {@code config.properties}
 * для интерфейса {@code di.SomeInterface}.</p>
 * 
 * <p>Метод {@link #doSomething()} выводит строку {@code "A"} в стандартный вывод.</p>
 * 
 * @see OtherImpl
 */
public class SomeImpl implements SomeInterface{

    /**
     * {@inheritDoc}
     * 
     * <p>Выводит {@code "A"} через {@link System#out}.</p>
     */
    @Override
    public void doSomething(){
        System.out.println("A");
    };
}