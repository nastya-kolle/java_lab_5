package com.example;

/**
 * Пример "бин"-класса, требующего внедрения зависимостей.
 * 
 * <p>Содержит два поля, помеченных аннотацией {@link AutoInjectable}:
 * <ul>
 *   <li>{@link #field1} типа {@link SomeInterface}</li>
 *   <li>{@link #field2} типа {@link SomeOtherInterface}</li>
 * </ul>
 * </p>
 * 
 * <p>Без вызова {@link Injector#inject(Object)} поля остаются {@code null},
 * и вызов метода {@link #foo()} приведёт к {@link NullPointerException}.</p>
 * 
 * @see Injector
 */
public class SomeBean{

    /** Зависимость по интерфейсу {@link SomeInterface}. */
    @AutoInjectable
    public SomeInterface field1;

    /** Зависимость по интерфейсу {@link SomeOtherInterface}. */
    @AutoInjectable
    public SomeOtherInterface field2;

     /**
     * Вызывает методы у внедрённых зависимостей.
     * 
     * <p>Вывод зависит от того, какие реализации указаны в {@code config.properties}:
     * <ul>
     *   <li>{@code di.SomeInterface=di.SomeImpl} → {@code A}</li>
     *   <li>{@code di.SomeInterface=di.OtherImpl} → {@code B}</li>
     *   <li>{@code di.SomeOtherInterface=di.SODoer} → {@code C}</li>
     * </ul>
     * Итог: {@code AC} или {@code BC}.</p>
     * 
     * @throws NullPointerException если зависимости не внедрены
     */
    void foo(){
        field1.doSomething();
        field2.doSomeOther();
    }
}