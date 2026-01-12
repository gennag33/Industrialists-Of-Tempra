package com.example.model;

public class Dice {
    private int die1;
    private int die2;

    public Dice() {
        this.die1 = 1;
        this.die2 = 1;
    }

    public int roll() {
        this.die1 = (int)(Math.random() * 6) + 1;
        this.die2 = (int)(Math.random() * 6) + 1;
        return die1 + die2;
    }

    public int getDie1() {
        return die1;
    }

    public int getDie2() {
        return die2;
    }

}
