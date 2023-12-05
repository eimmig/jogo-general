let jogadorDaVez = -1;
let players = []


const observador = new Proxy({}, {
    set: async function(target, key, value) {
        if (key === "jogadorDaVez") {
            if (players[value].tipo === "M") {
                setTimeout(function() {
                    if (Math.floor(Math.random() * 2) + 1 === 1) {
                        $('#botao-general').click();
                    } else {
                        $('#botao-azar').click();
                    }
                }, 5000);
            }
        }
        target[key] = value;
        return true;
    }
});


document.addEventListener("DOMContentLoaded", async function() {
    axios.get('/controller/carregarInformacoes')
        .then(async function(response) {
            let jogadores = response.data.jogadores;

            let jogadoresTransformados = jogadores.map(jogador => ({
                id: jogador.id,
                nome: jogador.nome,
                tipo: jogador.tipo,
                saldo: jogador.saldo,
                numeroJogos : jogador.numeroJogos
            }));

            players.push(...jogadoresTransformados);

            jogadorDaVez = response.data.jogadorDaVez;
            observador.jogadorDaVez = response.data.jogadorDaVez;

            $("#jogador-escolha-jogo").text(players[jogadorDaVez].nome);
            $("#saldo-escolha-jogo").text("Saldo de: R$" + players[jogadorDaVez].saldo);

            if (players[jogadorDaVez].saldo <= 0 || players[jogadorDaVez].numeroJogos === 10) {
                players.forEach(player => {
                    if (player.saldo <= 0 || player.numeroJogos === 10) {
                        window.location.reload("index.html");
                    }
                    toastr.error("Jogador sem saldo ou com limite de apostas atingido.");
                    axios.post('/controller/atualizarJogador')
                        .then(response => {
                            console.log(response.data);
                            window.location.reload();
                        })
                        .catch(error => {
                            console.error(error);
                        });
                });
            }
        })
        .catch(function(error) {
            // Tratar erros
            console.error('Erro na requisição:', error);
        });
});