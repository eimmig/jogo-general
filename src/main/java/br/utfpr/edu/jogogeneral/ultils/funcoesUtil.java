package br.utfpr.edu.jogogeneral.ultils;

//classe de funções uteis que são usadas em metodos como validacao e pontuacao

public class funcoesUtil {
    public static int contarNumeros(int[] numeros, int numero) {
        int count = 0;
        for (int num : numeros) {
            if (num == numero) {
                count++;
            }
        }
        return count;
    }

    public static int somarNumeros(int[] numeros) {
        int soma = 0;
        for (int num : numeros) {
            soma += num;
        }
        return soma;
    }
}
