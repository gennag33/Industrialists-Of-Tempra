package com.example.model.config.service;

import com.example.model.config.*;
import com.example.model.config.registry.*;

import java.util.Collection;

public class ConfigService {

    // -------------------------
    // Game Rules
    // -------------------------
    public static GameRulesConfig getGameRules() {
        return GameRulesRegistry.get();
    }

    public static int getVictoryPointsToWin() {
        return GameRulesRegistry.get().victoryPointsToWin;
    }

    public static int getRobberCardLimit() {
        return GameRulesRegistry.get().robberCardLimit;
    }

    // -------------------------
    // Resource Config
    // -------------------------
    public static ResourceConfig getResource(String id) {
        return ResourceRegistry.getInstance().get(id);
    }

    public static Collection<ResourceConfig> getAllResources() {
        return ResourceRegistry.getInstance().all();
    }

    // -------------------------
    // Dev Card Config
    // -------------------------
    public static DevCardConfig getDevCard(String id) {
        return DevCardRegistry.getInstance().get(id);
    }

    public static Collection<DevCardConfig> getAllDevCards() {
        return DevCardRegistry.getInstance().all();
    }

    // -------------------------
    // Port Config
    // -------------------------
    public static PortConfig getPort(String id) {
        return PortRegistry.getInstance().get(id);
    }

    public static Collection<PortConfig> getAllPorts() {
        return PortRegistry.getInstance().all();
    }

    // -------------------------
    // Player Infrastructure Config
    // -------------------------
    public static PlayerInfrastructureConfig getInfrastructure(String id) {
        return PlayerInfrastructureRegistry.getInstance().get(id);
    }

    public static Collection<PlayerInfrastructureConfig> getAllInfrastructure() {
        return PlayerInfrastructureRegistry.getInstance().all();
    }

    // -------------------------
    //  Tile Config
    // -------------------------
    public static TileConfig getTile(String id) {
        return TileRegistry.getInstance().get(id);
    }

    public static Collection<TileConfig> getAllTiles() {
        return TileRegistry.getInstance().all();
    }

    // -------------------------
    // Language Functions for Config Items
    // -------------------------
    public static String getDisplayName(String id) {
        if (id == null || id.equals("")) {
            return "NONE";
        }
        return LangManager.get(id + ".name");
    }

    public static String getDevCardDescription(String id) {
        if (id == null || id.equals("")) {
            return "NONE";
        }
        return LangManager.get(id + ".description");
    }
}
