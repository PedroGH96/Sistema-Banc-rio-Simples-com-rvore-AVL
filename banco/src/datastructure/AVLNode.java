package datastructure;

import model.Cliente;

public class AVLNode {
    public Cliente cliente;
    public AVLNode left;
    public AVLNode right;
    public int height;

    public AVLNode(Cliente cliente) {
        this.cliente = cliente;
        this.height = 1; // Novo nó é inicialmente uma folha, altura 1
    }
}
