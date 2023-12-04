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
let mostrouTabela = false;

//array de objetos jogador
let players = [];

//INICIAR CAMPEONATO
startButton.addEventListener('click', () => {

    if (players.length < 1) {
        toastr.error("Inclua os Jogadores Primeiro!")
        return
    }

    axios.post('/controller/iniciarCampeonato')
        .then (response => {
            debugger;
            startButton.classList.add('hidden')
            toastr.success(response.data)
            window.location.href='EscolhaJogo.html'
        })
        .catch(error => {
            console.error('Erro ao fazer a solicitação POST:', error)
        });

})
//FIM INICIAR CAMPEONATO

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
        const newRow = document.getElementById("tr-"+i);
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
mostrarTabela.addEventListener('click', async () => {
    if (tabelaContainer.classList.contains('transition-fade-out')) {
        topLogo.classList.add('hidden')

        if (!mostrouTabela) {
            await axios.get('/controller/mostrarCartelaFinal/')
                .then(response => {
                    console.log(response)
                    let jogadas = response.data.jogadas
                    jogadas.forEach(jogada => {
                        if (jogada) {
                            addRowsToTable(jogada)
                            mostrouTabela = true
                        }
                    })
                })
                .catch(error => {
                    console.log(error)
                });
            }
        tabelaContainer.classList.remove('transition-fade-out');
        tabelaContainer.style.display = 'block';
        mostrarTabela.innerHTML = '<i class="fa-solid fa-eye-slash" style="color: #000000;"></i> Ocultar cartela de resultados';
    } else {
        topLogo.classList.remove('hidden')
        tabelaContainer.classList.add('transition-fade-out');
        mostrarTabela.innerHTML = '<i class="fa-solid fa-table-list" style="color: #000000;"></i> Cartela de resultados';
    }
});
//FIM CARTELA RESULTADOS

//MODAL ADICIONAR PLAYERS
openModal.addEventListener('click', () => {
    debugger;
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
        cpf: $('#cpf-jogador').val(),
        agencia: $('#agencia-jogador').val(),
        conta: $('#conta-jogador').val(),
        numeroBanco: $('#numero-jogador').val(),
        tipo: $('#tipo-jogador').val()
    };
    axios.post('controller/incluirJogador', data)
        .then(response => {
            if (response.data.id != -1) {
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
            } else {
                toastr.error(response.data.message)
            }
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


document.getElementById('tipo-jogador').addEventListener('change', () => {
    if ($('#tipo-jogador').val() === 'M') {
        $('#cpf-jogador').addClass('hidden');
        $('#agencia-jogador').addClass('hidden');
        $('#conta-jogador').addClass('hidden');
        $('#numero-jogador').addClass('hidden');
    } else {
        $('#cpf-jogador').removeClass('hidden');
        $('#agencia-jogador').removeClass('hidden');
        $('#conta-jogador').removeClass('hidden');
        $('#numero-jogador').removeClass('hidden');
    }
})