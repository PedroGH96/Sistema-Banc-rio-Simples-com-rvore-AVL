package datastructure;

import model.Cliente;

public class AVLNode {
    public Cliente cliente;
    public AVLNode left;  // Filho à Esquerda (CPFs menores)
    public AVLNode right; // Filho à Direita (CPFs maiores)
    
    // Armazena a altura para cálculo do FB (Fator de Balanceamento)
    public int height;

    // Construtor: Cria um novo nó folha
    public AVLNode(Cliente cliente) {
        this.cliente = cliente;
        this.height = 1; // Todo nó nasce como folha (altura 1)
    }
}
