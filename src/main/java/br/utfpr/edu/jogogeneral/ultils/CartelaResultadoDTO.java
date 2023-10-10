package br.utfpr.edu.jogogeneral.ultils;

/*
* classe DTO apenas para comunicacao entre front e backend
* */
public class CartelaResultadoDTO {
    JogadorCartelaDTO[] jogadas;

    public CartelaResultadoDTO(JogadorCartelaDTO[] jogadas) {
        this.jogadas = jogadas;
    }

    public JogadorCartelaDTO[] getJogadas() {
        return jogadas;
    }

    public void setJogadas(JogadorCartelaDTO[] jogadas) {
        this.jogadas = jogadas;
    }
}
