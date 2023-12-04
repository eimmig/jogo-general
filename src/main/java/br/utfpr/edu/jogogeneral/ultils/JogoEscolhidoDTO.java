package br.utfpr.edu.jogogeneral.ultils;

import br.utfpr.edu.jogogeneral.model.Jogador;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


//esse arquivo é apenas um DTO de comunicação com o frontend, nenhum método é executado a partir dele, apeans getter e/ou setters

public class JogoEscolhidoDTO {
   String jogoEscolhido;

    public String getJogoEscolhido() {
        return jogoEscolhido;
    }

    public void setJogoEscolhido(String jogoEscolhido) {
        this.jogoEscolhido = jogoEscolhido;
    }

    public JogoEscolhidoDTO() { }
}
