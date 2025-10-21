package com.example.model.config.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.model.config.PlayerInfrastructureConfig;

public final class PlayerInfrastructureRegistry extends IdentifiableConfigRegistry<PlayerInfrastructureConfig> {
    private static final PlayerInfrastructureRegistry INSTANCE = new PlayerInfrastructureRegistry();
    private static final String PATH = "/config/player_infrastructure.json";

    private PlayerInfrastructureRegistry() {}

    public static PlayerInfrastructureRegistry getInstance() {
        return INSTANCE;
    }

    public void load(ObjectMapper mapper) {
        super.load(mapper, PATH, new TypeReference<>() {});
    }
}