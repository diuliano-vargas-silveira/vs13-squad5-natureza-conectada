 package br.com.vemser.naturezaconectada.naturezaconectada.services;


 import exceptions.BancoDeDadosException;
 import models.Contato;
 import repository.ClienteRepository;
 import repository.ContatoRepository;

 import java.util.ArrayList;
 import java.util.List;

 public class ServiceContato  {

    private ContatoRepository contatoRepository = new ContatoRepository();
    private ClienteRepository clienteRepository = new ClienteRepository();

    public void adicionarContato(Contato contato,Integer idUsuario)  {

        try{
            this.contatoRepository.novoContato(contato,idUsuario);
            System.out.println("Contato adicionado com sucesso");

        }catch (BancoDeDadosException ex){
            ex.printStackTrace();

        }


    }
     public List<Contato> listaDeContatoPorCliente(Integer idCliente)  {
        List<Contato> listaDeContatos = new ArrayList<>();
        try {
            listaDeContatos = this.contatoRepository.contatosPorCliente(idCliente);
        }catch (BancoDeDadosException ex){
            System.out.println("Erro ao buscar contatos do cliente " + ex.getMessage());
            ex.printStackTrace();
        }catch (Exception erro){
            System.out.println("Erro: "+ erro.getMessage());
            erro.printStackTrace();
        }
       return listaDeContatos;
     }
    public void EditarContato(Integer idContato, Contato contato){
        try{
            this.contatoRepository.editar(idContato,contato);

        }catch (BancoDeDadosException be){
            System.out.println("Erro: "+ be.getMessage());
            be.printStackTrace();
        }


    }

    public void remover(Integer idContato){
        try {
            this.contatoRepository.excluirContato(idContato);
            System.out.println("contato " + idContato + " excluido com sucesso");
        }catch (BancoDeDadosException e){
            System.out.println("Erro ao excluir contato : " + e.getMessage());
            e.printStackTrace();
        }
    }

}

