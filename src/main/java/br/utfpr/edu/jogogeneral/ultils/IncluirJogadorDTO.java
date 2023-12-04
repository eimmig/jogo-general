package br.utfpr.edu.jogogeneral.ultils;

//esse arquivo é apenas um DTO de comunicação com o frontend, nenhum método é executado a partir dele, apeans getter e/ou setters
public class IncluirJogadorDTO {
    private String nome;
    private String tipo;
    private String cpf;
    private String agencia;
    private String conta;
    private int numeroBanco;

    public IncluirJogadorDTO(String nome, String tipo, String cpf, String agencia, String conta, int numeroBanco) {
        this.nome = nome;
        this.tipo = tipo;
        this.cpf = cpf;
        this.agencia = agencia;
        this.conta = conta;
        this.numeroBanco = numeroBanco;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCpf() {
        return cpf;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getConta() {
        return conta;
    }

    public int getNumeroBanco() {
        return numeroBanco;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public void setNumeroBanco(int numeroBanco) {
        this.numeroBanco = numeroBanco;
    }
}
