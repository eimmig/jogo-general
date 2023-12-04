package br.utfpr.edu.jogogeneral.controller;

import br.utfpr.edu.jogogeneral.model.*;
import br.utfpr.edu.jogogeneral.ultils.CarregarInformacoesDTO;
import br.utfpr.edu.jogogeneral.ultils.JogadaDTO;
import br.utfpr.edu.jogogeneral.ultils.JogadorDTO;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;


/*
* Classe campeonato
*  funciona como um segundo controlador
*  nela temos os jogadores do campeonato e algumas funções
* */
public class Campeonato {
    private Jogador[] jogadores;

    private int numJogadores;

    //controle de jogador na vez
    private static int jogadorDaVez = 0;

    public Campeonato(int maxJogadores) {
        jogadores = new Jogador[maxJogadores];
        numJogadores = 0;
    }

    public Campeonato(int maxJogadores, Jogador[] jogadores) {
        this.jogadores = jogadores;
        this.numJogadores = maxJogadores;
    }

    public int getNumJogadores() {
        return numJogadores;
    }

    public Jogador[] getJogadores() {
        return jogadores;
    }

    public void setNumJogadores(int numJogadores) {
        this.numJogadores = numJogadores;
    }

    public void setJogadores(Jogador[] jogadores) {
        this.jogadores = jogadores;
    }

    //funcao para incluir jogadores

    public boolean incluirJogador(Jogador jogador) {
        if (numJogadores < jogadores.length) {
            jogadores[numJogadores] = jogador;
            numJogadores++;
            return true;
        } else {
            System.out.println("Limite máximo de jogadores atingido.");
            return false;
        }
    }


    //funcao remover jogador
    public void removerJogador(Long id) {
        Jogador[] jogadores = this.getJogadores();

        Jogador[] novoArray = new Jogador[jogadores.length]; //crio um array com jogadores

        int novoIndice = 0;

        for (Jogador jogador : jogadores) {
            if (jogador == null) {
                break;
            }

            if (jogador.getId().longValue() != id) {
                novoArray[novoIndice] = jogador;
                novoIndice++;
            } else {
                novoArray[novoIndice] = null;
            }
        }
        this.setJogadores(novoArray);
        if (this.jogadores[0] != null) {
            this.numJogadores = novoArray.length;

        } else {
            this.numJogadores = 0;
        }
    }

    //funcao iniciar o campeonato
    public void iniciarCampeonato() throws Exception {
        try {
            gravarEmArquivo("campeonato.dat");
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    //gravar em arquivo
    public void gravarEmArquivo(String nomeArquivo) throws Exception {
        Path arquivoPath = Paths.get(nomeArquivo);
        try {
            // Verifica se o arquivo existe
            if (Files.exists(arquivoPath)) {
                // Apaga o arquivo se ele existe
                Files.delete(arquivoPath);
            }
        } catch (IOException e) {
            throw  new Exception(e);
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            outputStream.writeObject(jogadores);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }


    //ler objetos do arquivo para a cartela
    public Jogador[] lerDoArquivo(String nomeArquivo) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            Jogador[] jogadoresLidos = (Jogador[]) inputStream.readObject();
            numJogadores = jogadoresLidos.length;
            jogadores = Arrays.copyOf(jogadoresLidos, jogadoresLidos.length);
            System.out.println("Dados lidos do arquivo com sucesso.");
            return jogadoresLidos;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao ler do arquivo: " + e.getMessage());
            return new Jogador[0];
        }
    }
    public CarregarInformacoesDTO carregarInformacoes() {
        if (jogadorDaVez == -1) {
            jogadorDaVez = 0;
        }


        JogadorDTO[] jogadoresDTO = new JogadorDTO[this.numJogadores];
        for (int i = 0; i < numJogadores; i++) {
            jogadoresDTO[i] = new JogadorDTO(this.jogadores[i], "ok");
        }

        CarregarInformacoesDTO informacoes = new CarregarInformacoesDTO(jogadorDaVez, jogadoresDTO);

        if (jogadorDaVez + 1 > numJogadores-1) {
            jogadorDaVez = 0;
        } else {
            jogadorDaVez++;
        }

        return informacoes;
    }

    public void setJogoEscolhido(String jogoEscolhido) {
        JogoDados novoJogo;
        if (jogoEscolhido.equals("Azar")) {
            novoJogo = new JogoAzar();
        } else {
            novoJogo = new JogoGeneral();
        }
        // Verifica se o array jogos já foi inicializado
        JogoDados[] jogos = this.jogadores[jogadorDaVez].getJogos();
        if (jogos == null) {
            // Se não foi inicializado, crie um novo array com tamanho 1
            this.jogadores[jogadorDaVez].setJogos(new JogoDados[]{novoJogo});
        } else {
            // Se o array já foi inicializado, verifique se tem comprimento maior que zero
            if (jogos.length > 0) {
                // Se tiver comprimento maior que zero, crie um novo array com tamanho aumentado em 1
                JogoDados[] novoArray = new JogoDados[jogos.length + 1];

                // Copie os jogos existentes para o novo array
                System.arraycopy(jogos, 0, novoArray, 0, jogos.length);

                novoArray[jogos.length] = novoJogo;

                // Atualiza a referência para o novo array
                this.jogadores[jogadorDaVez].setJogos(novoArray);
            } else {
                // Se o array tem comprimento zero, crie um novo array com tamanho 1
                this.jogadores[jogadorDaVez].setJogos(new JogoDados[]{novoJogo});
            }
        }
    }

    public void realizarJogada(Jogador jogador, JogadaDTO jogada) {

        JogoDados[] jogos = jogador.getJogos();
        JogoGeneral ultimoJogoNaoNulo = null;
        for (int i = jogos.length - 1; i >= 0; i--) {
            if (jogos[i] != null) {
                ultimoJogoNaoNulo = (JogoGeneral) jogos[i];
                break;
            }
        }
        //a logica acima praticamente via pegar o ultimo registro que não é nulo. Esse pode ser validado da maneira que será sempre do jogo que esta sendo jogado agora
        ((Humano) jogador).escolherJogada(jogada, ultimoJogoNaoNulo);
    }

    public int[] mostrarJogadas(Jogador jogador) {
        JogoDados[] jogos = jogador.getJogos();
        JogoGeneral ultimoJogoNaoNulo = null;
        for (int i = jogos.length - 1; i >= 0; i--) {
            if (jogos[i] != null) {
                ultimoJogoNaoNulo = (JogoGeneral) jogos[i];
                break;
            }
        }
        return jogador.mostrarJogadasExecutadas(ultimoJogoNaoNulo);
    }
}
