Executando um Projeto Spring com o Maven
========================================

Pré-requisitos
--------------

*   [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html): Certifique-se de ter o JDK instalado em sua máquina.
*   [Maven](https://maven.apache.org/download.cgi): Certifique-se de ter o Maven instalado.
*   [IntelliJ IDEA](https://www.jetbrains.com/idea/download/): Faça o download e instale o IntelliJ IDEA.

Passos para Executar o Projeto
------------------------------

1.  **Clone o Repositório**: Clone o repositório do projeto em seu ambiente local. (No caso da avaliação o projeto está comprimido em um .zip)

    git clone https://github.com/eimmig/jogo-general.git

3.  **Abra o Projeto no IntelliJ IDEA (recomendo abrir nessa IDE se possível, pois o IntelliJ IDEA detecta o maven e já configura tudo automaticamente)**:

*   Abra o IntelliJ IDEA.
*   Clique em "File" -> "Open" e navegue até a pasta onde você clonou o projeto.
*   Selecione a pasta do projeto e clique em "Open".

5.  **Importe o Projeto como um Projeto Maven**:

*   O IntelliJ IDEA deve detectar automaticamente o arquivo `pom.xml` e sugerir importar o projeto como um projeto Maven.
*   Certifique-se de que todas as dependências do Maven são baixadas e configuradas corretamente.

7.  **Configure o JDK**:

*   Vá para "File" -> "Project Structure".
*   Na seção "Project", verifique se o JDK apropriado está selecionado. Caso contrário, adicione o JDK corretamente. (O projeto foi feito na JDK 20)

9.  **Execute o Projeto**:

*   Encontre o arquivo principal do aplicativo Spring (geralmente uma classe com a anotação `@SpringBootApplication`), nesse caso está como UsaCampeonato.
*   Clique com o botão direito do mouse no arquivo e escolha "Run" para iniciar o aplicativo Spring.

11.  **Acesse o Aplicativo**:

*   Após iniciar o aplicativo com sucesso, abra um navegador e acesse [http://localhost:8080](http://localhost:8080) (ou a porta em que o aplicativo está configurado para ser executado).
*   Deixei implementado para que o algoritmo detecte o sistema operacional do usuário e abra automaticamente o navegador padrão já na url.

13.  **Encerre a Execução**:

*   Para encerrar a execução do aplicativo no IntelliJ IDEA, pressione `Ctrl+C` no terminal em que o aplicativo está em execução.
*   Ou no botão vermelho do IntelliJ IDEA.
