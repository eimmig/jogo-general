package br.utfpr.edu.jogogeneral.ultils;

import br.utfpr.edu.jogogeneral.model.Jogador;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JogadorDTO<T> {
    @JsonProperty("id")
    private int id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("message")
    private String message;

    public JogadorDTO(Jogador jogador, String message) {
        this.id = jogador.getId();
        this.nome = jogador.getNome();
        this.tipo = jogador.getTipoJogador();
        this.message = message;
    }
}