#!/bin/bash

# Olá usuário. Para rodar o seguinte arquivo será preciso realizar 2 passos.
# O primeiro é  dentro da pasta atual do projeto (jogo-general) utilizar o seguinte comando:
#
# sudo chmod -x iniciarAplicação.sh      ( esse comando da permissão ao ubuntu para executar o aplicativo)
# posteriormente será preciso utilizar esse comando para iniciar: 
#
# sh iniciarAplicação.sh


# Função para verificar se o comando está disponívelcc
command_exists() {
  command -v "$1" >/dev/null 2>&1
}

# Verificar se o Java 17 está instalado
if command_exists java && [ "$(java -version 2>&1 | awk 'NR==1 {print $3}' | sed 's/"//g')" == "17" ]; then
  echo "Java 17 já está instalado. Pulando a instalação."
else
  # Instalar o OpenJDK 17
  echo "Instalando o OpenJDK 17..."
  sudo apt update
  sudo apt install -y openjdk-17-jdk openjdk-17-jre

  # Configurar o Java 17 como padrão
  echo "Configurando o Java 17"
  sudo update-alternatives --set java /usr/lib/jvm/java-17-openjdk-amd64/bin/java
  sudo update-alternatives --set javac /usr/lib/jvm/java-17-openjdk-amd64/bin/javac

  # Configurar as variáveis de ambiente
  echo "Configurando as variáveis de ambiente..."
  echo "export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64" >> ~/.bashrc
  echo "export PATH=\$PATH:/usr/lib/jvm/java-17-openjdk-amd64/bin" >> ~/.bashrc

  # Atualizar as variáveis de ambiente para o script atual
  export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
  export PATH=$PATH:/usr/lib/jvm/java-17-openjdk-amd64/bin
fi

# Verificar se o Maven está instalado
if command_exists mvn; then
  echo "Maven já está instalado. Pulando a instalação."
else
  # Baixar e instalar o Maven
  echo "Instalando o Maven..."
  wget https://downloads.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz
  tar -xzf apache-maven-3.8.4-bin.tar.gz
  sudo mv apache-maven-3.8.4 /opt/maven

  # Configurar o Maven como padrão
  echo "Configurando o Maven como padrão..."
  echo "export MAVEN_HOME=/opt/maven" >> ~/.bashrc
  echo "export PATH=\$PATH:/opt/maven/bin" >> ~/.bashrc

  # Atualizar as variáveis de ambiente para o script atual
  export MAVEN_HOME=/opt/maven
  export PATH=$PATH:/opt/maven/bin
fi

# Executar o Maven para construir e iniciar o aplicativo
echo "Executando o Maven para construir e iniciar o aplicativo..."
mvn clean install
mvn spring-boot:run

