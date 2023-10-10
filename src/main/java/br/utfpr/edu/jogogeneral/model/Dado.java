package br.utfpr.edu.jogogeneral.model;

import java.io.Serializable;
import java.util.Random;


/*
* classe dos dados, contem os lados e a funcao de roll
*
* */
public class Dado implements Serializable {
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
