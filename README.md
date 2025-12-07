# Reflection-Based Dependency Injection

Простой механизм внедрения зависимостей, реализованный с помощью **рефлексии**, **аннотаций** и файла конфигурации `properties`.  
Позволяет автоматически инициализировать поля объектов, помеченные `@AutoInjectable`, на основе маппинга *интерфейс → реализация* из `config.properties`.

---

## Компоненты

| Компонент | Описание |
|----------|----------|
| **`@AutoInjectable`** | Аннотация для пометки полей, подлежащих внедрению |
| **`Injector`** | Класс с методом `inject(T obj)`, выполняющий DI через рефлексию |
| **`config.properties`** | Файл настроек: `интерфейс = реализация` |
| **Интерфейсы и реализации** | Примеры: `SomeInterface` → `SomeImpl` / `OtherImpl`; `SomeOtherInterface` → `SoDoer` |
| **`SomeBean`** | Класс-пример с аннотированными полями и методом `foo()` |

---

## Примеры конфигурации и поведения

| `config.properties` | Результат вызова `sb.foo()` |
|---------------------|----------------------------|
| `com.example.SomeInterface=com.example.SomeImpl`<br>`com.example.SomeOtherInterface=com.example.SoDoer` | `A`<br>`C` |
| `com.example.SomeInterface=com.example.OtherImpl`<br>`com.example.SomeOtherInterface=com.example.SoDoer` | `B`<br>`C` |

---

## Использование

```java
SomeBean sb = new Injector().inject(new SomeBean());
sb.foo();  // → AC или BC (в зависимости от config.properties)
```

Без вызова `inject()` — `NullPointerException`.

---

## Тестирование

| Тест | Проверяемое поведение |
|------|------------------------|
| **Успешное внедрение (`AC`, `BC`)** | Поля инициализируются корректными реализациями |
| **Пропуск неаннотированных полей** | Поля без `@AutoInjectable` остаются `null` |
| **Отсутствие маппинга в config** | Выбрасывается `RuntimeException` |
| **Несуществующий класс реализации** | Выбрасывается `RuntimeException` (обёртка над `ClassNotFoundException`) |
| **Несовместимость типов** | Выбрасывается `RuntimeException` (реализация не implements интерфейс) |
| **`null`-инстанс** | Выбрасывается `NullPointerException` |

---

## Структура проекта

```
src/
├── main/java/com/example/
│   ├── AutoInjectable.java
│   ├── SomeInterface.java
│   ├── SomeOtherInterface.java
│   ├── SomeImpl.java      // → "A"
│   ├── OtherImpl.java     // → "B"
│   ├── SoDoer.java        // → "C"
│   ├── SomeBean.java
│   ├── Injector.java
│   └── Main.java
├── main/resources/
│   └── config.properties
└── test/java/com/example/
    └── InjectorTest.java
```

---

## Сборка и запуск

```bash
mvn compile        # компиляция
mvn test           # запуск тестов
mvn exec:java      # запуск Main
mvn package        # сборка JAR
java -jar target/*.jar
```

Требуется **Java 17+**, **Maven**, **JUnit 5**.
