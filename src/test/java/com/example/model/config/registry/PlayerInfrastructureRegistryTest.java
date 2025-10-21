package com.example.model.config.registry;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

class PlayerInfrastructureRegistryTest {

    private static PlayerInfrastructureRegistry registry;
    private static ObjectMapper mapper;

    @BeforeAll
    static void setUp() {
        mapper = new ObjectMapper();
        registry= PlayerInfrastructureRegistry.getInstance();
    }

    @AfterEach
    void tearDownEach() {
        registry.unload();
    }

    @Test
    void testGetInstanceReturnsSingleton() {
        registry.load(mapper);
        PlayerInfrastructureRegistry instance1 = PlayerInfrastructureRegistry.getInstance();
        PlayerInfrastructureRegistry instance2 = PlayerInfrastructureRegistry.getInstance();
        assertSame(instance1, instance2, "getInstance should return the same instance");
    }

    @Test
    void testLoadDoesNotThrowException() {
        assertDoesNotThrow(() -> registry.load(mapper), "load should not throw an exception");
    }
}