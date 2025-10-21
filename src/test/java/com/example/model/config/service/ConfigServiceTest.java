package com.example.model.config.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.example.model.config.ConfigManager;
import com.example.model.config.GameRulesConfig;
import com.example.model.config.registry.GameRulesRegistry;
import com.example.model.config.LangManager;
import com.example.model.config.registry.DevCardRegistry;
import com.example.model.config.registry.PlayerInfrastructureRegistry;
import com.example.model.config.registry.PortRegistry;
import com.example.model.config.registry.ResourceRegistry;
import com.example.model.config.registry.TileRegistry;

public class ConfigServiceTest {

    @BeforeAll
    public static void setup() {
        ConfigManager.loadAll();
    }

    @AfterAll
    public static void teardown() {
        ConfigManager.unloadAll();
    }

    @Test
    public void testGetGameRulesDelegatesToRegistry() {
        GameRulesConfig expected = GameRulesRegistry.get();
        GameRulesConfig actual = ConfigService.getGameRules();
        assertSame(expected, actual,
                "ConfigService.getGameRules should return the exact instance from GameRulesRegistry.get()");
    }

    @Test
    public void testGetVictoryPointsToWinDelegatesToRegistry() {
        int expected = GameRulesRegistry.get().victoryPointsToWin;
        int actual = ConfigService.getVictoryPointsToWin();
        assertEquals(expected, actual,
                "ConfigService.getVictoryPointsToWin should return the value from GameRulesRegistry.get().victoryPointsToWin");
    }

    @Test
    public void testGetRobberCardLimitDelegatesToRegistry() {
        int expected = GameRulesRegistry.get().robberCardLimit;
        int actual = ConfigService.getRobberCardLimit();
        assertEquals(expected, actual,
                "ConfigService.getRobberCardLimit should return the value from GameRulesRegistry.get().robberCardLimit");
    }

    @Test
    public void testGetGameRulesReturnsSingletonInstance() {
        GameRulesConfig firstCall = ConfigService.getGameRules();
        GameRulesConfig secondCall = ConfigService.getGameRules();
        assertSame(firstCall, secondCall,
                "ConfigService.getGameRules should always return the same singleton instance");
    }

    @Test
    public void testGetAllResourcesDelegatesToRegistry() {
        assertEquals(
            Set.copyOf(ResourceRegistry.getInstance().all()),
            Set.copyOf(ConfigService.getAllResources()),
            "ConfigService.getAllResources should return the same collection instance as ResourceRegistry.getInstance().all()");
    }

    @Test
    public void testGetAllDevCardsDelegatesToRegistry() {
        assertEquals(
            Set.copyOf(DevCardRegistry.getInstance().all()),
            Set.copyOf(ConfigService.getAllDevCards()),
            "ConfigService.getAllDevCards should return the same collection instance as DevCardRegistry.getInstance().all()");
    }

    @Test
    public void testGetAllPortsDelegatesToRegistry() {
        assertEquals(
            Set.copyOf(PortRegistry.getInstance().all()),
            Set.copyOf(ConfigService.getAllPorts()),
            "ConfigService.getAllPorts should return the same collection instance as PortRegistry.getInstance().all()");
    }

    @Test
    public void testGetAllInfrastructureDelegatesToRegistry() {
        assertEquals(
            Set.copyOf(PlayerInfrastructureRegistry.getInstance().all()),
            Set.copyOf(ConfigService.getAllInfrastructure()),
            "ConfigService.getAllInfrastructure should return the same collection instance as PlayerInfrastructureRegistry.getInstance().all()");
    }

    @Test
    public void testGetAllTilesDelegatesToRegistry() {
        assertEquals(
            Set.copyOf(TileRegistry.getInstance().all()),
            Set.copyOf(ConfigService.getAllTiles()),
            "ConfigService.getAllTiles should return the same collection instance as TileRegistry.getInstance().all()");
    }

    @Test
    public void testGetDisplayNameNullOrEmptyReturnsNONE() {
        assertEquals("NONE", ConfigService.getDisplayName(null), "Null id should return NONE");
        assertEquals("NONE", ConfigService.getDisplayName(""), "Empty id should return NONE");
    }

    @Test
    public void testGetDisplayNameDelegatesToLangManager() {
        String id = "example.test.display";
        String expected = LangManager.get(id + ".name");
        String actual = ConfigService.getDisplayName(id);
        assertEquals(expected, actual,
                "ConfigService.getDisplayName should delegate to LangManager.get with '.name' suffix");
    }

    @Test
    public void testGetDevCardDescriptionNullOrEmptyReturnsNONE() {
        assertEquals("NONE", ConfigService.getDevCardDescription(null),
                "Null id should return NONE for dev card description");
        assertEquals("NONE", ConfigService.getDevCardDescription(""),
                "Empty id should return NONE for dev card description");
    }

    @Test
    public void testGetDevCardDescriptionDelegatesToLangManager() {
        String id = "example.test.devcard";
        String expected = LangManager.get(id + ".description");
        String actual = ConfigService.getDevCardDescription(id);
        assertEquals(expected, actual,
                "ConfigService.getDevCardDescription should delegate to LangManager.get with '.description' suffix");
    }
}
