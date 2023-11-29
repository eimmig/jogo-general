package br.utfpr.edu.jogogeneral.model;

import br.utfpr.edu.jogogeneral.model.Dado;

public abstract class JogoDados {

    int nDados;
    String nomeJogo;
    float saldo;
    Dado[] dados;

    public JogoDados() {

    }

    public abstract void rolarDados();
}
