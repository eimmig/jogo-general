package br.utfpr.edu.jogogeneral.ultils;

//esse arquivo é apenas um DTO de comunicação com o frontend, nenhum método é executado a partir dele, apeans getter e/ou setters
public class IncluirJogadorDTO {
    private String nome;
    private String tipo;

    public IncluirJogadorDTO(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
