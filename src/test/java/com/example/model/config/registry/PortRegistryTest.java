package com.example.model.config.registry;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class PortRegistryTest {

    private static PortRegistry registry;
    private static ObjectMapper mapper;

    @BeforeAll
    static void setUp() {
        mapper = new ObjectMapper();
        registry= PortRegistry.getInstance();
    }

    @AfterEach
    void tearDownEach() {
        registry.unload();
    }

    @Test
    void testGetInstanceReturnsSingleton() {
        registry.load(mapper);
        PortRegistry instance1 = PortRegistry.getInstance();
        PortRegistry instance2 = PortRegistry.getInstance();
        assertSame(instance1, instance2, "getInstance should return the same instance");
    }

    @Test
    void testLoadDoesNotThrowException() {
        assertDoesNotThrow(() -> registry.load(mapper), "load should not throw an exception");
    }
}