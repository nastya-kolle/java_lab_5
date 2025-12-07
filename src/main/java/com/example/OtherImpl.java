package com.example;

/**
 * Альтернативная реализация интерфейса {@link SomeInterface}.
 * 
 * <p>Может быть указана в {@code config.properties} вместо {@link SomeImpl}
 * для изменения поведения зависимостей без изменения кода.</p>
 * 
 * <p>Метод {@link #doSomething()} выводит строку {@code "B"}.</p>
 */
public class OtherImpl implements SomeInterface{
     /**
     * {@inheritDoc}
     * 
     * <p>Выводит {@code "B"} через {@link System#out}.</p>
     */
    @Override
    public void doSomething(){
        System.out.println("B");
    };
}