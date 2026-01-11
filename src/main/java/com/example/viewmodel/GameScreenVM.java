package com.example.viewmodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.view.GameScreenV;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * ViewModel for the main game screen.
 * Owns all game state and rules.
 */
public final class GameScreenVM 
{
    public class HexModel 
    {
        private final int id;
        private final StringProperty resource = new SimpleStringProperty();
        private final IntegerProperty diceNumber = new SimpleIntegerProperty();
        private final BooleanProperty hasRobber = new SimpleBooleanProperty();

        public HexModel(int id, String resource, int diceNumber, boolean hasRobber) {
            this.id = id;
            this.resource.set(resource);
            this.diceNumber.set(diceNumber);
            this.hasRobber.set(hasRobber);
        }

        // Getters and setters
        public int getId() { return id; }
        public StringProperty resourceProperty() { return resource; }
        public IntegerProperty diceNumberProperty() { return diceNumber; }
        public BooleanProperty hasRobberProperty() { return hasRobber; }

        public void setResource(String r) { resource.set(r); }
        public void setDiceNumber(int d) { diceNumber.set(d); }
        public void setRobber(boolean b) { hasRobber.set(b); }
    }

    private final ObservableList<HexModel> hexes = FXCollections.observableArrayList();

    // Constructor or initialization populates hexes list
    // public GameScreenViewModel(List<HexModel> initialHexes) {
    //     hexes.addAll(initialHexes);
    // }

    public ObservableList<HexModel> getHexes() {
        return hexes;
    }

    // ---- THE CHANGE METHOD ----
    public void changeHex(int hexID, String resource, int token, boolean robber) {
        for (HexModel hex : hexes) {
            if (hex.getId() == hexID) {
                if (resource != null) hex.setResource(resource);
                if (token >= 0) hex.setDiceNumber(token);
                hex.setRobber(robber);
                break;
            }
        }
    }           
}
