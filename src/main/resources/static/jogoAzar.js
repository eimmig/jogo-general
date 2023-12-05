const cubes = document.querySelectorAll('.cube');
const rollButton = document.getElementById('roll-button');
const voltarBotao = document.querySelector('#voltarAplicacao');
//vai armazenar o resultado
let results = [];
let results1 = [];

//array de objetos jogador
let players = [];

//controle de jogador na vez
let jogadorDaVez = -1;

//controle de fim do jogo
let maxRodadas = 0;

//DADOS
let rolling = false;
rollButton.addEventListener('click', () => {
    if (!rolling && jogadorDaVez !== -1) {
        rolling = true;
        results = []

        let cubesMoving = cubes.length; // Inicializa com o número total de cubos
        var index = 0;

        if ($('#valorApostaInput').val().length <= 0) {
            toastr.error("Adicione o valor da aposta!")
            rolling = false;
            return;
        }
        axios.post('/controller/salvarAposta', {"valorAposta" : parseFloat($('#valorApostaInput').val()), "jogador" : players[jogadorDaVez].id})
            .then(response => {
                console.log(response.data);
                $('#valorApostaInput').prop('readonly', true);
            })
            .catch(error => {
                console.error(error);
            });

        axios.get('/controller/rolarDados/'+ players[jogadorDaVez].id)
            .then(response => {
                const responseData = response.data;

                cubes.forEach(cube => {
                    cube.style.transition = 'transform 2s ease-in-out';

                    const result = responseData[index];
                    index++;
                    results.push(result);

                    const rotations = 5 + Math.floor(Math.random() * 5);

                    // Gira o dado aleatoriamente antes de parar na face do resultado
                    const randomX = Math.floor(Math.random() * 360);
                    const randomY = Math.floor(Math.random() * 360);

                    setTimeout(() => {
                        cube.style.transform = `rotateX(${randomX}deg) rotateY(${randomY}deg)`;
                    }, 100);

                    setTimeout(() => {
                        cube.style.transform = `rotateX(0deg) rotateY(0deg)`;
                        cube.style.transition = 'transform 0.5s ease-in-out';
                        // Define a face do resultado para cima (substitua pelos seus próprios valores)
                        switch (result) {
                            case 1:
                                cube.style.transform = 'rotateX(0deg) rotateY(0deg)';
                                break;
                            case 2:
                                cube.style.transform = 'rotateX(0deg) rotateY(180deg)';
                                break;
                            case 3:
                                cube.style.transform = 'rotateX(0deg) rotateY(270deg)';
                                break;
                            case 4:
                                cube.style.transform = 'rotateX(0deg) rotateY(90deg)';
                                break;
                            case 5:
                                cube.style.transform = 'rotateX(-90deg) rotateY(0deg)';
                                break;
                            case 6:
                                cube.style.transform = 'rotateX(90deg) rotateY(0deg)';
                                break;
                        }
                        // Verificar se o cubo parou de se mover
                        cube.addEventListener('transitionend', () => {
                            cubesMoving--;
                            if (cubesMoving === 0) {
                                // Todos os cubos pararam de se mover
                                setTimeout(async () => {
                                    // Apresentar os resultados em um único alert
                                    toastr.success("Dados rolados com sucesso!")
                                    rolling = false; // Redefine a variável para permitir futuras rolagens
                                    await calculaVitoriaDerrota()
                                }, 500);
                            }
                        });
                    }, 2000 + rotations * 100); // Tempo adicional com base no número de rotações aleatórias
                });
            })
            .catch(error => {
                console.error('Erro ao fazer a solicitação GET:', error);
            });
    }
});
//FIM DADOS

//CONTROLE JOGADA MAQUINA
const observador = new Proxy({}, {
    set: async function(target, key, value) {
        if (key === "jogadorDaVez") {
            if (players[value].tipo === "M") {
                for (let i = 0; i < 13 && maxRodadas < 12; i++) {
                    $('#valorApostaInput').val((players[value].saldo / 2 >= 1) ? (players[value].saldo / 2) : players[value].saldo);
                    await rollButton.click();
                    await new Promise(resolve => setTimeout(resolve, 5000)); // Aguarda 5 segundos
                }
            }
        }
        target[key] = value;
        return true;
    }
});

//VOLTAR
voltarBotao.addEventListener('click', async () => {

    await axios.post('/controller/atualizarJogador')
        .then(response => {
            console.log(response.data);
        })
        .catch(error => {
            console.error(error);
        });
    window.location.href = "EscolhaJogo.html";
})

//FIM VOLTAR


//INICIO CARREGAR INFORMAÇÕES
document.addEventListener("DOMContentLoaded", async function() {

    await axios.post('/controller/jogoEscolhido', {"jogoEscolhido" : "Azar"})
        .then(response => {
            console.log(response.data);
        })
        .catch(error => {
            console.error(error);
        });



    axios.get('/controller/carregarInformacoes')
        .then(function(response) {
            let jogadores = response.data.jogadores;

            let jogadoresTransformados = jogadores.map(jogador => ({
                id: jogador.id,
                nome: jogador.nome,
                tipo: jogador.tipo,
                saldo: jogador.saldo
            }));

            players.push(...jogadoresTransformados);
            jogadorDaVez = response.data.jogadorDaVez;
            observador.jogadorDaVez = response.data.jogadorDaVez;
        })
        .catch(function(error) {
            // Tratar erros
            console.error('Erro na requisição:', error);
        });
});
//FIM CARREGAR INFORMAÇÕES

// INICIO CALCULA VITORIA DERROTA

async function calculaVitoriaDerrota() {
    if (results) {
        debugger;
        let soma = results[0] + results[1];
        let resultado = 'A'
        if (results1.length == 0 && (soma == 7 || soma ==  11)) {
            toastr.success("Vitória!")
            resultado = 'V'
        } else if (results1.length == 0 && (soma == 2 || soma == 3 || soma == 12)) {
            toastr.success("Derrota!")
            resultado = 'D'
        } else if (resultado == 'A' && results1.length == 0) {
            toastr.success("Segunda Rodada de dados!")
            results1 = results;
            rollButton.click();
        } else {
            if (soma == (results1[0] + results1[1])){
                toastr.success("Vitória!")
                resultado = 'V'
            } else {
                toastr.success("Derrota!")
                resultado = 'D'
            }
        }
        if (resultado != 'A') {
            axios.post('/controller/vitoriaDerrota', {"vitoriaDerrota" : resultado, "jogador" : players[jogadorDaVez].id })
                .then(response => {
                    console.log(response.data);
                    voltarBotao.click();
                })
                .catch(error => {
                    console.error(error);
                });
        }
    }
}

//FIM CALCULA DERROTA VITORIA