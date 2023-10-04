package br.utfpr.edu.jogogeneral.ultils;


public class RequestPessoa {
    private String nome;
    private String tipo;

    public RequestPessoa(String nome, String tipo) {
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
