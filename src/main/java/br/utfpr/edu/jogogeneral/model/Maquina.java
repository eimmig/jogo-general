package br.utfpr.edu.jogogeneral.model;

public class Maquina extends Jogador implements JogarComoMaquina{

    @Override
    public int[] aplicarEstrategia(JogoGeneral jogo) {
        return removerValoresDiferentesDeZero(jogo.getJogadas());
    }

    public Maquina(String nome) {
        super(nome, "M");
    }

    //metodo de para remover as jogadas ja usadas da maquina
    public static int[] removerValoresDiferentesDeZero(int[] array) {
        int count = 0;

        for (int valor : array) {
            if (valor != 0) {
                count++;
            }
        }
        int[] novoArray = new int[12-count];

        for (int i = 0; i < novoArray.length; i++) {
            novoArray[i] = i;
        }
        return novoArray;
    }
}
