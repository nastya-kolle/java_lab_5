package com.example;

import  java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;


/**
 * Класс-инжектор, реализующий простейший механизм внедрения зависимостей (DI)
 * на основе аннотации {@link AutoInjectable} и конфигурационного файла.
 * 
 * <p>Инициализируется один раз и загружает маппинг интерфейс → реализация из
 * файла {@code config.properties} в classpath.</p>
 * 
 * <p>Поддерживает только:
 * <ul>
 *   <li>поля (не конструкторы/сеттеры)</li>
 *   <li>публичные конструкторы без параметров</li>
 *   <li>рефлексивное внедрение (через {@link Field#set(Object, Object)})</li>
 * </ul>
 * </p>
 * 
 * <h3>Пример использования:</h3>
 * <pre>{@code
 * SomeBean bean = new Injector().inject(new SomeBean());
 * bean.foo(); // → AC
 * }</pre>
 * 
 * @see AutoInjectable
 */
public class Injector{

    /** Конфигурация: отображение полного имени интерфейса → полного имени реализации. */
    private final Properties config;

    /**
     * Конструктор.
     * 
     * <p>Загружает {@code config.properties} из корня classpath.
     * Файл должен содержать пары вида:
     * <pre>
     * di.SomeInterface=di.SomeImpl
     * di.SomeOtherInterface=di.SODoer
     * </pre>
     * </p>
     * 
     * @throws RuntimeException если файл не найден или не удалось прочитать
     */
    public Injector(){
        config = new Properties();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")){
            if (is == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }
            config.load(is);
        }catch (IOException e){
            throw new RuntimeException("Failed to load config.properties ", e);
        }
    }

    /**
     * Конструктор для тестирования: принимает маппинг напрямую.
     * 
     * @param config маппинг интерфейс → реализация
     */
    Injector(Properties config){
        this.config = config;
    }

    /**
     * Внедряет зависимости в переданный объект.
     * 
     * <p>Для всех полей объекта:
     * <ol>
     *   <li>проверяет наличие аннотации {@link AutoInjectable}</li>
     *   <li>находит реализацию по типу поля в {@code config.properties}</li>
     *   <li>создаёт экземпляр через конструктор по умолчанию</li>
     *   <li>присваивает значение полю (даже если оно {@code private})</li>
     * </ol>
     * </p>
     * 
     * @param <T> тип объекта
     * @param instance объект, в который нужно внедрить зависимости (не null)
     * @return тот же объект {@code instance} после внедрения
     * @throws RuntimeException при ошибках:
     *         <ul>
     *           <li>реализация не указана в конфиге</li>
     *           <li>класс реализации не найден</li>
     *           <li>реализация несовместима с типом поля</li>
     *           <li>не удалось создать экземпляр</li>
     *         </ul>
     * @throws NullPointerException если {@code instance == null}
     */
    @SuppressWarnings("unchecked")
    public<T> T inject(T instance){
        if (instance == null) {
        throw new NullPointerException("instance must not be null");
    }

        Class<?> clazz = instance.getClass();
        for (Field field : clazz.getDeclaredFields()){
            if (field.isAnnotationPresent(AutoInjectable.class)){
                Class<?> fieldType = field.getType();
                String interfaceName = fieldType.getName();

                String implClassName = config.getProperty(interfaceName);
                if (implClassName == null){
                    throw new RuntimeException("No implementation found for interface: " + interfaceName);
                }

                try {
                    Class<?> implClass = Class.forName(implClassName);

                    if (!fieldType.isAssignableFrom(implClass)){
                        throw new RuntimeException(implClass.getName() + " does not implement " + interfaceName);
                    }

                    Object implInstance = implClass.getDeclaredConstructor().newInstance();
                    field.setAccessible(true);
                    field.set(instance, implInstance);
                    
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException("Failed to instantiate or inject into field: " + field.getName(), e);
                }
            }
        }
        return instance;
    }

}