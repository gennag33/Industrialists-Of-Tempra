package com.example.model.config.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.model.config.DisasterCardConfig;

public final class DisasterCardRegistry extends IdentifiableConfigRegistry<DisasterCardConfig> {
    private static final String PATH = "/config/disaster_cards.json";
    private static final DisasterCardRegistry INSTANCE = new DisasterCardRegistry();

    private DisasterCardRegistry() {}

    public static DisasterCardRegistry getInstance() {
        return INSTANCE;
    }

    public void load(ObjectMapper mapper) {
        super.load(mapper, PATH, new TypeReference<>() {});
    }
}