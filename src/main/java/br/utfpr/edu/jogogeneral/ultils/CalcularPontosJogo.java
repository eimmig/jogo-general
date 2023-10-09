package br.utfpr.edu.jogogeneral.ultils;

//como tem funções em comum com a de validação resolvi extender da classe funcoesUtil

public class CalcularPontosJogo extends funcoesUtil {

    //switch case na opção onde já retorna o valor em pontos da jogada
    public static int calcularPontos(int[] numeros, int opcao) {
        return switch (opcao) {
            case 0 -> contarNumeros(numeros, 1);
            case 1 -> contarNumeros(numeros, 2) * 2;
            case 2 -> contarNumeros(numeros, 3) * 3;
            case 3 -> contarNumeros(numeros, 4) * 4;
            case 4 -> contarNumeros(numeros, 5) * 5;
            case 5 -> contarNumeros(numeros, 6) * 6;
            case 6, 12, 7 -> somarNumeros(numeros);
            case 8 -> 25; // Valor fixo para Full-house
            case 9 -> 30; // Valor fixo para Sequência alta
            case 10 -> 40; // Valor fixo para Sequência baixa
            case 11 -> 50; // Valor fixo para General
            default -> 0; // Opção inválida
        };
    }

}
