package br.utfpr.edu.jogogeneral.model;

import br.utfpr.edu.jogogeneral.ultils.JogadaDTO;

import java.io.Serializable;



/*
* classe jogador, compreende todas as funcionalidades e metodos do jogador
*
*
* */
public class Jogador implements Serializable {
    private String nome;
    private String tipoJogador;
    private JogoGeneral jogoG;

    private final Integer id;

    public Jogador(String nome, String tipoJogador, JogoGeneral jogoG, int id) {
        this.nome = nome;
        this.tipoJogador = tipoJogador;
        this.jogoG = jogoG;
        this.id = id;
    }

    public Jogador(JogoGeneral jogoG, int id) {
        this.nome = "";
        this.tipoJogador = "";
        this.jogoG = jogoG;
        this.id = id;
    }

    public Dado[] jogarDados() {
        return jogoG.rolarDados();
    }

    public void escolherJogada(JogadaDTO jogada) {
        jogoG.pontuarJogada(jogada);
    }

    public int[] mostrarJogadasExecutadas() {
        return jogoG.getJogadas();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoJogador() {
        return tipoJogador;
    }

    public void setTipoJogador(String tipoJogador) {
        this.tipoJogador = tipoJogador;
    }

    public JogoGeneral getJogoG() {
        return jogoG;
    }

    public void setJogoG(JogoGeneral jogoG) {
        this.jogoG = jogoG;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Jogador: " + nome + "\nTipo de Jogador: " + tipoJogador + "\nid: "  + id + "\n" + jogoG;
    }


    //metodo de jogadas restantes utilizado para jogada da maquina
    public int[] mostrarJogadasRestantes() {
        return removerValoresDiferentesDeZero(jogoG.getJogadas());
    }

    //metodo de para remover as jogadas ja usadas da maquina
    public static int[] removerValoresDiferentesDeZero(int[] array) {
        int count = 0;

        for (int valor : array) {
            if (valor != 0) {
                count++;
            }
        }
        int[] novoArray = new int[12-count];

        for (int i = 0; i < novoArray.length; i++) {
                novoArray[i] = i;
        }
        return novoArray;
    }
}
