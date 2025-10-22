package com.example.model;

public class Structure {
    
    //every vertex (point/corner) on the grid is given a unique number
    //this variable stores the vertex the structure is placed on
    private int vertex;

    //settlement or city
    private structureType type;

    //which player owns this structure
    private int playerID;

    //default constructor
    public Structure() {}

    //paramaterised constructor
    public Structure(int _vertex, structureType _type, int _playerID){
        this.vertex = _vertex;
        this.type = _type;
        this.playerID = _playerID;
    }

    //getters
    public int getVertex(){return vertex;}
    public structureType getType(){return type;}
    public int getPlayerID(){return playerID;}

    //setters
    public void setVertex(int _vertex){this.vertex = _vertex;}
    public void setType(structureType _type){this.type = _type;}
    public void setPlayerID(int _playerID){this.playerID = _playerID;}
}
