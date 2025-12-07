package com.example;

/**
 * Второй пример интерфейса для демонстрации DI.
 * 
 * <p>Содержит метод {@link #doSomeOther()}, который должен быть реализован классом
 * {@link SODoer}. Используется в поле {@link SomeBean#field2}.</p>
 * 
 */
public interface SomeOtherInterface{
    /**
     * Выполняет другое действие.
     * 
     * <p>В текущей реализации ({@link SODoer}) выводит {@code "C"}.</p>
     */
    void doSomeOther();
}