const cubes = document.querySelectorAll('.cube');
const rollButton = document.getElementById('roll-button');
const table = document.querySelector('table');
const containerEscolhas = document.querySelector('.container-escolhas');
const escolhaNumero1 = document.querySelector('#escolha-numero-1');
const escolhaNumero2 = document.querySelector('#escolha-numero-2');
const escolhaNumero3 = document.querySelector('#escolha-numero-3');
const escolhaNumero4 = document.querySelector('#escolha-numero-4');
const escolhaNumero5 = document.querySelector('#escolha-numero-5');
const escolhaNumero6 = document.querySelector('#escolha-numero-6');
const escolhaNumeroT = document.querySelector('#escolha-numero-7');
const escolhaNumeroQ = document.querySelector('#escolha-numero-8');
const escolhaNumeroF = document.querySelector('#escolha-numero-9');
const escolhaNumeroS1 = document.querySelector('#escolha-numero-10');
const escolhaNumeroS2 = document.querySelector('#escolha-numero-11');
const escolhaNumeroG = document.querySelector('#escolha-numero-12');
const escolhaNumeroX = document.querySelector('#escolha-numero-13');
const mostrarJogadasExecutadas = document.querySelector('#mostrar-jogadas-executadas');
const topLogo = document.querySelector('.top-logo');
const voltarBotao = document.querySelector('#voltarAplicacao');
//vai armazenar o resultado
let results = [];

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

        if ($('#valorApostaInput').val().length === 0) {
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
                                setTimeout(() => {
                                    // Apresentar os resultados em um único alert
                                    toastr.success("Dados rolados com sucesso!")
                                    rolling = false; // Redefine a variável para permitir futuras rolagens
                                    containerEscolhas.classList.remove('hidden')
                                    mostrarJogadasExecutadas.classList.remove('hidden')
                                    topLogo.classList.add('hidden')

                                    if (maxRodadas === 12) {
                                        rollButton.classList.add('hidden')
                                    } else {
                                        maxRodadas++;
                                    }

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

//CARTELA RESULTADOS
function addColumnToHeader(newColumnHeader) {
    const headerRow = table.querySelector('thead tr');

    if (newColumnHeader) {
        const newHeaderCell = document.createElement('th');
        newHeaderCell.textContent = newColumnHeader;
        headerRow.appendChild(newHeaderCell);
    }
}

function addRowsToTable(jogada) {
    let soma = 0
    for (let i = 0; i <= 13; i++) {
        const newRow = document.getElementById("tr-" + i);
        const newCell = document.createElement('td');
        if (i === 13) {
            newCell.innerHTML = soma
        } else {
            newCell.innerHTML = jogada.jogadas[i]
            soma += jogada.jogadas[i]
        }
        newCell.classList.add("filho")
        newRow.appendChild(newCell);
    }
}

//FAZER JOGADA
escolhaNumero1.addEventListener('click', () => {
    fazerJogada(0)
})
escolhaNumero2.addEventListener('click', () => {
    fazerJogada(1)
})
escolhaNumero3.addEventListener('click', () => {
    fazerJogada(2)
})
escolhaNumero4.addEventListener('click', () => {
    fazerJogada(3)
})
escolhaNumero5.addEventListener('click', () => {
    fazerJogada(4)
})
escolhaNumero6.addEventListener('click', () => {
    fazerJogada(5)
})
escolhaNumeroT.addEventListener('click', () => {
    fazerJogada(6)
})
escolhaNumeroF.addEventListener('click', () => {
    fazerJogada(7)
})
escolhaNumeroQ.addEventListener('click', () => {
    fazerJogada(8)
})
escolhaNumeroS1.addEventListener('click', () => {
    fazerJogada(9)
})
escolhaNumeroS2.addEventListener('click', () => {
    fazerJogada(10)
})
escolhaNumeroG.addEventListener('click', () => {
    fazerJogada(11)
})
escolhaNumeroX.addEventListener('click', () => {
    fazerJogada(12)
})

function fazerJogada (opcao) {
    axios.post('/controller/executarJogada', {"opcao" : opcao, "jogador" : players[jogadorDaVez].id, "dados" : results})
        .then(response => {
            console.log(response.data);
            toastr.success(response.data)
            containerEscolhas.classList.add('hidden')
            mostrarJogadasExecutadas.classList.add('hidden')
            topLogo.classList.remove('hidden')
            if (maxRodadas === 12) {
                debugger;
                calculaVitoriaDerrota();
            }
        })
        .catch(error => {
            console.error(error);
        });
}
//FIM FAZER JOGADA

//MOSTRAR JOGADAS EXECUTADAS
mostrarJogadasExecutadas.addEventListener('click', () => {
    axios.get('/controller/mostrarJogadas/'+ players[jogadorDaVez].id)
        .then(response => {
            console.log(response.data);
            dados = response.data
            $('#escolha1').val(dados[0])
            $('#escolha2').val(dados[1])
            $('#escolha3').val(dados[2])
            $('#escolha4').val(dados[3])
            $('#escolha5').val(dados[4])
            $('#escolha6').val(dados[5])
            $('#escolha7').val(dados[6])
            $('#escolha8').val(dados[7])
            $('#escolha9').val(dados[8])
            $('#escolha10').val(dados[9])
            $('#escolha11').val(dados[10])
            $('#escolha12').val(dados[11])
            $('#escolha13').val(dados[12])
            toastr.success("Jogadas Recuperadas com sucesso!")
            document.getElementById("modal-escolhas").style.display = "block";
        })
        .catch(error => {
            console.error(error);
        });
})
document.getElementById("closeModalBtn").addEventListener("click", function() {
    document.getElementById("modal-escolhas").style.display = "none";
});
//FIM MOSTRAR JOGADAS EXECUTADAS

//CONTROLE JOGADA MAQUINA
const observador = new Proxy({}, {
    set: async function(target, key, value) {
        if (key === "jogadorDaVez") {
            if ((maxRodadas === 12)) {
                rollButton.classList.add('hidden')
                return
            }
            if (players[value].tipo === "M") {
                toastr.info("Agora é vez da Maquina Jogar!")
                await rollButton.click();
                setTimeout(async function () {
                    await jogadaMaquina();
                }, 5000)
            } else {
                toastr.info("Agora é vez de " + players[value].nome + " Jogar!")
            }
        }
        target[key] = value;
        return true;
    }
});

async function jogadaMaquina() {
    await axios.post('/controller/jogadaMaquina', {"opcao" : 1, "jogador" : players[jogadorDaVez].id, "dados" : results})
        .then(response => {
            console.log(response.data);
            toastr.success(response.data)
            containerEscolhas.classList.add('hidden')
            mostrarJogadasExecutadas.classList.add('hidden')
            topLogo.classList.remove('hidden')
        })
        .catch(error => {
            console.error(error);
        });
}
//FIM CONTROLE JOGADA MAQUINA


//VOLTAR
voltarBotao.addEventListener('click', () => {
    window.location.href = "index.html";
})

//FIM VOLTAR


//INICIO CARREGAR INFORMAÇÕES
document.addEventListener("DOMContentLoaded", async function() {

    await axios.post('/controller/jogoEscolhido', {"jogoEscolhido" : "General"})
        .then(response => {
            console.log(response.data);
        })
        .catch(error => {
            console.error(error);
        });



    axios.get('/controller/carregarInformacoes')
        .then(function(response) {
            debugger;
            let jogadores = response.data.jogadores;

            let jogadoresTransformados = jogadores.map(jogador => ({
                id: jogador.id,
                nome: jogador.nome,
                tipo: jogador.tipo,
            }));

            players.push(...jogadoresTransformados);
            jogadorDaVez = response.data.jogadorDaVez;
        })
        .catch(function(error) {
            // Tratar erros
            console.error('Erro na requisição:', error);
        });
});
//FIM CARREGAR INFORMAÇÕES

// INICIO CALCULA VITORIA DERROTA

async function calculaVitoriaDerrota() {
    debugger;
    await axios.get('/controller/mostrarJogadas/'+ players[jogadorDaVez].id)
        .then(response => {
            console.log(response.data);
            dados = response.data
            let soma = dados[0] +
                       dados[1] +
                       dados[2] +
                       dados[3] +
                       dados[4] +
                       dados[5] +
                       dados[6] +
                       dados[7] +
                       dados[8] +
                       dados[9] +
                       dados[10] +
                       dados[11];
            debugger;
            if (soma > (dados[12] * 2)) {
                toastr.success("Vitória!")
            } else {
                toastr.success("Derrota!")
            }


        })
        .catch(error => {
            console.error(error);
        });
}

//FIM CALCULA DERROTA VITORIA