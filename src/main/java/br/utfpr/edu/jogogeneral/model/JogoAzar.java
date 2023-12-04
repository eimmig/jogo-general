package br.utfpr.edu.jogogeneral.model;

public class JogoAzar extends JogoDados {
    private float valorAposta;

    public JogoAzar(float valorAposta) {
        this.valorAposta = valorAposta;
    }

    public JogoAzar() {
        super(2, "Jogo Azar", 0F);
    }

    @Override
    public Dado[] rolarDados() {
        for (int i = 0; i < nDados; i++) {
            dados[i].roll();
        }
        return dados;
    }

    public void executarRegrasJogo() {

    }

    @Override
    public int[] somarFacesSorteadas(Dado[] dados) {
        return new int[0];
    }

}
