package br.utfpr.edu.jogogeneral.controller;

import br.utfpr.edu.jogogeneral.model.Jogador;

import java.io.*;
import java.util.Arrays;

public class Campeonato {
    private Jogador[] jogadores;

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

    private int numJogadores;

    public Campeonato(int maxJogadores) {
        jogadores = new Jogador[maxJogadores];
        numJogadores = 0;
    }

    public void incluirJogador(Jogador jogador) {
        if (numJogadores < jogadores.length) {
            jogadores[numJogadores] = jogador;
            numJogadores++;
        } else {
            System.out.println("Limite máximo de jogadores atingido.");
        }
    }

    public void removerJogador(Jogador jogador) {
        for (int i = 0; i < numJogadores; i++) {
            if (jogadores[i].equals(jogador)) {
                for (int j = i; j < numJogadores - 1; j++) {
                    jogadores[j] = jogadores[j + 1];
                }
                numJogadores--;
                return;
            }
        }
        System.out.println("Jogador não encontrado.");
    }

    public void iniciarCampeonato() {
        for (int i = 0; i < numJogadores; i++) {
            jogadores[i].jogarDados();
        }
    }

    public void mostrarCartela() {
        for (int i = 0; i < numJogadores; i++) {
            System.out.println("Cartela de " + jogadores[i].getNome() + ":");
            jogadores[i].mostrarJogadasExecutadas();
            System.out.println();
        }
    }

    public void gravarEmArquivo(String nomeArquivo) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            outputStream.writeObject(jogadores);
            System.out.println("Dados gravados em arquivo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao gravar em arquivo: " + e.getMessage());
        }
    }

    public void lerDoArquivo(String nomeArquivo) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            Jogador[] jogadoresLidos = (Jogador[]) inputStream.readObject();
            numJogadores = jogadoresLidos.length;
            jogadores = Arrays.copyOf(jogadoresLidos, jogadoresLidos.length);
            System.out.println("Dados lidos do arquivo com sucesso.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler do arquivo: " + e.getMessage());
        }
    }
}
