package com.example.model.config.registry;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;


class DevCardRegistryTest {

    private static DevCardRegistry registry;
    private static ObjectMapper mapper;

    @BeforeAll
    static void setUp() {
        mapper = new ObjectMapper();
        registry= DevCardRegistry.getInstance();
    }

    @AfterEach
    void tearDownEach() {
        registry.unload();
    }

    @Test
    void testGetInstanceReturnsSingleton() {
        registry.load(mapper);
        DevCardRegistry instance1 = DevCardRegistry.getInstance();
        DevCardRegistry instance2 = DevCardRegistry.getInstance();
        assertSame(instance1, instance2, "getInstance should return the same singleton instance");
    }

    @Test
    void testLoadDoesNotThrowException() {
        assertDoesNotThrow(() -> registry.load(mapper), "load should not throw an exception");
    }
}