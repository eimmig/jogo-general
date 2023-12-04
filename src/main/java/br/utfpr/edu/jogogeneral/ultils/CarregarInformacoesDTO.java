package br.utfpr.edu.jogogeneral.ultils;


/*
 * classe DTO apenas para comunicacao entre front e backend
 * */
public class CarregarInformacoesDTO {
    int jogadorDaVez;
    JogadorDTO[] jogadores;

    public CarregarInformacoesDTO(int jogadorDaVez, JogadorDTO[] jogadores) {
        this.jogadorDaVez = jogadorDaVez;
        this.jogadores = jogadores;
    }

    public int getJogadorDaVez() {
        return jogadorDaVez;
    }

    public void setJogadorDaVez(int jogadorDaVez) {
        this.jogadorDaVez = jogadorDaVez;
    }

    public JogadorDTO[] getJogadores() {
        return jogadores;
    }

    public void setJogadores(JogadorDTO[] jogadores) {
        this.jogadores = jogadores;
    }
}
