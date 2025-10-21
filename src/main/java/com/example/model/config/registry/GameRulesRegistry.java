package com.example.model.config.registry;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;

import com.example.model.config.GameRulesConfig;


public final class GameRulesRegistry {

    private static GameRulesConfig instance;

    private GameRulesRegistry() {} // prevent instantiation

    /** Load the rules from JSON; call this once at startup. */
    public static void load(ObjectMapper mapper, String jsonPath) {
        if (instance != null) throw new IllegalStateException("Game rules already loaded");

        try (InputStream is = GameRulesRegistry.class.getResourceAsStream(jsonPath)) {
            if (is == null) throw new IllegalArgumentException("JSON file not found: " + jsonPath);
            instance = mapper.readValue(is, GameRulesConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load game rules", e);
        }
    }

    /** Access the loaded rules anywhere in your game */
    public static GameRulesConfig get() {
        if (instance == null) throw new IllegalStateException("Game rules not loaded yet");
        return instance;
    }

    public static boolean isLoaded() {
        return instance != null;
    }

    public static void unload() {
        instance = null;
    }
}