package com.example.model.config.registry;

import com.example.model.config.GameRulesConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameRulesRegistryTest {

    private static ObjectMapper mapper;

    @BeforeAll
    public static void setUp() {
        GameRulesRegistry.unload();
        mapper = new ObjectMapper();
    }

    @AfterEach
    public void tearDownEach() throws Exception {
        GameRulesRegistry.unload();
    }

    @Test
    public void testLoadValidJson() throws Exception {
        String jsonPath = "/test-gamerules.json";
        // Place a valid test-gamerules.json in src/test/resources
        GameRulesRegistry.load(mapper, jsonPath);
        GameRulesConfig config = GameRulesRegistry.get();
        assertNotNull(config);
    }

    @Test
    public void testLoadTwiceThrows() throws Exception {
        String jsonPath = "/test-gamerules.json";
        GameRulesRegistry.load(mapper, jsonPath);
        // Second load should throw
        assertThrows(IllegalStateException.class, () -> {
            GameRulesRegistry.load(mapper, jsonPath);
        });
    }

    @Test
    public void testLoadResourceNotFound() throws Exception {
        String jsonPath = "/nonexistent.json";
        assertThrows(RuntimeException.class, () -> {
            GameRulesRegistry.load(mapper, jsonPath);
        });
    }

    @Test
    public void testLoadMalformedJson() throws Exception {
        String jsonPath = "/invalid-gamerules.json";
        // Place an invalid-gamerules.json in src/test/resources
        assertThrows(RuntimeException.class, () -> {
            GameRulesRegistry.load(mapper, jsonPath);
        });
    }

    @Test
    public void testGetBeforeLoadThrows() {
        assertThrows(IllegalStateException.class, () -> {
            GameRulesRegistry.get();
        });
    }

    @Test
    public void testIsLoaded() throws Exception {
        assertFalse(GameRulesRegistry.isLoaded());
        String jsonPath = "/test-gamerules.json";
        GameRulesRegistry.load(mapper, jsonPath);
        assertTrue(GameRulesRegistry.isLoaded());
    }

    @Test
    public void testUnload() throws Exception {
        String jsonPath = "/test-gamerules.json";
        GameRulesRegistry.load(mapper, jsonPath);
        assertTrue(GameRulesRegistry.isLoaded());
        GameRulesRegistry.unload();
        assertFalse(GameRulesRegistry.isLoaded());
    }

    @Test
    public void testGetReturnsCorrectConfig() throws Exception {
        String jsonPath = "/test-gamerules.json";
        GameRulesRegistry.load(mapper, jsonPath);
        GameRulesConfig config = GameRulesRegistry.get();
        // Assuming test-gamerules.json has a known value for a field
        assertEquals(10, config.victoryPointsToWin);
    }
}