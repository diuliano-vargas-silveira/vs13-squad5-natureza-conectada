package services;

import database.BancoDeDados;
import exceptions.InformacaoNaoEncontrada;
import exceptions.ObjetoExistente;
import interfaces.IService;
import interfaces.IServiceUsuario;
import models.*;

import java.util.List;
import java.util.Optional;

public class ServiceCliente implements IService<Cliente> {

    ServiceContato serviceContato = new ServiceContato();
    ServiceEndereco serviceEndereco = new ServiceEndereco();

    @Override
    public void adicionar(Cliente cliente) {
        Optional<Cliente> clienteEncontrado = procurarPorEmail(cliente.getEmail());

        if (clienteEncontrado.isPresent()) {
            throw new ObjetoExistente("Um cliente com este email já foi criado!");
        }

        cliente.setId(BancoDeDados.gerarNovoIdCliente());
        BancoDeDados.usuarios.add(cliente);
        BancoDeDados.clientes.add(cliente);
    }

    @Override
    public void deletar(int id) {
        Cliente cliente = procurarPorID(id);

        BancoDeDados.clientes.remove(cliente);
    }

    @Override
    public boolean editar(int id, Cliente clienteEditado) {
        Cliente cliente = procurarPorID(id);

        int indexRelatorio = BancoDeDados.clientes.indexOf(cliente);

        cliente.setNome(clienteEditado.getNome());
        cliente.setEmail(clienteEditado.getEmail());
        cliente.setCpf(clienteEditado.getCpf());

        BancoDeDados.clientes.set(indexRelatorio, cliente);

        return true;
    }

    public boolean editarContato(int id, Contato contato){
        Cliente cliente = procurarPorID(id);

        if(cliente == null)
            throw new InformacaoNaoEncontrada("Cliente não encontrado");

        serviceContato.editar(id, contato);
        return true;
    }

    public boolean editarEndereco(int id, Endereco endereco){
        Cliente cliente = procurarPorID(id);

        if(cliente == null)
            throw new InformacaoNaoEncontrada("Cliente não encontrado");

        serviceEndereco.editar(id, endereco);
        return true;
    }

    @Override
    public Cliente procurarPorID(int id) {
        Optional<Cliente> cliente = procurar(id);

        if(cliente.isEmpty()){
            throw new InformacaoNaoEncontrada("Não existe nenhum cliente com este ID!");
        }

        return cliente.get();
    }

    @Override
    public List<Cliente> listarTodos() {return BancoDeDados.clientes;
    }

    @Override
    public Optional<Cliente> procurar(int id) {
        return BancoDeDados.clientes.stream().filter(cliente -> cliente.getId() == id).findFirst();
    }

    public Optional<Cliente> procurarPorEmail(String email) {
        return BancoDeDados.clientes.stream().filter(cliente -> cliente.getEmail().equals(email)).findFirst();
    }

    // Métodos para adicionar aos arrays
    public void adicionarContato(Cliente cliente, Contato contato) {
        cliente.adicionarContato(contato);
    }
    public void adicionarEndereco(Cliente cliente, Endereco endereco) {
        cliente.adicionarEndereco(endereco);
    }
    public void adicionarMuda(Cliente cliente, Muda muda) {
        cliente.adicionarMuda(muda);
    }
    public void adicionarEntregas(Cliente cliente, Entrega entrega) {
        cliente.adicionarEntregas(entrega);
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
