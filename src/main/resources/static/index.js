const cubes = document.querySelectorAll('.cube');
const rollButton = document.getElementById('roll-button');
const startButton = document.getElementById('start-button');
const mostrarTabela = document.getElementById('mostrarTabela');
const tabelaContainer = document.getElementById('tabelaContainer');
const modal = document.getElementById('modal');
const openModal = document.getElementById('openModal');
const removePlayerModal = document.getElementById('removePlayerModal');
const openRemovePlayerModal = document.getElementById('openRemovePlayerModal');
const playerSelect = document.getElementById('playerSelect');
const cancelButton = document.querySelector('.modal-button-cancel');
const confirmButton = document.querySelector('.modal-button-confirm');
const confirmButtonRemove = document.querySelector('.modal-button-confirm-remove');
const cancelButtonRemove = document.querySelector('.modal-button-cancel-remove');
const table = document.querySelector('table');
const containerEscolhas = document.querySelector('.container-escolhas');
const escolhaNumero1 = document.querySelector('#escolha-numero-1');
const escolhaNumero2 = document.querySelector('#escolha-numero-2');
const escolhaNumero3 = document.querySelector('#escolha-numero-3');
const escolhaNumero4 = document.querySelector('#escolha-numero-4');
const escolhaNumero5 = document.querySelector('#escolha-numero-5');
const escolhaNumero6 = document.querySelector('#escolha-numero-6');
const escolhaNumeroT = document.querySelector('#escolha-numero-T');
const escolhaNumeroQ = document.querySelector('#escolha-numero-Q');
const escolhaNumeroF = document.querySelector('#escolha-numero-F');
const escolhaNumeroS1 = document.querySelector('#escolha-numero-S+');
const escolhaNumeroS2 = document.querySelector('#escolha-numero-S-');
const escolhaNumeroG = document.querySelector('#escolha-numero-G');
const escolhaNumeroX = document.querySelector('#escolha-numero-X');
const mostrarJogadasExecutadas = document.querySelector('#mostrar-jogadas-executadas');

//vai armazenar o resultado
const results = [];

//array de objetos jogador
var players = [];

//controle de jogador na vez
var jogadorDaVez = -1;

//INICIAR CAMPEONATO
startButton.addEventListener('click', () => {

    if (this.players.length < 1) {
        toastr.error("Inclua os Jogadores Primeiro!")
        return
    }

    axios.post('/controller/iniciarCampeonato')
        .then (response => {
            rollButton.classList.remove('hidden')
            mostrarTabela.classList.remove('hidden')
            startButton.classList.add('hidden')
            toastr.success(response.data)
            jogadorDaVez++;
        })
        .catch(error => {
            console.error('Erro ao fazer a solicitação POST:', error)
        });
})
//FIM INICIAR CAMPEONATO

