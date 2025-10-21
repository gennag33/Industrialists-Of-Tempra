package com.example.model.config.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.model.config.PortConfig;

public final class PortRegistry extends IdentifiableConfigRegistry<PortConfig> {
    private static final PortRegistry INSTANCE = new PortRegistry();
    private static final String PATH = "/config/ports.json";

    private PortRegistry() {}

    public static PortRegistry getInstance() {
        return INSTANCE;
    }

    public void load(ObjectMapper mapper) {
        super.load(mapper, PATH, new TypeReference<>() {});
    }
}