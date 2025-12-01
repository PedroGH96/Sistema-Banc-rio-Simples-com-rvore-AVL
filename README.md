# Sistema Bancário Simples com Árvore AVL

Este projeto é uma adaptação do sistema bancário original para atender aos requisitos da **Atividade da Unidade 3** da disciplina de Estruturas de Dados.

A principal modificação foi a substituição da estrutura de dados de armazenamento de clientes (`ArrayList`) por uma **Árvore AVL** (`AVLTree`), garantindo o balanceamento automático e a complexidade de tempo $O(\log n)$ para as operações de busca, inserção e remoção de clientes.

## Requisitos da Atividade Atendidos

| Requisito | Status | Detalhes da Implementação |
| :--- | :--- | :--- |
| **1. CRUD em 5 Entidades** | ✅ Atendido | O sistema possui 5 entidades principais com operações CRUD: `Cliente`, `Conta`, `ContaCorrente`, `ContaPoupanca` e `BancoServico` (que atua como entidade de transação/serviço). |
| **2. Utilizar Estrutura de Árvore (AVL)** | ✅ Atendido | A estrutura `AVLTree` foi implementada do zero em Java, com métodos para cálculo de altura, fator de balanceamento e as quatro rotações (LL, RR, LR, RL). |
| **3. Inserção, Busca e Remoção na Árvore** | ✅ Atendido | As operações de `adicionar`, `buscarPorCpf` e `remover` de clientes no `RepositorioClientes` utilizam a lógica da `AVLTree`. |
| **4. Estrutura com Sentido para o Sistema** | ✅ Atendido | A AVL é utilizada para armazenar os **Clientes**, usando o **CPF** como chave de ordenação. Isso otimiza a busca por clientes, uma operação central em um sistema bancário. |
| **5. Visualização da Estrutura da Árvore** | ✅ Atendido | Foi adicionada a opção **"11. Visualizar AVL (Clientes)"** no menu, que exibe a representação hierárquica da árvore, mostrando o CPF, o Fator de Balanceamento (FB) e a Altura de cada nó. |
| **6. Linguagem** | ✅ Atendido | O projeto está implementado em **Java**. |
| **7. Diagrama UML (Mermaid)** | ✅ Atendido | O código Mermaid para o diagrama de classes está no arquivo `diagrama.md`. |

## Como Rodar a Aplicação

O projeto foi desenvolvido em Java e pode ser executado a partir da linha de comando ou de uma IDE (como IntelliJ IDEA ou Eclipse).

### 1. Pré-requisitos

*   Java Development Kit (JDK) 8 ou superior.

### 2. Execução via Linha de Comando

1.  **Navegue** até a pasta `src`:
    ```bash
    cd /home/ubuntu/banco/src
    ```

2.  **Compile** os arquivos Java:
    ```bash
    javac -d ../out $(find . -name "*.java")
    ```

3.  **Execute** a classe principal (`Main.java`):
    ```bash
    java -cp ../out Main
    ```

### 3. Funcionalidades para Teste

Após iniciar a aplicação, você pode testar as seguintes funcionalidades que demonstram a AVL:

1.  **Cadastrar Cliente (Opção 1):** Insira vários clientes com CPFs em ordem aleatória para ver a AVL se auto-balancear.
2.  **Remover Cliente (Opção 10):** Remova um cliente, o que acionará a lógica de remoção e rebalanceamento da AVL.
3.  **Visualizar AVL (Opção 11):** Use esta opção para ver a representação hierárquica da árvore de clientes, confirmando que o Fator de Balanceamento (FB) de cada nó está entre -1 e 1.

---

## Estrutura do Projeto

```
banco/
├── src/
│   ├── constants/
│   ├── datastructure/
│   │   ├── AVLNode.java      <- Novo: Nó da AVL
│   │   └── AVLTree.java      <- Novo: Lógica da Árvore AVL
│   ├── exception/
│   ├── factory/
│   ├── model/
│   ├── repository/
│   │   ├── IRepositorioClientes.java
│   │   └── RepositorioClientes.java  <- Modificado para usar AVLTree
│   ├── service/
│   │   ├── IOperacoesBancarias.java
│   │   └── BancoServico.java         <- Modificado para usar remoção de cliente
│   ├── ui/
│   │   └── Menu.java                 <- Modificado para incluir opções 10 e 11
│   └── validator/
├── diagrama.md             <- Novo: Código Mermaid para o Diagrama UML
└── README.md               <- Este arquivo
```
