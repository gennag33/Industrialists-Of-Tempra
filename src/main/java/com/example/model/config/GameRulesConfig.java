package com.example.model.config;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameRulesConfig {
    public final int players;
    public final int victoryPointsToWin;
    public final int robberCardLimit;
    public final HashMap<Integer, Integer> numberTokens; // tokenValue -> quantity

    @JsonCreator
    public GameRulesConfig(
            @JsonProperty("players") int players,
            @JsonProperty("victoryPointsToWin") int victoryPointsToWin,
            @JsonProperty("robberCardLimit") int robberCardLimit,
            @JsonProperty("numberTokens") Map<Integer, Integer> numberTokens,
            @JsonProperty("robberNumberToken") int robberNumberToken) {
        this.players = players;
        this.victoryPointsToWin = victoryPointsToWin;
        this.robberCardLimit = robberCardLimit;
        this.numberTokens = new HashMap<>(numberTokens); // make mutable copy
    }
}
