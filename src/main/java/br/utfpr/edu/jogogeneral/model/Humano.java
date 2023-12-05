package br.utfpr.edu.jogogeneral.model;

import br.utfpr.edu.jogogeneral.ultils.JogadaDTO;

public class Humano extends Jogador implements  JogarComoHumano{

    private  String cpf;
    private String agencia;
    private String conta;
    private int numeroBanco;


    public Humano(String nome,  String cpf, String agencia, String conta, int numeroBanco) {
        super(nome, "H");
        this.cpf = cpf;
        this.agencia = agencia;
        this.conta = conta;
        this.numeroBanco = numeroBanco;
    }

    @Override
    public int escolherJogo() {
        return 0;
    }


}
