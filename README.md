# Trabalho Banco de dados
- `Exemplo de codigo feito em JAVA para um sistema de gerenciamento de Controle de Estoque!`

Este projeto consiste no desenvolvimento de uma aplicação de console em Java para simular um sistema de controle de estoque. O sistema permite o gerenciamento de produtos e suas respectivas movimentações (entradas e saídas), interagindo diretamente com um banco de dados MySQL. A comunicação com o banco de dados é realizada utilizando JDBC puro, sem o auxílio de frameworks ORM, conforme especificado nos requisitos. O objetivo é aplicar os conceitos de modelagem relacional, linguagem SQL e integração entre aplicação e banco de dados.

# 👨‍🏫 Disciplina e Professor

- Disciplina: `Banco de Dads`
- Professor: `Howard Roatti`
## 👥 Integrantes do Grupo

- `Gabriel Dazilio Fanchiotti`
- `Victor Castro`

## ✨ Funcionalidades Implementadas

O sistema oferece as seguintes funcionalidades através de um menu interativo no console:

- `📊 Relatórios:`
  - Listagem de todas as movimentações registradas (não excluídas logicamente), exibindo detalhes do produto associado (Nome e ID). (Consulta com Junção implícita via DAO)
  - Relatório de total de itens movimentados, agrupado por tipo (ENTRADA/SAIDA). (Consulta com Agrupamento/Sumarização)
- `➕ Inserir Registro:`
  - Sub-menu para escolher entre inserir Produto ou Movimentação
  - Cadastro de novos Produtos (nome, descrição, estoque inicial)
  - Cadastro de novas Movimentações (associando a um produto existente, tipo, quantidade), com atualização automática do estoque do produto
  - Loop opcional para inserir múltiplos registros ("Deseja continuar?")
- `➖ Remover Registro:`
  - Sub-menu para escolher entre remover Produto ou Movimentação
  - Listagem dos registros existentes (ID e Nome/Tipo) para seleção
  - Implementação de "soft delete" (atualiza o campo sr_deleted para 'T') em vez de exclusão física
  - Verificação de chave estrangeira: impede a remoção de um Produto se ele possuir movimentações associadas
  - Confirmação do usuário ("Tem certeza? S/N") antes de efetivar a remoção
  - Loop opcional para remover múltiplos registros ("Deseja continuar?")
- `🔄 Atualizar Registro:`
  - Sub-menu para escolher entre atualizar Produto ou Movimentação
  - Listagem dos registros existentes (ID e Nome/Tipo) para seleção
  - Permite a alteração dos dados de Produtos (nome, descrição, estoque)
  - Permite a alteração dos dados de Movimentações (produto associado, tipo, quantidade)
  - Exibição do registro com os dados atualizados após a operação
  - Loop opcional para atualizar múltiplos registros ("Deseja continuar?")
- `🖥️ Splash Screen:` Exibição inicial com nome do sistema, integrantes e contagem atual de registros nas tabelas produtos e movimentacao

## 🛠️ Tecnologias Utilizadas

- Linguagem: Java (JDK 17+)
- Banco de Dados: MySQL 8.0+
- Conectividade: JDBC (via MySQL Connector/J 9.4.0)
- Controle de Versão: Git / GitHub
- IDE: IntelliJ IDEA

## Demonstração

Aqui vou deixar o Link do video mostrando todas as funcionalidades do projeto que gravei, editei e postei no YouTube.

https://www.youtube.com/watch?v=-1Xr28ZH_xU

## 💾 Configuração do Ambiente (Linux)

Para compilar e executar este projeto em um ambiente Linux, você precisará dos seguintes componentes instalados:

### 1. Java Development Kit (JDK)

* **Verificação:** Abra o terminal e digite `java -version`. Você precisa de uma versão 17 ou superior.
* **Instalação (Debian/Ubuntu):**
    ```bash
    sudo apt update
    sudo apt install openjdk-17-jdk 
    ```
