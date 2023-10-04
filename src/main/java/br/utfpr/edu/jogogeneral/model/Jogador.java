package br.utfpr.edu.jogogeneral.model;

public class Jogador {
    private String nome;
    private String tipoJogador;
    private JogoGeneral jogoG;

    private final Integer id;

    public Jogador(String nome, String tipoJogador, JogoGeneral jogoG, int id) {
        this.nome = nome;
        this.tipoJogador = tipoJogador;
        this.jogoG = jogoG;
        this.id = id;
    }

    public Jogador(JogoGeneral jogoG, int id) {
        this.nome = "";
        this.tipoJogador = "";
        this.jogoG = jogoG;
        this.id = id;
    }

    public void jogarDados() {
        jogoG.rolarDados();
    }

    public void escolherJogada(int jogada, int pontos) {
        jogoG.pontuarJogada(jogada, pontos);
    }

    public void mostrarJogadasExecutadas() {
        System.out.println("Jogadas Executadas: ");
        System.out.println(jogoG);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoJogador() {
        return tipoJogador;
    }

    public void setTipoJogador(String tipoJogador) {
        this.tipoJogador = tipoJogador;
    }

    public JogoGeneral getJogoG() {
        return jogoG;
    }

    public void setJogoG(JogoGeneral jogoG) {
        this.jogoG = jogoG;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Jogador: " + nome + "\nTipo de Jogador: " + tipoJogador + "\nid: "  + id + "\n" + jogoG;
    }
}
