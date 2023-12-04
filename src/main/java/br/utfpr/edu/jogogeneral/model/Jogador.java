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
    private final Integer id;

    private static int lastId = 0;


    public Jogador(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
        this.id = lastId++;
    }

    public Jogador(int id) {
        this.nome = "";
        this.tipo = "M";
        this.id = lastId++;
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

    //metodo de jogadas restantes utilizado para jogada da maquina
//    public int[] mostrarJogadasRestantes() {
//        return removerValoresDiferentesDeZero(jogoG.getJogadas());
//    }

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

    public Dado[] jogarDados() {
        int indexJogo = this.jogos.length;

        //vai pegar o ultimo pois é o jogo que ta sendo jogado agora
        JogoDados jogo = this.jogos[indexJogo-1];
       return jogo.rolarDados();
    }

    public int[] mostrarJogadasExecutadas(JogoGeneral jogo) {
        return jogo.getJogadas();
    }
}
