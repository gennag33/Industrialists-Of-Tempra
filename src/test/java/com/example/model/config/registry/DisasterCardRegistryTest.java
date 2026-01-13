package com.example.model.config.registry;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Modifier;

import java.lang.reflect.Constructor;

class DisasterCardRegistryTest {

    private DisasterCardRegistry registry;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        registry = DisasterCardRegistry.getInstance();
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    void tearDownEach() {
        registry.unload();
    }

    @Test
    void testGetInstance_ReturnsSameInstance() {
        DisasterCardRegistry instance1 = DisasterCardRegistry.getInstance();
        DisasterCardRegistry instance2 = DisasterCardRegistry.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    void testGetInstance_ReturnsNonNull() {
        DisasterCardRegistry instance = DisasterCardRegistry.getInstance();

        assertNotNull(instance);
    }

    @Test
    void testLoad_WithValidMapper() {
        assertDoesNotThrow(() -> registry.load(objectMapper));
    }

    @Test
    void testLoad_WithNullMapper_ThrowsException() {
        assertThrows(Exception.class, () -> registry.load(null));
    }

    @Test
    void testSingletonPattern_PrivateConstructor() throws Exception {
        Constructor<DisasterCardRegistry> constructor = DisasterCardRegistry.class.getDeclaredConstructor();

        assertTrue(Modifier.isPrivate(constructor.getModifiers()),
                "Constructor should be private");
    }
}
