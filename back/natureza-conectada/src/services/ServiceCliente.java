package services;


import exceptions.BancoDeDadosException;
import exceptions.InformacaoNaoEncontrada;
import exceptions.ObjetoExistente;
import interfaces.IService;
import models.*;
import repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

public class ServiceCliente implements IService<Cliente> {

    ServiceUsuario serviceUsuario = new ServiceUsuario();
    ClienteRepository clienteRepository = new ClienteRepository();

    @Override
    public void adicionar(Cliente cliente) throws BancoDeDadosException {

       Usuario usuario = serviceUsuario.adicionarUsuario(cliente);

       cliente.setId(usuario.getId());

       clienteRepository.adicionar(cliente);
    }

    @Override
    public void deletar(int id) {
        Cliente cliente =



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

    public boolean editarContato(int idCliente, int idContato, Contato contatoEditado){
        Cliente cliente = procurarPorID(idCliente);

        for(Contato contato : cliente.getContatos()){
            if(contato.getId() == idContato){
                serviceContato.editar(idContato, contatoEditado);
                return true;
            }
        }

        throw new InformacaoNaoEncontrada("Contato não encontrado.");
    }

    public boolean editarEndereco(int idCliente, int idEndereco, Endereco enderecoEditado){
        Cliente cliente = procurarPorID(idCliente);

        for(Endereco endereco : cliente.getEnderecos()){
            if(endereco.getId() == idEndereco){
                serviceEndereco.editar(idEndereco, enderecoEditado);
                return true;
            }
        }

        throw new InformacaoNaoEncontrada("Endereço não encontrado.");
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

    public void imprimirMudasCliente(Cliente cliente){
        cliente.imprimirMudas();
    }
}
