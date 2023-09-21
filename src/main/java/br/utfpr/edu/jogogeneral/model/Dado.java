package br.utfpr.edu.jogogeneral.model;

import java.util.Random;

public class Dado {
    private int sideUp;

    public Dado() {
        roll();
    }

    public void roll() {
        Random rand = new Random();
        this.sideUp = rand.nextInt(6) + 1;
    }

    public int getSideUp() {
        return this.sideUp;
    }

    public void setSideUp(int sideUp) {
        this.sideUp = sideUp;
    }

    @Override
    public String toString() {
        return String.valueOf(this.sideUp);
    }
}
