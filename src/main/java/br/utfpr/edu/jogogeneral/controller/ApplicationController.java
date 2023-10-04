package br.utfpr.edu.jogogeneral.controller;

import br.utfpr.edu.jogogeneral.model.Dado;
import br.utfpr.edu.jogogeneral.model.Jogador;
import br.utfpr.edu.jogogeneral.model.JogoGeneral;
import br.utfpr.edu.jogogeneral.ultils.CustomResponse;
import br.utfpr.edu.jogogeneral.ultils.RequestPessoa;
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
    public ResponseEntity<CustomResponse<Jogador>> incluirJogadores(@RequestBody RequestPessoa pessoa) {
        Jogador jogador = new Jogador(pessoa.getNome(), pessoa.getTipo(), this.jogo, this.campeonato.getNumJogadores());
        this.campeonato.incluirJogador(jogador);
        CustomResponse<Jogador> responseObject = new CustomResponse<>(jogador, "Jogador incluído com sucesso");
        return new ResponseEntity<>(responseObject, HttpStatus.OK);
    }
}