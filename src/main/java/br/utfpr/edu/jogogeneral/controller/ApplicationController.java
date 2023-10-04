package br.utfpr.edu.jogogeneral.controller;

import br.utfpr.edu.jogogeneral.model.Dado;
import br.utfpr.edu.jogogeneral.model.Jogador;
import br.utfpr.edu.jogogeneral.model.JogoGeneral;
import br.utfpr.edu.jogogeneral.ultils.JogadorDTO;
import br.utfpr.edu.jogogeneral.ultils.IncluirJogadorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/rolarDados")
    public ResponseEntity<int[]> rolarDados() {
        Dado[] dados = this.jogo.rolarDados();

        int[] valores = {dados[0].getSideUp(), dados[1].getSideUp(), dados[2].getSideUp(), dados[3].getSideUp(), dados[4].getSideUp()};

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

            for (int i = 0; (i < jogadores.length); i++) {
                if (jogadores[i] == null) {
                    break;
                }

                if (jogadores[i].getId().longValue() != id) {
                    novoArray[novoIndice] = jogadores[i];
                    novoIndice++;
                }
            }
            this.campeonato.setJogadores(novoArray);
            return ResponseEntity.ok("Item excluído com sucesso");
        } else {
            return ResponseEntity.badRequest().body("ID inválido");
        }
    }
}