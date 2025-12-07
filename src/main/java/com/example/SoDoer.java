package com.example;


/**
 * Реализация интерфейса {@link SomeOtherInterface}.
 * 
 * <p>Используется для внедрения в поле {@link SomeBean#field2} при обработке
 * аннотации {@link AutoInjectable}.</p>
 * 
 * <p>Метод {@link #doSomeOther()} выводит {@code "C"}.</p>
 */
public class SoDoer implements SomeOtherInterface{
    
    /**
     * {@inheritDoc}
     * 
     * <p>Выводит {@code "C"} через {@link System#out}.</p>
     */
    @Override
    public void doSomeOther(){
        System.out.println("C");
    };
}