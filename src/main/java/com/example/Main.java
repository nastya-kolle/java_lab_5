package com.example;

/**
 * Точка входа в демонстрационное приложение.
 * 
 * <p>Создаёт экземпляр {@link SomeBean}, внедряет зависимости через {@link Injector},
 * и вызывает метод {@link SomeBean#foo()}, который выводит результат.</p>
 * 
 * <p>Ожидаемый вывод: {@code AC} или {@code BC}, в зависимости от содержимого
 * {@code config.properties}.</p>
 */
public class Main {
    /**
     * Запускает демо.
     * 
     * @param args игнорируются
     */
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SomeBean bean = new Injector().inject(new SomeBean());
        bean.foo();
    }
}