//DADOS
let rolling = false;
rollButton.addEventListener('click', () => {
    if (!rolling && jogadorDaVez !== -1) {
        rolling = true;

        this.results = []

        let cubesMoving = cubes.length; // Inicializa com o número total de cubos
        var index = 0;

        axios.get('/controller/rolarDados/'+ players[jogadorDaVez].id)
            .then(response => {
                debugger;
                const responseData = response.data;

                cubes.forEach(cube => {
                    cube.style.transition = 'transform 2s ease-in-out';

                    const result = responseData[index];
                    index++;
                    this.results.push(result);

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
                        debugger;
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

function addRowsToTable() {
    for (let i = 0; i < 13; i++) {
        const newRow = document.createElement('tr');
        for (let j = 0; j < table.rows[0].cells.length; j++) {
            const newCell = document.createElement('td');
            newRow.appendChild(newCell);
        }
        table.querySelector('tbody').appendChild(newRow);
    }
}

mostrarTabela.addEventListener('click', () => {
    if (tabelaContainer.classList.contains('transition-fade-out')) {
        tabelaContainer.classList.remove('transition-fade-out');
        tabelaContainer.style.display = 'block';
        mostrarTabela.innerHTML = '<i class="fa-solid fa-eye-slash" style="color: #000000;"></i> Ocultar cartela de resultados';
    } else {
        tabelaContainer.classList.add('transition-fade-out');
        mostrarTabela.innerHTML = '<i class="fa-solid fa-table-list" style="color: #000000;"></i> Cartela de resultados';
    }
});

//FIM CARTELA RESULTADOS

//MODAL ADICIONAR PLAYERS
openModal.addEventListener('click', () => {
    modal.style.display = 'block';
});

cancelButton.addEventListener('click', () => {
    modal.style.display = 'none';
});

window.addEventListener('click', (event) => {
    if (event.target === modal) {
        modal.style.display = 'none';
    }
});

confirmButton.addEventListener('click', () => {
    const data = {
        nome: $('#nome-jogador').val(),
        tipo: $('#tipo-jogador').val()
    };
    axios.post('controller/incluirJogador', data)
        .then(response => {
            let jogadorCriado = {
                id: response.data.id,
                nome: response.data.nome,
                tipo: response.data.tipo,
            };
            players.push(jogadorCriado);
            //adiciona o player na lista pra remover
            const option = document.createElement('option');
            option.value = jogadorCriado.id;
            option.textContent = jogadorCriado.nome;
            playerSelect.appendChild(option);
            //adiciona ao cabeçalho da tabela de resultado
            addColumnToHeader(response.data.nome)
            //mostra o toastr de sucesso
            toastr.success(response.data.message);
            modal.style.display = 'none';
        })
        .catch(error => {
            toastr.error(error.data);
            modal.style.display = 'none';
        });
});
//FIM MODAL ADICIONAR PLAYERS

//MODAL REMOVER PLAYERS
players.forEach(player => {
    const option = document.createElement('option');
    option.value = player.id;
    option.textContent = player.nome;
    playerSelect.appendChild(option);
});

openRemovePlayerModal.addEventListener('click', () => {
    removePlayerModal.style.display = 'block';
});

cancelButtonRemove.addEventListener('click', () => {
    removePlayerModal.style.display = 'none';
});

window.addEventListener('click', (event) => {
    if (event.target === removePlayerModal) {
        removePlayerModal.style.display = 'none';
    }
});

confirmButtonRemove.addEventListener('click', () => {
    debugger;
    const idJogador = $('#playerSelect').val();
    axios.delete(`/controller/remover/${idJogador}`)
        .then((response) => {
            this.removerOpcaoPorId(idJogador);
            toastr.success(response.data);
        })
        .catch((error) => {
            console.error('Erro ao excluir:', error);
        });
});

function removerOpcaoPorId(id) {
    const playerSelect = document.getElementById('playerSelect'); // Obtenha o elemento select

    for (let i = 0; i < playerSelect.options.length; i++) {
        if (playerSelect.options[i].value === id.toString()) {
            playerSelect.remove(i); // Remove a opção com o valor correspondente ao ID
            break;
        }
    }
}
//FIM MODAL REMOVER PLAYERS

//SAIR DA APLICAÇÃO
document.getElementById('fecharAplicacao').addEventListener('click', () => {
    axios.post('/controller/exit')
        .then(response => {
            console.log(response.data);
        })
        .catch(error => {
            console.error(error);
        });
    toastr.success("A aplicação será finalizada em alguns segundos!");
});
//FIM SAIR DA APLICAÇÃO

//FAZER JOGADA
escolhaNumero1.addEventListener('click', () => {
    fazerJogada(1)
})
escolhaNumero2.addEventListener('click', () => {
    fazerJogada(2)
})
escolhaNumero3.addEventListener('click', () => {
    fazerJogada(3)
})
escolhaNumero4.addEventListener('click', () => {
    fazerJogada(4)
})
escolhaNumero5.addEventListener('click', () => {
    fazerJogada(5)
})
escolhaNumero6.addEventListener('click', () => {
    fazerJogada(6)
})
escolhaNumeroT.addEventListener('click', () => {
    fazerJogada(7)
})
escolhaNumeroF.addEventListener('click', () => {
    fazerJogada(8)
})
escolhaNumeroQ.addEventListener('click', () => {
    fazerJogada(9)
})
escolhaNumeroS1.addEventListener('click', () => {
    fazerJogada(10)
})
escolhaNumeroS2.addEventListener('click', () => {
    fazerJogada(11)
})
escolhaNumeroX.addEventListener('click', () => {
    fazerJogada(12)
})
escolhaNumeroG.addEventListener('click', () => {
    fazerJogada(13)
})
function fazerJogada (opcao) {
    axios.post('/controller/executarJogada', {"opcao" : opcao, "jogador" : players[jogadorDaVez].id, "dados" : this.results})
        .then(response => {
            console.log(response.data);
            toastr.success(response.data)
            containerEscolhas.classList.add('hidden')
        })
        .catch(error => {
            console.error(error);
        });

    if (jogadorDaVez+1 > players.length-1) {
        jogadorDaVez = 0
    } else {
        jogadorDaVez++
    }
}

//FIM FAZER JOGADA

//MOSTRAR JOGADAS EXECUTADAS
mostrarJogadasExecutadas.addEventListener('click', () => {
    axios.get('/controller/mostrarJogadas/'+ players[jogadorDaVez].id)
        .then(response => {
            console.log(response.data);
            toastr.success(response.data)
        })
        .catch(error => {
            console.error(error);
        });
})
//FIM MOSTRAR JOGADAS EXECUTADAS