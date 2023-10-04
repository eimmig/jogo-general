package br.utfpr.edu.jogogeneral.model;

import br.utfpr.edu.jogogeneral.controller.Campeonato;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class JogoGeneral {
    @JsonProperty("dados")
    private Dado[] dados;
    @JsonProperty("jogadas")
    private int[] jogadas;

    @JsonProperty("campeonato")
    private Campeonato campeonato;

    public JogoGeneral() {
        dados = new Dado[5];
        for (int i = 0; i < 5; i++) {
            dados[i] = new Dado();
        }

        jogadas = new int[13];
    }

    public Dado[] rolarDados() {
        for (int i = 0; i < 5; i++) {
            dados[i].roll();
        }

        return dados;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public Campeonato getCampeonato(Campeonato campeonato) {
        return this.campeonato;
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

    public boolean validarJogada(int jogada) {
        // Implementação de validação da jogada
        return true;
    }

    public void pontuarJogada(int jogada, int pontos) {
        if (validarJogada(jogada)) {
            jogadas[jogada - 1] = pontos;
        } else {
            System.out.println("Jogada inválida. Os pontos não foram registrados.");
        }
    }
}
