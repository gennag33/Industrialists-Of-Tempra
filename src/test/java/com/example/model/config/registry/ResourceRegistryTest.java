package com.example.model.config.registry;


import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class ResourceRegistryTest {

    private static ResourceRegistry registry;
    private static ObjectMapper mapper;

    @BeforeAll
    static void setUp() {
        mapper = new ObjectMapper();
        registry = ResourceRegistry.getInstance();
    }

    @AfterEach
    void tearDownEach() {
        registry.unload();
    }

    @Test
    void testGetInstanceReturnsSingleton() {
        registry.load(mapper);
        ResourceRegistry instance1 = ResourceRegistry.getInstance();
        ResourceRegistry instance2 = ResourceRegistry.getInstance();
        assertSame(instance1, instance2, "getInstance should return the same singleton instance");
    }

    @Test
    void testLoadDoesNotThrowException() {
        assertDoesNotThrow(() -> registry.load(mapper), "load should not throw an exception");
    }
}