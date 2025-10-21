package com.example.model.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PortConfig implements IdentifiableConfig {
    public final String id;
    public final String texturePath;

    public final String resourceID; // -1 for any
    public final int giveQuantity;
    public final int receiveQuantity;

    @JsonCreator
    public PortConfig(
            @JsonProperty("id") String id,
            @JsonProperty("texturePath") String texturePath,
            @JsonProperty("resourceID") String resourceID,
            @JsonProperty("giveQuantity") int giveQuantity,
            @JsonProperty("receiveQuantity") int receiveQuantity) {
        this.id = id;
        this.texturePath = texturePath;
        this.resourceID = resourceID;
        this.giveQuantity = giveQuantity;
        this.receiveQuantity = receiveQuantity;
    }

    @Override
    public String getId() {
        return this.id; 
    }
}
