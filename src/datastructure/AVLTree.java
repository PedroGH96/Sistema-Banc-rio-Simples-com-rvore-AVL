package datastructure;

import model.Cliente;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
public class AVLTree {

    private AVLNode root;
    
    // Define que nossa árvore é organizada/ordenada pelo CPF do cliente
    // Usamos isso para saber se um cliente vai para a Esquerda (menor) ou Direita (maior)
    private final Comparator<Cliente> comparator = Comparator.comparing(Cliente::getCpf);

    // --- Métodos Auxiliares ---

    // Se o nó for nulo (não existe), a altura é 0
    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    // Calcula a Altura da Esquerda - Altura da Direita
    // Se o resultado for 2 ou -2, significa que a árvore está torta e precisa girar
    private int getBalanceFactor(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    // Mantém a árvore atualizada
    // A altura de um nó é sempre 1 + a altura do seu maior filho
    private void updateHeight(AVLNode node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    // --- Rotações ---

    // Rotação Simples à Direita (LL)
    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Executa a rotação
        x.right = y;
        y.left = T2;

        // Atualiza alturas
        updateHeight(y);
        updateHeight(x);

        return x; // Nova raiz da subárvore
    }

    // Rotação Simples à Esquerda (RR)
    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Executa a rotação
        y.left = x;
        x.right = T2;

        // Atualiza alturas
        updateHeight(x);
        updateHeight(y);

        return y; // Nova raiz da subárvore
    }

    // Rotação Dupla à Esquerda (LR)
    private AVLNode rotateLeftRight(AVLNode z) {
        z.left = rotateLeft(z.left);
        return rotateRight(z);
    }

    // Rotação Dupla à Direita (RL)
    private AVLNode rotateRightLeft(AVLNode z) {
        z.right = rotateRight(z.right);
        return rotateLeft(z);
    }

    // --- Inserção ---

    public void insert(Cliente cliente) {
        root = insert(root, cliente);
    }

    private AVLNode insert(AVLNode node, Cliente cliente) {
        // 1. Inserção normal de BST
        if (node == null) {
            return new AVLNode(cliente);
        }

        int compareResult = comparator.compare(cliente, node.cliente);

        if (compareResult < 0) {
            node.left = insert(node.left, cliente);
        } else if (compareResult > 0) {
            node.right = insert(node.right, cliente);
        } else {
            // Cliente já existe (CPF igual)
            return node;
        }

        // 2. Atualiza a altura do nó atual
        updateHeight(node);

        // 3. Obtém o fator de balanceamento
        int balance = getBalanceFactor(node);

        // 4. Realiza as Rotações se desbalanceado
        // Caso LL (Left Left)
        if (balance > 1 && comparator.compare(cliente, node.left.cliente) < 0) {
            return rotateRight(node);
        }

        // Caso RR (Right Right)
        if (balance < -1 && comparator.compare(cliente, node.right.cliente) > 0) {
            return rotateLeft(node);
        }

        // Caso LR (Left Right)
        if (balance > 1 && comparator.compare(cliente, node.left.cliente) > 0) {
            return rotateLeftRight(node);
        }

        // Caso RL (Right Left)
        if (balance < -1 && comparator.compare(cliente, node.right.cliente) < 0) {
            return rotateRightLeft(node);
        }

        return node;
    }

    // --- Busca ---

    public Cliente search(String cpf) {
        return search(root, cpf); // Começa a busca pelo topo (Raiz)
    }

    private Cliente search(AVLNode node, String cpf) {
        if (node == null) {
            return null;
        }
        // Compara o CPF buscado com o CPF do nó atual.
        int compareResult = node.cliente.getCpf().compareTo(cpf);

        if (compareResult > 0) { // CPF do nó é maior que o procurado, ir para a esquerda
            return search(node.left, cpf);
        } else if (compareResult < 0) { // CPF do nó é menor que o procurado, ir para a direita
            return search(node.right, cpf);
        } else { // Encontrado
            return node.cliente;
        }
    }

