package com.example.model;
public class Tile {

    //the resource the tile produces
    private int tileID;
    //the number that needs rolled to produce said resource
    private int number;
    //stores every possible location for a settlement
    private int[] adjVertices;
    //if blocked by a robber, true
    private boolean isBlocked;

    //default constructor
    public Tile() {}

    //paramaterised constructor
    public Tile(int _tileID, int _number,  int[] _adjVertices, boolean _isBlocked){
        tileID = _tileID;
        number = _number;
        adjVertices = _adjVertices;
        isBlocked = _isBlocked;
    }

    //getters
    public int getResource(){ return this.tileID; }
    public int getNumber(){ return this.number; }
    public int[] getAdjVertices(){ return this.adjVertices; }
    public boolean getIsBlocked(){ return this.isBlocked; }

    //setters
    public void setResource(int _tileID){ this.tileID = _tileID;}
    public void setNumber(int _number){ this.number = _number;}
    public void setAdjVertices(int[] _adjVertices){ this.adjVertices = _adjVertices;}
    public void setIsBlocked(boolean _isBlocked){ this.isBlocked = _isBlocked;}
}
