package com.example.model.config.registry;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.model.config.IdentifiableConfig;

import java.io.InputStream;
import java.util.*;

public abstract class IdentifiableConfigRegistry<T extends IdentifiableConfig> {
    private final Map<String, T> entries = new HashMap<>();

    public void load(ObjectMapper mapper, String jsonPath, TypeReference<List<T>> typeRef) {
        if (isLoaded()) {
            throw new IllegalStateException("Config already loaded");
        }
        try (InputStream is = getClass().getResourceAsStream(jsonPath)) {
            if (is == null) {
                throw new IllegalArgumentException("Resource not found: " + jsonPath);
            }

            List<T> list = mapper.readValue(is, typeRef);
            for (T item : list) {
                if (entries.containsKey(item.getId())) {
                    throw new IllegalStateException("Duplicate config ID: " + item.getId());
                }
                entries.put(item.getId(), item);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config from " + jsonPath, e);
        }
    }

    public T get(String id) {
        return entries.get(id);
    }

    public Collection<T> all() {
        return Collections.unmodifiableCollection(entries.values());
    }

    public boolean isLoaded() {
        return !entries.isEmpty();
    }

    public int size() {
        return entries.size();
    }

    public void unload() {
        entries.clear();
    }
}