    // --- Getters ---

    public AVLNode getRoot() {
        return root;
    }

    // --- Remoção ---

    public void delete(String cpf) {
        root = delete(root, cpf); // Atualiza a raiz, pois ela pode mudar
    }

    // Usado quando o nó a ser removido tem 2 filhos (precisamos do menor da direita)
    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        // Vai tudo para a esquerda para achar o menor valor
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private AVLNode delete(AVLNode node, String cpf) {
        // 1. Executa a remoção padrão de BST
        if (node == null) {
            return null;
        }

        // Compara o CPF buscado com o CPF do nó atual.
        int compareResult = node.cliente.getCpf().compareTo(cpf);

        if (compareResult > 0) { // CPF do nó é maior que o procurado, ir para a esquerda
            node.left = delete(node.left, cpf);
        } else if (compareResult < 0) { // CPF do nó é menor que o procurado, ir para a direita
            node.right = delete(node.right, cpf);
        } else { // Nó encontrado (compareResult == 0)
            // Caso 1: Nó com 0 ou 1 filho
            if ((node.left == null) || (node.right == null)) {
                AVLNode temp = (node.left != null) ? node.left : node.right;

                // Caso sem filho
                if (temp == null) {
                    temp = null;
                    node = null;
                } else { // Caso com 1 filho
                    node = temp; // Copia o conteúdo do filho não vazio
                }
            } else {
                // Caso 2: Nó com 2 filhos: Obtém o sucessor in-order (menor na subárvore direita)
                AVLNode temp = minValueNode(node.right);

                // Copia os dados do sucessor in-order para este nó
                node.cliente = temp.cliente;

                // Remove o sucessor in-order
                node.right = delete(node.right, temp.cliente.getCpf());
            }
        }

        // Se a árvore tinha apenas um nó, retorna
        if (node == null) {
            return null;
        }

        // 2. Atualiza a altura do nó atual
        updateHeight(node);

        // 3. Obtém o fator de balanceamento
        int balance = getBalanceFactor(node);

        // 4. Realiza as Rotações se desbalanceado

        // Caso LL (Left Left)
        if (balance > 1 && getBalanceFactor(node.left) >= 0) {
            return rotateRight(node);
        }

        // Caso LR (Left Right)
        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            return rotateLeftRight(node);
        }

        // Caso RR (Right Right)
        if (balance < -1 && getBalanceFactor(node.right) <= 0) {
            return rotateLeft(node);
        }

        // Caso RL (Right Left)
        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            return rotateRightLeft(node);
        }

        return node;
    }

    // --- Listagem In-Order para o CRUD ---

    public List<Cliente> inOrderTraversal() {
        List<Cliente> clientes = new ArrayList<>();
        inOrderTraversal(root, clientes);
        return clientes;
    }

    private void inOrderTraversal(AVLNode node, List<Cliente> clientes) {
        if (node != null) {
            inOrderTraversal(node.left, clientes);
            clientes.add(node.cliente);
            inOrderTraversal(node.right, clientes);
        }
    }

    // --- Visualização Hierárquica (Requisito da Atividade) ---

    public String getHierarchicalView() {
        if (root == null) {
            return "Árvore AVL vazia.";
        }
        StringBuilder sb = new StringBuilder();
        printTree(root, "", true, sb);
        return sb.toString();
    }

    private void printTree(AVLNode node, String prefix, boolean isTail, StringBuilder sb) {
        if (node.right != null) {
            printTree(node.right, prefix + (isTail ? "│   " : "    "), false, sb);
        }
        sb.append(prefix).append(isTail ? "└── " : "┌── ").append(node.cliente.getCpf()).append(" (FB: ").append(getBalanceFactor(node)).append(", Alt: ").append(node.height).append(")\n");
        if (node.left != null) {
            printTree(node.left, prefix + (isTail ? "    " : "│   "), true, sb);
        }
    }
}
