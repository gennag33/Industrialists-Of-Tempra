package com.example.model.config;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerInfrastructureConfig implements IdentifiableConfig {
    public final String id;
    public final String texturePath;

    public final HashMap<String, Integer> constructionCosts; // resourceID -> quantity
    public final int maxQuantity;
    public final int victoryPoints;

    @JsonCreator
    public PlayerInfrastructureConfig(
            @JsonProperty("id") String id,
            @JsonProperty("texturePath") String texturePath,
            @JsonProperty("constructionCosts") Map<String, Integer> constructionCosts,
            @JsonProperty("maxQuantity") int maxQuantity,
            @JsonProperty("victoryPoints") int victoryPoints) {
        this.id = id;
        this.texturePath = texturePath;
        this.constructionCosts = new HashMap<>(constructionCosts);
        this.maxQuantity = maxQuantity;
        this.victoryPoints = victoryPoints;
    }

    @Override
    public String getId() {
        return this.id; 
    }
}
