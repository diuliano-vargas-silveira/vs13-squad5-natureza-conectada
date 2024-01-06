package services;

import database.BancoDeDados;
import exceptions.InformacaoNaoEncontrada;
import exceptions.ObjetoExistente;
import interfaces.IService;
import models.Cliente;
import java.util.List;
import java.util.Optional;

public class ServiceCliente implements IService<Cliente> {

    @Override
    public void adicionar(Cliente cliente) {
        Optional<Cliente> clienteExistente = procurarPorID(cliente.getId());

        if (clienteExistente.isPresent())
            throw new ObjetoExistente("Este cliente já existe");

        cliente.setId(BancoDeDados.gerarNovoIdCliente());
        BancoDeDados.clientes.add(cliente);
    }

    @Override
    public void deletar(int id) {
        Optional<Cliente> clienteExistente = procurarPorID(id);

        if (clienteExistente.isEmpty())
            throw new InformacaoNaoEncontrada("Este cliente não existe.");

        BancoDeDados.clientes.remove(clienteExistente.get());
    }

    @Override
    public boolean editar(int id, Cliente clienteEditado) {
        Optional<Cliente> clienteExistente = procurarPorID(id);

        if (clienteExistente.isEmpty())
            throw new InformacaoNaoEncontrada("Este cliente não existe.");

        clienteEditado.setId(id);
        int indexCliente = BancoDeDados.clientes.indexOf(clienteExistente.get());
        BancoDeDados.clientes.set(indexCliente, clienteEditado);

        return true;
    }

    @Override
    public Optional<Cliente> procurarPorID(int id) {
        return BancoDeDados.clientes.stream().filter(cliente -> cliente.getId() == id).findFirst();
    }

    @Override
    public List<Cliente> listarTodos() {return BancoDeDados.clientes;
    }

    //Métodos de impressão
    public void imprimirCliente(Cliente cliente) {
        System.out.println("------------------------------");
        System.out.println("ID: " + cliente.getId());
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("E-mail: " + cliente.getEmail());
        System.out.println("------------------------------");
        System.out.println("Contatos:");
        cliente.imprimirContatos();
        System.out.println("------------------------------");
        System.out.println("Endereços:");
        cliente.imprimirEnderecos();
        System.out.println("------------------------------");
        System.out.println("Mudas:");
        cliente.imprimirMudas();
        System.out.println("------------------------------");
        System.out.println("Entregas:");
        cliente.imprimirEntregas();
        System.out.println("------------------------------");
    }
}
