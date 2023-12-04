package br.utfpr.edu.jogogeneral.model;

import br.utfpr.edu.jogogeneral.ultils.JogadaDTO;

public interface JogarComoHumano {
    int escolherJogo();
     void escolherJogada(JogadaDTO jogogada, JogoGeneral jogo);
}
