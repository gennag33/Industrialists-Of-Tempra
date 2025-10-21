package com.example.model.config.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.model.config.ResourceConfig;

public final class ResourceRegistry extends IdentifiableConfigRegistry<ResourceConfig> {
    private static final ResourceRegistry INSTANCE = new ResourceRegistry();
    private static final String PATH = "/config/resources.json";

    private ResourceRegistry() {}

    public static ResourceRegistry getInstance() {
        return INSTANCE;
    }

    public void load(ObjectMapper mapper) {
        super.load(mapper, PATH, new TypeReference<>() {});
    }
}