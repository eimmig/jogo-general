package br.utfpr.edu.jogogeneral.model;

import br.utfpr.edu.jogogeneral.controller.Campeonato;
import br.utfpr.edu.jogogeneral.ultils.CalcularPontosJogo;
import br.utfpr.edu.jogogeneral.ultils.JogadaDTO;
import br.utfpr.edu.jogogeneral.ultils.ValidacaoJogo;

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

    //funcao que valida se a jogada é valida

    public boolean validarJogada(int[] dados, int opcao) {
        return ValidacaoJogo.validarJogada(dados, opcao);
    }


    // funcao que pontua a jogada se é valida

    public void pontuarJogada(JogadaDTO jogada) {
        int opcao = jogada.getOpcao();

        int[] dados = jogada.getDados();

        if (validarJogada(dados, opcao) && jogadas[opcao] == 0) {
            jogadas[opcao] = CalcularPontosJogo.calcularPontos(dados, opcao);
        } else {
            jogadas[opcao] = 0;
        }
    }


    /* essa funcao é responsavel por escolher em qual opção
    * a máquina irá jogar o resultado dos dados
    * tentei criar um método que fosse possivel sempre
    * jogar na melhor opção
     */
    public static int encontrarMelhorOpcao(int[] numeros, int[] opcoes) {
        int melhorPontuacao = 0;
        int melhorOpcao = 0;

        for (int opcao : opcoes) {
            int pontuacao = CalcularPontosJogo.calcularPontos(numeros, opcao);
            if (pontuacao > melhorPontuacao && ValidacaoJogo.validarJogada(numeros, opcao)) {
                melhorPontuacao = pontuacao;
                melhorOpcao = opcao;
            }
        }
        return melhorOpcao;
    }
}
