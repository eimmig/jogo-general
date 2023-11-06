package br.utfpr.edu.jogogeneral.ultils;

import java.util.Arrays;

//como tem funções em comum com a de potuacao resolvi extender da classe FuncoesUtil

public class ValidacaoJogo extends FuncoesUtil {

    /*a função seguinte é a principal do método, ela quem vai controlar se é uma jogada valida ou nao
    * as demais funções são apenas funções de validação de jogadas onde elas retornam para
    *  esse método o qual chama elas.
    */
    public static boolean validarJogada(int[] numeros, int opcao) {
        return switch (opcao) {
            case 0 -> contarNumeros(numeros, 1) >= 1;
            case 1 -> contarNumeros(numeros, 2) >= 1;
            case 2 -> contarNumeros(numeros, 3) >= 1;
            case 3 -> contarNumeros(numeros, 4) >= 1;
            case 4 -> contarNumeros(numeros, 5) >= 1;
            case 5 -> contarNumeros(numeros, 6) >= 1;
            case 6 -> verificarTrinca(numeros);
            case 7 -> verificarQuadra(numeros);
            case 8 -> verificarFullHouse(numeros);
            case 9 -> verificarSequenciaAlta(numeros);
            case 10 -> verificarSequenciaBaixa(numeros);
            case 11 -> verificarGeneral(numeros);
            case 12 -> true; // Sempre válido para jogada aleatória
            default -> false; // Opção inválida
        };
    }

    public static boolean verificarTrinca(int[] numeros) {
        for (int num : numeros) {
            if (contarNumeros(numeros, num) >= 3) {
                return true;
            }
        }
        return false;
    }

    public static boolean verificarQuadra(int[] numeros) {
        for (int num : numeros) {
            if (contarNumeros(numeros, num) >= 4) {
                return true;
            }
        }
        return false;
    }

    public static boolean verificarFullHouse(int[] numeros) {
        Arrays.sort(numeros);
        if ((numeros[0] == numeros[1] && numeros[2] == numeros[3] && numeros[3] == numeros[4])
                || (numeros[0] == numeros[1] && numeros[1] == numeros[2] && numeros[3] == numeros[4])) {
            return true;
        }
        return false;
    }

    public static boolean verificarSequenciaAlta(int[] numeros) {
        Arrays.sort(numeros);
        return (numeros[0] == 2 && numeros[1] == 3 && numeros[2] == 4 && numeros[3] == 5 && numeros[4] == 6);
    }

    public static boolean verificarSequenciaBaixa(int[] numeros) {
        Arrays.sort(numeros);
        return (numeros[0] == 1 && numeros[1] == 2 && numeros[2] == 3 && numeros[3] == 4 && numeros[4] == 5);
    }

    public static boolean verificarGeneral(int[] numeros) {
        for (int num : numeros) {
            if (contarNumeros(numeros, num) >= 5) {
                return true;
            }
        }
        return false;
    }
}
