const cubes = document.querySelectorAll('.cube');
const rollButton = document.getElementById('roll-button');
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

//array de objetos jogador
var players = [];


//DADOS
let rolling = false;
rollButton.addEventListener('click', () => {
    if (!rolling) {
        rolling = true;

        const results = [];

        let cubesMoving = cubes.length; // Inicializa com o número total de cubos
        var index = 0;

        axios.get('/controller/rolarDados')
            .then(response => {
                debugger;
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
function addColumnToHeader() {
    const headerRow = table.querySelector('thead tr');
    const newColumnHeader = prompt('Digite o título da nova coluna:');

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