package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.BancoDeDadosException;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.interfaces.IService;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.ClienteRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceCliente implements IService<Cliente> {

    ServiceUsuario serviceUsuario;
    ClienteRepository clienteRepository = new ClienteRepository();

    @Override
    public void adicionar(Cliente cliente) throws Exception {

        Usuario usuarioCriado = serviceUsuario.adicionarUsuario(cliente);
        cliente.setId(usuarioCriado.getId());

        if (cliente.getCpf().length() != 11) {
            throw new Exception("CPF Invalido!");
        }

        clienteRepository.adicionar(cliente);
        System.out.println("Cliente adicionado com sucesso! " + cliente);

    }

    @Override
    public void deletar(int id) throws BancoDeDadosException {
        Cliente cliente = procurarPorID(id);

        clienteRepository.remover(id);
        serviceUsuario.remover(cliente.getId());
    }

    @Override
    public boolean editar(int id, Cliente clienteEditado) throws BancoDeDadosException {
        serviceUsuario.editar(clienteEditado.getId(), clienteEditado);
        return clienteRepository.editar(id, clienteEditado);
    }



    @Override
    public List<Cliente> listarTodos() throws  BancoDeDadosException {
        return clienteRepository.listar();
    }



    @Override
    public Cliente procurarPorID(int id) throws BancoDeDadosException {
        Cliente cliente = clienteRepository.listarPorID(id);

        if(cliente == null){
            throw new InformacaoNaoEncontrada("Não existe nenhum cliente com este ID!");
        }

        return cliente;
    }
    @Override
    public Cliente procurar(int id) throws BancoDeDadosException {
        return clienteRepository.listarPorID(id);
    }

    public void inserirMudasEntregues(Integer idCliente,Integer idMuda){
        try {
            this.clienteRepository.InserirMudaEmCliente(idCliente,idMuda);

        }catch (BancoDeDadosException ex){
            System.out.println("erro ao Anexar as mudas Entregar, Erro: " + ex.getMessage());
        }

    }
}
