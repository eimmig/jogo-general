package br.utfpr.edu.jogogeneral.model;

import br.utfpr.edu.jogogeneral.ultils.JogadaDTO;

import java.io.Serializable;



/*
* classe jogador, compreende todas as funcionalidades e metodos do jogador
*
*
* */
public abstract class Jogador implements Serializable {
    private String nome;
    private String tipo;
    private JogoDados[] jogos;
    private float valorDisponivel;

    private final Integer id;

    private static int lastId = 0;


    public Jogador(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
        this.id = lastId++;
        this.valorDisponivel = 100L;
    }

    public Jogador(int id) {
        this.nome = "";
        this.tipo = "M";
        this.id = lastId++;
        this.valorDisponivel = 100L;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public JogoDados[] getJogos() {
        return jogos;
    }

    public void setJogos(JogoDados[] jogos) {
        this.jogos = jogos;
    }

    public Integer getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getValorDisponivel() {
        return valorDisponivel;
    }

    public void setValorDisponivel(float valorDisponivel) {
        this.valorDisponivel = valorDisponivel;
    }

    public Dado[] jogarDados(JogoDados jogo) {

       return jogo.rolarDados();
    }

    public int[] mostrarJogadasExecutadas(JogoGeneral jogo) {
        return jogo.getJogadas();
    }

    public void escolherJogada(JogadaDTO jogada, JogoGeneral jogo) {
        jogo.pontuarJogada(jogada);
    }

    public int getNumeroJogos() {
        if (jogos == null) {
            return 0;
        }

        int contador = 0;
        for (JogoDados jogo : jogos) {
            if (jogo != null) {
                contador++;
            }
        }
        return contador;
    }
}
