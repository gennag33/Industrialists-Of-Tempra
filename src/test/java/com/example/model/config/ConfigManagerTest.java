package com.example.model.config;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



public class ConfigManagerTest {

    @BeforeAll
    public static void setup() {
        try {
            ConfigManager.unloadAll();
        } catch (Exception e) {
            fail("ConfigManager.unloadAll() threw an exception during setup: " + e);
        }
    }
    @AfterEach
    public void tearDown() {
        try {
            ConfigManager.unloadAll();
        } catch (Exception ignored) {
            fail("ConfigManager.unloadAll() threw an exception during teardown: " + ignored);
        }
    }

    @Test
    public void loadAll_doesNotThrow() {
        try {
            ConfigManager.loadAll();
        } catch (Exception e) {
            fail("ConfigManager.loadAll() threw an exception: " + e);
        }
    }

    @Test
    public void loadThenUnload_doesNotThrow() {
        try {
            ConfigManager.loadAll();
            ConfigManager.unloadAll();
        } catch (Exception e) {
            fail("ConfigManager.loadAll()/unloadAll() threw an exception: " + e);
        }
    }
}