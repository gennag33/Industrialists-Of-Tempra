package com.example.model.config.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.model.config.DevCardConfig;

public final class DevCardRegistry extends IdentifiableConfigRegistry<DevCardConfig> {
    private static final String PATH = "/config/dev_cards.json";
    private static final DevCardRegistry INSTANCE = new DevCardRegistry();

    private DevCardRegistry() {}

    public static DevCardRegistry getInstance() {
        return INSTANCE;
    }

    public void load(ObjectMapper mapper) {
        super.load(mapper, PATH, new TypeReference<>() {});
    }
}