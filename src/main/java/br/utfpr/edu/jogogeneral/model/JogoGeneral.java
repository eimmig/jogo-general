package br.utfpr.edu.jogogeneral.model;

import br.utfpr.edu.jogogeneral.controller.Campeonato;
import br.utfpr.edu.jogogeneral.ultils.JogadaDTO;

import java.io.Serializable;

public class JogoGeneral implements Serializable {
    private Dado[] dados;
    private int[] jogadas;

    private Campeonato campeonato;

    public JogoGeneral() {
        dados = new Dado[5];
        for (int i = 0; i < 5; i++) {
            dados[i] = new Dado();
        }

        jogadas = new int[13];
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public Campeonato getCampeonato(Campeonato campeonato) {
        return this.campeonato;
    }

    public Dado[] getDados() {
        return dados;
    }

    public void setDados(Dado[] dados) {
        this.dados = dados;
    }

    public int[] getJogadas() {
        return jogadas;
    }

    public void setJogadas(int[] jogadas) {
        this.jogadas = jogadas;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Valores dos Dados: ");
        for (int i = 0; i < 5; i++) {
            result.append(dados[i].getSideUp()).append(" ");
        }
        result.append("\n");
        result.append("Jogadas: ");
        for (int i = 0; i < jogadas.length; i++) {
            result.append(jogadas[i]).append(" ");
        }
        return result.toString();
    }

    public Dado[] rolarDados() {
        for (int i = 0; i < 5; i++) {
            dados[i].roll();
        }

        return dados;
    }

    public boolean validarJogada(Integer[] dados, Integer opcao) {
        // Implementação de validação da jogada
        return true;
    }

    public void pontuarJogada(JogadaDTO jogada) {

        Integer opcao = jogada.getOpcao();

        Integer[] dados = jogada.getDados();

        if (validarJogada(dados, opcao)) {
            int pontos = 0;
            jogadas[opcao] = pontos;
        } else {
            System.out.println("Jogada inválida. Os pontos não foram registrados.");
        }
    }
}
