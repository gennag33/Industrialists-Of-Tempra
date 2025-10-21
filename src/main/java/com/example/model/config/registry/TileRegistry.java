package com.example.model.config.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.model.config.TileConfig;

public final class TileRegistry extends IdentifiableConfigRegistry<TileConfig> {
    private static final TileRegistry INSTANCE = new TileRegistry();
    private static final String PATH = "/config/tiles.json";

    private TileRegistry() {}

    public static TileRegistry getInstance() {
        return INSTANCE;
    }

    public void load(ObjectMapper mapper) {
        super.load(mapper, PATH, new TypeReference<>() {});
    }
}