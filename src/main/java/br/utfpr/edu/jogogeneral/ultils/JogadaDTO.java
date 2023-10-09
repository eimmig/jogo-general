package br.utfpr.edu.jogogeneral.ultils;

//esse arquivo é apenas um DTO de comunicação com o frontend, nenhum método é executado a partir dele, apeans getter e/ou setters

public class JogadaDTO {

    private int opcao;

    private int jogador;

    private int[] dados;

    public JogadaDTO(int opcao, int jogador, int[] dados) {
        this.opcao = opcao;
        this.dados = dados;
        this.jogador = jogador;
    }

    public int[] getDados() {
        return dados;
    }

    public void setDados(int[] dados) {
        this.dados = dados;
    }

    public int getJogador() {
        return jogador;
    }

    public void setJogador(int jogador) {
        this.jogador = jogador;
    }

    public int getOpcao() {
        return opcao;
    }

    public void setOpcao(int opcao) {
        this.opcao = opcao;
    }
}
