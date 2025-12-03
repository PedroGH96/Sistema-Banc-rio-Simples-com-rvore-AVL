package repository;

import model.Cliente;
import exception.ClienteJaExisteException;

import datastructure.AVLTree;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Implementação do repositório de clientes.
 * Boa Prática: SRP - Responsabilidade única de armazenar clientes.
 */
public class RepositorioClientes implements IRepositorioClientes {
    private final AVLTree clientesTree;

    public RepositorioClientes() {
        this.clientesTree = new AVLTree();
    }

    @Override
    public void adicionar(Cliente cliente) throws ClienteJaExisteException {
        Objects.requireNonNull(cliente, "Cliente não pode ser nulo");

        if (existe(cliente.getCpf())) {
            throw new ClienteJaExisteException(cliente.getCpf());
        }

        clientesTree.insert(cliente);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        Objects.requireNonNull(cpf, "CPF não pode ser nulo");

        Cliente cliente = clientesTree.search(cpf);
        return Optional.ofNullable(cliente);
    }

    @Override
  
    public boolean existe(String cpf) {
        return buscarPorCpf(cpf).isPresent();
    }

    @Override
    public void remover(String cpf) {
        Objects.requireNonNull(cpf, "CPF não pode ser nulo");
        if (!existe(cpf)) {
            // Lançar exceção apropriada se necessário, mas o método original não tinha
            // um método de remoção, então vamos assumir que a remoção silenciosa é aceitável
            // ou que a camada de serviço fará a verificação.
            return;
        }
        clientesTree.delete(cpf);
    }

    @Override
    public List<Cliente> listarTodos() {
        // A travessia in-order da AVL retorna a lista ordenada por CPF
        return clientesTree.inOrderTraversal();
    }

    @Override
    public String getAVLView() {
        return clientesTree.getHierarchicalView();
    }
}
