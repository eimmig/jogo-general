package br.utfpr.edu.jogogeneral.ultils;


public class JogadaDTO {

    private Integer opcao;

    private Integer jogador;

    private Integer[] dados;

    public JogadaDTO(Integer opcao, Integer jogador, Integer[] dados) {
        this.opcao = opcao;
        this.jogador = jogador;
        this.dados = dados;
    }

    public Integer[] getDados() {
        return dados;
    }

    public void setDados(Integer[] dados) {
        this.dados = dados;
    }

    public Integer getJogador() {
        return jogador;
    }

    public void setJogador(Integer jogador) {
        this.jogador = jogador;
    }

    public Integer getOpcao() {
        return opcao;
    }

    public void setOpcao(Integer opcao) {
        this.opcao = opcao;
    }
}
