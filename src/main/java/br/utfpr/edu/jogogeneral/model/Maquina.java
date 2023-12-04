package br.utfpr.edu.jogogeneral.model;

public class Maquina extends Jogador implements JogarComoMaquina{

    @Override
    public int aplicarEstrategia() {
        return 0;
    }

    public Maquina(String nome) {
        super(nome, "M");
    }
}
