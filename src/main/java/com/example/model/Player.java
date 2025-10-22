package com.example.model;

import java.util.ArrayList;

public class Player {
    
    //player ID
    private int playerID;

    //player colour
    private String colour;

    //player name
    private String name;

    //resources owned
    private ArrayList<resourceType> resources;

    //development cards owned
    private ArrayList<devCard> devCards;

    //remaining structures to be built
    private ArrayList<structureType> structuresRemaining;

    //default constructor
    public Player(){}

    //getters
    public int getPlayerID(){return playerID;}
    public String getColour() {return colour;}
    public String getName() {return name;}
    public ArrayList<resourceType> getResources() {return resources;}
    public ArrayList<devCard> getDevCards() {return devCards;}
    public ArrayList<structureType> getStructuresRemaining() {return structuresRemaining;}

    // setters
    public void setPlayerID(int _playerID) {this.playerID = _playerID;}
    public void setColour(String _colour) {this.colour = _colour;}
    public void setName(String _name) {this.name = _name; }
    public void setResources(ArrayList<resourceType> _resources) {this.resources = _resources;}
    public void setDevCards(ArrayList<devCard> _devCards) {this.devCards = _devCards;}
    public void setStructuresRemaining(ArrayList<structureType> _structuresRemaining) {this.structuresRemaining = _structuresRemaining;}
}
