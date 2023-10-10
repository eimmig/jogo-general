package br.utfpr.edu.jogogeneral.ultils;

/*
 * classe DTO apenas para comunicacao entre front e backend
 * */
public class JogadorCartelaDTO {
    int idJogador;
    int[] jogadas;

    public int[] getJogadas() {
        return jogadas;
    }

    public void setJogadas(int[] jogadas) {
        this.jogadas = jogadas;
    }

    public void setIdJogador(int idJogador) {
        this.idJogador = idJogador;
    }

    public int getIdJogador() {
        return idJogador;
    }

    public JogadorCartelaDTO(int idJogador, int[] jogadas) {
        this.idJogador = idJogador;
        this.jogadas = jogadas;
    }
}
