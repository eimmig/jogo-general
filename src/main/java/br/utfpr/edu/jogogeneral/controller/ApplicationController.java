package br.utfpr.edu.jogogeneral.controller;

import br.utfpr.edu.jogogeneral.model.Dado;
import br.utfpr.edu.jogogeneral.model.Jogador;
import br.utfpr.edu.jogogeneral.model.JogoGeneral;
import br.utfpr.edu.jogogeneral.ultils.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;


/*
*  Controller principal da aplicação
* todos os endpoints estão nesse arquivo
*  ele distribui as chamadas de responsabilidade para as outras classes
* */

@Controller
@RequestMapping("/controller")
public class ApplicationController {
    //incializo o jogo com todas as classes dele
    private final JogoGeneral jogo = new JogoGeneral();
    private final Campeonato campeonato = new Campeonato(10);
    @GetMapping("/")
    public String viewIndex() {
        return "./index";
    }

    //endpoint para finalizar a aplicação;
    @PostMapping("/exit")
    @ResponseBody
    public String exitApplication() {
        System.exit(0);

        return "Ok";
    }


    //endpoint que gera o numero dos dados
    @GetMapping("/rolarDados/{id}")
    public ResponseEntity<int[]> rolarDados(@PathVariable Integer id) {

        Jogador[] jogadores = this.campeonato.getJogadores();

        int[] valores = {1,1,1,1,1};

        for (Jogador jogador : jogadores) {
            if (Objects.equals(jogador.getId(), id)) {
                //chama o jogarDados para o jogador passado
                Dado[] dados = jogador.jogarDados();

                valores = new int[]{dados[0].getSideUp(), dados[1].getSideUp(), dados[2].getSideUp(), dados[3].getSideUp(), dados[4].getSideUp()};

                return new ResponseEntity<>(valores, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(valores, HttpStatus.OK);


    }


    //endpoint para incluir jogador
    @PostMapping(value = "/incluirJogador", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> incluirJogadores(@RequestBody IncluirJogadorDTO pessoa) {
        Jogador jogador = new Jogador(pessoa.getNome(), pessoa.getTipo(), this.jogo, this.campeonato.getNumJogadores());
        boolean incluiu = this.campeonato.incluirJogador(jogador);
        if (!incluiu) {
            jogador = new Jogador("0", "H", this.jogo, -1);
        }
        JogadorDTO responseObject = new JogadorDTO(jogador, (incluiu ? "Jogador incluído com sucesso" : "Limite de jogadores já atingido"));
        return ResponseEntity.ok(responseObject);
    }


    //endpoint remover jogador
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<String> removerJogador(@PathVariable Long id) {
        if (id != null) {
            this.campeonato.removerJogador(id);
            return ResponseEntity.ok("Jogador excluído com sucesso");
        } else {
            return ResponseEntity.badRequest().body("ID inválido");
        }
    }


    //endpoint iniciar campeonato, grava um primeiro registro dos jogadores
    @PostMapping("/iniciarCampeonato")
    public ResponseEntity<String> iniciarCampeonato() {
        try {
            this.campeonato.iniciarCampeonato();
            return ResponseEntity.ok("Campeonato Inciado!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao iniciar: " + e);
        }
    }


    //endpoint jogada manual
    @PostMapping(value = "/executarJogada", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> executarJogada(@RequestBody JogadaDTO jogada) {
        Jogador[] jogadores = this.campeonato.getJogadores();
        try {
            for (Jogador jogador : jogadores) {
                if (Objects.equals(jogador.getId(), jogada.getJogador())) {
                    jogador.escolherJogada(jogada);
                    this.campeonato.gravarEmArquivo("campeonato.dat");
                    return ResponseEntity.ok("Jogada Realizada com sucesso!");
                }
            }
            return ResponseEntity.badRequest().body("Erro ao realizar jogada: Jogador não encontrado");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao realizar jogada: " + e);
        }
    }


    //botão de mostrar jogadas do jogador
    @GetMapping("/mostrarJogadas/{id}")
    public ResponseEntity<int[]> MostrarJogadas(@PathVariable Integer id) {
        Jogador[] jogadores = this.campeonato.getJogadores();

        int[] jogadas = {0,0,0,0,0,0,0,0,0,0};

        for (Jogador jogador : jogadores) {
            if (Objects.equals(jogador.getId(), id)) {
                //chama o jogarDados para o jogador passado
                jogadas = jogador.mostrarJogadasExecutadas();

                return new ResponseEntity<>(jogadas, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(jogadas, HttpStatus.OK);
    }


    //cartela final de resultados
    @GetMapping("/mostrarCartelaFinal/")
    public ResponseEntity<CartelaResultadoDTO> MostrarJogadas() {
        Jogador[] jogadores = this.campeonato.lerDoArquivo("campeonato.dat");

        JogadorCartelaDTO[] jogadorCartela = new JogadorCartelaDTO[jogadores.length];
        int i = 0;
        for(Jogador jogador : jogadores) {
            if (jogador != null) {
                int[] jogadas = jogador.getJogoG().getJogadas();
                int id = jogador.getId();
                jogadorCartela[i] = new JogadorCartelaDTO(id, jogadas);
                i++;
            }
        }
        CartelaResultadoDTO cartela = new CartelaResultadoDTO(jogadorCartela);
        return new ResponseEntity<>(cartela, HttpStatus.OK);
    }


    //endpoint de jogada da máquina obs: esse endpoint esta com um pequeno bug, as vezes a maquina não consegue escolher a melhor jogada
    @PostMapping(value = "/jogadaMaquina", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> jogadaMaquina(@RequestBody JogadaDTO jogada) {
        Jogador[] jogadores = this.campeonato.getJogadores();
        try {
            for (Jogador jogador : jogadores) {
                if (Objects.equals(jogador.getId(), jogada.getJogador())) {
                    int[] opcoes = jogador.mostrarJogadasRestantes();
                    jogada.setOpcao(JogoGeneral.encontrarMelhorOpcao(jogada.getDados(), opcoes));

                    jogador.escolherJogada(jogada);
                    this.campeonato.gravarEmArquivo("campeonato.dat");
                    return ResponseEntity.ok("Jogada Realizada com sucesso!");
                }
            }
            return ResponseEntity.badRequest().body("Erro ao realizar jogada: Jogador não encontrado");
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao realizar jogada: " + e);
        }
    }
}