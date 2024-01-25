 package br.com.vemser.naturezaconectada.naturezaconectada.services;


 import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.Exception;
 import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;
 import br.com.vemser.naturezaconectada.naturezaconectada.repository.ClienteRepository;
 import br.com.vemser.naturezaconectada.naturezaconectada.repository.ContatoRepository;
 import org.springframework.stereotype.Service;

 import java.util.ArrayList;
 import java.util.List;

 @Service
 public class ServiceContato  {

    private ContatoRepository contatoRepository;
    private ClienteRepository clienteRepository;

     public ServiceContato(ContatoRepository contatoRepository, ClienteRepository clienteRepository) {
         this.contatoRepository = contatoRepository;
         this.clienteRepository = clienteRepository;
     }

     public void adicionarContato(Contato contato, Integer idUsuario)  {

        try{
            this.contatoRepository.novoContato(contato,idUsuario);
            System.out.println("Contato adicionado com sucesso");

        }catch (Exception ex){
            ex.printStackTrace();

        }


    }
     public List<Contato> listaDeContatoPorCliente(Integer idCliente)  {
        List<Contato> listaDeContatos = new ArrayList<>();
        try {
            listaDeContatos = this.contatoRepository.contatosPorCliente(idCliente);
        }catch (Exception ex){
            System.out.println("Erro ao buscar contatos do cliente " + ex.getMessage());
            ex.printStackTrace();
        }catch (java.lang.Exception erro){
            System.out.println("Erro: "+ erro.getMessage());
            erro.printStackTrace();
        }
       return listaDeContatos;
     }
    public void EditarContato(Integer idContato, Contato contato){
        try{
            this.contatoRepository.editar(idContato,contato);

        }catch (Exception be){
            System.out.println("Erro: "+ be.getMessage());
            be.printStackTrace();
        }


    }

    public void remover(Integer idContato){
        try {
            this.contatoRepository.excluirContato(idContato);
            System.out.println("contato " + idContato + " excluido com sucesso");
        }catch (Exception e){
            System.out.println("Erro ao excluir contato : " + e.getMessage());
            e.printStackTrace();
        }
    }

}