* **Instalação (Fedora/CentOS/RHEL):**
    ```bash
    sudo dnf update 
    sudo dnf install java-17-openjdk-devel 
    ```
    *(Ajuste o comando `dnf` para `yum` em versões mais antigas do CentOS/RHEL)*

### 2. MySQL Server

* **Verificação:** Tente conectar ao servidor local com `mysql -u root -p`.
* **Instalação (Debian/Ubuntu):**
    ```bash
    sudo apt update
    sudo apt install mysql-server
    sudo mysql_secure_installation # Siga as instruções para definir a senha do root
    sudo systemctl start mysql     # Inicia o serviço
    sudo systemctl enable mysql    # Faz o serviço iniciar com o sistema
    ```
* **Instalação (Fedora/CentOS/RHEL):**
    ```bash
    sudo dnf update
    sudo dnf install mysql-community-server # Ou o pacote equivalente na sua distro
    sudo systemctl start mysqld
    sudo systemctl enable mysqld
    # Procure pela senha temporária do root no log, se aplicável, e redefina-a.
    # Exemplo: sudo grep 'temporary password' /var/log/mysqld.log
    #          mysql_secure_installation 
    ```
* **Criar Schema e Tabelas:** Após instalar e iniciar o servidor, conecte-se a ele (`mysql -u root -p`) e execute o script SQL fornecido no repositório para criar o schema (`controle_estoque_novo`) e as tabelas (`produtos`, `movimentacao`).

### 3. MySQL Connector/J (Driver JDBC)

* **Download:** Baixe o arquivo `.jar` do MySQL Connector/J (Platform Independent ZIP Archive) do site oficial do MySQL. A versão utilizada no desenvolvimento foi a 9.4.0.
* **Organização:** Crie uma pasta chamada `lib` na raiz do seu projeto e coloque o arquivo `.jar` dentro dela.

### 4. Código Fonte do Projeto

* **Clone o Repositório:** Use `git clone` para baixar o código do GitHub para sua máquina Linux.

### 5. Verificar Credenciais no Código

* **Abra o Arquivo:** Edite o arquivo `src/utils/conexaoDB.java`.
* **Ajuste:** Verifique se a `URL` (apontando para `controle_estoque_novo`), `USER` (`root`) e `PASSWORD` (`senhasupersecreta` ou a que você definiu) estão corretos para o seu ambiente MySQL.

## 🚀 Como Compilar e Executar o Projeto (Linux)

Siga estas etapas no terminal Linux, **estando dentro do diretório raiz do projeto** (ex: `~/DEV/ControleEstoque`):

1.  **Compile as Classes Java:**
    Crie o diretório de saída (`out`) se ele não existir e compile todos os arquivos `.java`, incluindo o driver JDBC no classpath (`-cp`).
    ```bash
    mkdir -p out
    javac -cp "lib/mysql-connector-j-9.4.0.jar:." -d out src/*.java src/dao/*.java src/entidades/*.java src/utils/*.java
    ```
    *(**Importante:** Substitua `mysql-connector-j-9.4.0.jar` pelo nome **exato** do seu arquivo `.jar` na pasta `lib`. O `:` separa os caminhos no classpath do Linux)*

2.  **Execute a Classe Principal (`Main`):**
    Execute a classe `Main`, incluindo a pasta `out` (onde estão os arquivos `.class`) e o driver JDBC no classpath.
    ```bash
    java -cp "lib/mysql-connector-j-9.4.0.jar:out:." Main
    ```
    *(**Importante:** Use o mesmo nome exato do arquivo `.jar`)*

3.  **Interaja com o Sistema:**
    O menu principal será exibido no console. Siga as instruções na tela para usar as funcionalidades do sistema de controle de estoque.


## Autores

- [@Dazilio-Gabriel](https://github.com/Dazilio-Gabriel)
- [@Victor-Castro]()
