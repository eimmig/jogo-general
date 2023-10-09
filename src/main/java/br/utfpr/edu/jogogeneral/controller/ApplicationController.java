package br.utfpr.edu.jogogeneral.controller;

import br.utfpr.edu.jogogeneral.model.Dado;
import br.utfpr.edu.jogogeneral.model.Jogador;
import br.utfpr.edu.jogogeneral.model.JogoGeneral;
import br.utfpr.edu.jogogeneral.ultils.JogadorDTO;
import br.utfpr.edu.jogogeneral.ultils.IncluirJogadorDTO;
import br.utfpr.edu.jogogeneral.ultils.JogadaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

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

    @PostMapping("/exit")
    @ResponseBody
    public String exitApplication() {
        System.exit(0);

        return "Ok";
    }

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

    @PostMapping(value = "/incluirJogador", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> incluirJogadores(@RequestBody IncluirJogadorDTO pessoa) {
        Jogador jogador = new Jogador(pessoa.getNome(), pessoa.getTipo(), this.jogo, this.campeonato.getNumJogadores());
        this.campeonato.incluirJogador(jogador);
        JogadorDTO responseObject = new JogadorDTO(jogador, "Jogador incluído com sucesso");
        return ResponseEntity.ok(responseObject);
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<String> removerJogador(@PathVariable Long id) {
        if (id != null) {
            Jogador[] jogadores = this.campeonato.getJogadores();

            Jogador[] novoArray = new Jogador[jogadores.length - 1]; //crio um array com jogadores -1

            int novoIndice = 0;

            for (Jogador jogador : jogadores) {
                if (jogador == null) {
                    break;
                }

                if (jogador.getId().longValue() != id) {
                    novoArray[novoIndice] = jogador;
                    novoIndice++;
                }
            }
            this.campeonato.setJogadores(novoArray);
            return ResponseEntity.ok("Jogador excluído com sucesso");
        } else {
            return ResponseEntity.badRequest().body("ID inválido");
        }
    }

    @PostMapping("/iniciarCampeonato")
    public ResponseEntity<String> iniciarCampeonato() {
        try {
            this.campeonato.iniciarCampeonato();
            return ResponseEntity.ok("Campeonato Inciado!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao iniciar: " + e);
        }
    }

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
}