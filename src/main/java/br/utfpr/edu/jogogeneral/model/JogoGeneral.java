package br.utfpr.edu.jogogeneral.model;

public class JogoGeneral {
    private Dado[] dados;
    private int[] jogadas;

    public JogoGeneral() {
        dados = new Dado[5];
        for (int i = 0; i < 5; i++) {
            dados[i] = new Dado();
        }

        jogadas = new int[13];
    }

    public void rolarDados() {
        for (int i = 0; i < 5; i++) {
            dados[i].roll();
        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Valores dos Dados: ");
        for (int i = 0; i < 5; i++) {
            result.append(dados[i].getSideUp()).append(" ");
        }
        result.append("\n");
        result.append("Jogadas: ");
        for (int i = 0; i < jogadas.length; i++) {
            result.append(jogadas[i]).append(" ");
        }
        return result.toString();
    }

    public boolean validarJogada(int jogada) {
        // Implementação de validação da jogada
        return true;
    }

    public void pontuarJogada(int jogada, int pontos) {
        if (validarJogada(jogada)) {
            jogadas[jogada - 1] = pontos;
        } else {
            System.out.println("Jogada inválida. Os pontos não foram registrados.");
        }
    }
}
