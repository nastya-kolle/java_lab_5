package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация, указывающая, что поле должно быть автоматически проинициализировано
 * через механизм внедрения зависимостей.
 * 
 * <p>Применяется к полям классов. При обработке {@link Injector#inject(Object)}
 * такие поля будут заполнены экземплярами реализаций, указанных в конфигурационном
 * файле {@code config.properties}.</p>
 * 
 * <p>Пример использования:</p>
 * <pre>{@code
 * public class SomeBean {
 *     @AutoInjectable
 *     private SomeInterface service;
 * }
 * }</pre>
 * 
 * @see Injector
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoInjectable{
    
}
