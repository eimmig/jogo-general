package br.utfpr.edu.jogogeneral.controller;

import br.utfpr.edu.jogogeneral.model.Jogador;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;


/*
* Classe campeonato
*  funciona como um segundo controlador
*  nela temos os jogadores do campeonato e algumas funções
* */
public class Campeonato {
    private Jogador[] jogadores;

    private int numJogadores;

    public Campeonato(int maxJogadores) {
        jogadores = new Jogador[maxJogadores];
        numJogadores = 0;
    }

    public Campeonato(int maxJogadores, Jogador[] jogadores) {
        this.jogadores = jogadores;
        this.numJogadores = maxJogadores;
    }

    public int getNumJogadores() {
        return numJogadores;
    }

    public Jogador[] getJogadores() {
        return jogadores;
    }

    public void setNumJogadores(int numJogadores) {
        this.numJogadores = numJogadores;
    }

    public void setJogadores(Jogador[] jogadores) {
        this.jogadores = jogadores;
    }

    //funcao para incluir jogadores

    public boolean incluirJogador(Jogador jogador) {
        if (numJogadores < jogadores.length) {
            jogadores[numJogadores] = jogador;
            numJogadores++;
            return true;
        } else {
            System.out.println("Limite máximo de jogadores atingido.");
            return false;
        }
    }


    //funcao remover jogador
    public void removerJogador(Long id) {
        Jogador[] jogadores = this.getJogadores();

        Jogador[] novoArray = new Jogador[jogadores.length - 1]; //crio um array com jogadores -1

        int novoIndice = 0;

        for (Jogador jogador : jogadores) {
            if (jogador == null) {
                break;
            }

            if (jogador.getId().longValue() != id) {
                novoArray[novoIndice] = jogador;
                novoIndice++;
            }
        }
        this.setJogadores(novoArray);
    }

    //funcao iniciar o campeonato
    public void iniciarCampeonato() throws Exception {
        try {
            gravarEmArquivo("campeonato.dat");
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    //gravar em arquivo
    public void gravarEmArquivo(String nomeArquivo) throws Exception {
        Path arquivoPath = Paths.get(nomeArquivo);
        try {
            // Verifica se o arquivo existe
            if (Files.exists(arquivoPath)) {
                // Apaga o arquivo se ele existe
                Files.delete(arquivoPath);
            }
        } catch (IOException e) {
            throw  new Exception(e);
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            outputStream.writeObject(jogadores);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }


    //ler objetos do arquivo para a cartela
    public Jogador[] lerDoArquivo(String nomeArquivo) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            Jogador[] jogadoresLidos = (Jogador[]) inputStream.readObject();
            numJogadores = jogadoresLidos.length;
            jogadores = Arrays.copyOf(jogadoresLidos, jogadoresLidos.length);
            System.out.println("Dados lidos do arquivo com sucesso.");
            return jogadoresLidos;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler do arquivo: " + e.getMessage());
            return new Jogador[0];
        }
    }
}
