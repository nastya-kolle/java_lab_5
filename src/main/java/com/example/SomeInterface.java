package com.example;

/**
 * Пример интерфейса, используемого в демонстрации механизма DI.
 * 
 * <p>Предоставляет один метод {@link #doSomething()}, который должен быть реализован
 * в классах-наследниках (например, {@link SomeImpl}, {@link OtherImpl}).</p>
 * 
 * <p>Используется в связке с {@link AutoInjectable} и {@link Injector}
 * для демонстрации внедрения зависимости по интерфейсу.</p>
 */
public interface SomeInterface{
    /**
     * Выполняет некоторое действие.
     * 
     * <p>Конкретное поведение определяется реализацией интерфейса.</p>
     * 
     * <p>В примерах:
     * <ul>
     *   <li>{@link SomeImpl} выводит {@code "A"}</li>
     *   <li>{@link OtherImpl} выводит {@code "B"}</li>
     * </ul>
     * </p>
     */
    void doSomething();
}
