package com.example.model;

public class Road {
    
    //player who owns this road
    private int playerID;

    //array of the two vertices the road connects
    private int[] vertices;

    //default constructor
    public Road(){}

    //parameterised constructor
    public Road(int _playerID, int[] _vertices){
        this.playerID = _playerID;
        this.vertices = _vertices;
    }

    //getters
    public int getPlayerID(){return this.playerID;}
    public int[] getVertices(){return this.vertices;}

    //setters
    public void setPlayerID(int _playerID){this.playerID = _playerID;}
    public void setVertices(int[] _vertices){this.vertices = _vertices;}
}
