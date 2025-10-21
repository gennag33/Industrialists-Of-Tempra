package com.example.model.config.registry;


import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TileRegistryTest {

    private static TileRegistry registry;
    private static ObjectMapper mapper;

    @BeforeAll
    static void setUp() {
        mapper = new ObjectMapper();
        registry = TileRegistry.getInstance();
    }

    @AfterEach
    void tearDown() {
        registry.unload();
    }

    @Test
    void testGetInstanceReturnsSingleton() {
        registry.load(mapper);
        TileRegistry instance1 = TileRegistry.getInstance();
        TileRegistry instance2 = TileRegistry.getInstance();
        assertSame(instance1, instance2);
    }

    @Test
    void testLoadDoesNotThrowException() {
        assertDoesNotThrow(() -> registry.load(mapper), "load should not throw an exception");
    }
}