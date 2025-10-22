package com.example.model;

public class Port {
    
    //stores what the port trades in
    private int portID;

    //stores where the port is
    private int[] adjVertices;

    
    //default constructor
    public Port(){}

    //paramaterised constructor
    public Port(int _portID, int[] _adjVertices){
        this.portID = _portID;
        this.adjVertices = _adjVertices;
    }

    //getters
    public int getPortID(){return portID;}
    public int[] getAdjVertices(){return adjVertices;}

    //setters
    public void setPortID(int _portID){this.portID = _portID;}
    public void setAdjVertices(int[] _adjVertices){this.adjVertices = _adjVertices;}
}
