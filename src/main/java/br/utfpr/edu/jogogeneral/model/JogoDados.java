package br.utfpr.edu.jogogeneral.model;

import br.utfpr.edu.jogogeneral.model.Dado;

public abstract class JogoDados implements Estatistica{

    int nDados;
    String nomeJogo;
    float saldo;
    Dado[] dados;

    public JogoDados() {

    }

    public JogoDados(int nDados, String nomeJogo, float saldo) {
        this.nDados = nDados;
        this.nomeJogo = nomeJogo;
        this.saldo = saldo;

        dados = new Dado[nDados];
        for (int i = 0; i < nDados; i++) {
            dados[i] = new Dado();
        }
    }

    public abstract Dado[] rolarDados();
}
