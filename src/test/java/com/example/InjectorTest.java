package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class InjectorTest{
    private Properties config;
    private Injector injector;

    @BeforeEach
    void setUp(){
        config = new Properties();
        config.setProperty("com.example.SomeInterface", "com.example.SomeImpl");
        config.setProperty("com.example.SomeOtherInterface", "com.example.SoDoer");
        injector = new Injector(config);
    }

    @Test
    @DisplayName("inject() should initialize fields and produce 'AC' output")
    void testInject_Success_AC(){
        SomeBean bean = new SomeBean();
        SomeBean injected = injector.inject(bean);

        assertNotNull(injected.field1, "field1 must be injected");
        assertNotNull(injected.field2, "field2 must be injected");
        assertInstanceOf(SomeImpl.class, injected.field1);
        assertInstanceOf(SoDoer.class, injected.field2);

        assertDoesNotThrow(()-> injected.foo());
    }

    @Test
    @DisplayName("inject() should support switching implementation to produce 'BC'")
    void testInject_Success_BC(){
        config.setProperty("com.example.SomeInterface", "com.example.OtherImpl");
        Injector bcInjector = new Injector(config);
        SomeBean bean = new SomeBean();

        SomeBean injected = bcInjector.inject(bean);
        assertInstanceOf(OtherImpl.class, injected.field1);
        assertInstanceOf(SoDoer.class, injected.field2);
        assertDoesNotThrow(()-> injected.foo());
    }

   @Test
   @DisplayName("inject() shpuld leave non-annotated fields unchanged (null)")
   void testInject_IgnoresNonAnnotatedFields(){
    class TestBean{
        @AutoInjectable
        SomeInterface annotatedField;
        SomeInterface nonAnnotatedField;
    }
    TestBean bean = new TestBean();
    Injector testInjector = new Injector(config);

    testInjector.inject(bean);

    assertNotNull(bean.annotatedField, "annotatedField must be injected");
    assertNull(bean.nonAnnotatedField, "nonAnnotatedField must remain null");
   }

   @Test
   @DisplayName("inject() throws RuntimeException when implementation is missing is config")
   void testInject_ThrowsWhenImplementationNonConfigered(){
    Properties badConfig = new Properties();
    badConfig.setProperty("com.example.SomeOtherInterface", "com.example.SoDoer");
    Injector badInjector = new Injector(badConfig);
    SomeBean bean  = new SomeBean();
    RuntimeException ex = assertThrows(RuntimeException.class, () -> badInjector.inject(bean), "Expected RuntimeException when implementation is missing");
    assertTrue(ex.getMessage().contains("No implementation found for interface: com.example.SomeInterface"), "Message should indicate missing interface mapping");
   }

   @Test
   @DisplayName("inject() throws RuntimeException when implementation class not found")
   void testInject_ThrowsWhenClassNotFound(){
        config.setProperty("com.example.SomeInterface", "com.example.NonExistentClass");
        Injector badInjector = new Injector(config);
        SomeBean bean  = new SomeBean();

        RuntimeException ex = assertThrows(RuntimeException.class, () -> badInjector.inject(bean));
        assertTrue(ex.getMessage().contains("Failed to instantiate or inject into field: field1"), "Should wrap ClassNotFoundException/NoClassDefFoundError");
        assertTrue(ex.getCause() instanceof ReflectiveOperationException, "Cause should be ReflectiveOperationException (e. g., ClassNotFoundException)");
   }

   @Test
   @DisplayName("inject() throws RuntimeException when implementation does not implement the interface")
   void testInject_ThrowsWhenImplementationIncompatible(){
        config.setProperty("com.example.SomeOtherInterface", "com.example.SomeImpl");
        Injector badInjector = new Injector(config);
        SomeBean bean = new SomeBean();
        RuntimeException ex = assertThrows(RuntimeException.class, ()->badInjector.inject(bean));
        assertTrue(ex.getMessage().contains("does not implement com.example.SomeOtherInterface"), "Should detect type incompatibility");
   }

   @Test
   @DisplayName("inject() throws NullPointerException when instance is null")
   void testInject_NullInstance(){
        NullPointerException ex = assertThrows(NullPointerException.class, ()->injector.inject(null));
        assertEquals("instance must not be null", ex.getMessage());
   }
}