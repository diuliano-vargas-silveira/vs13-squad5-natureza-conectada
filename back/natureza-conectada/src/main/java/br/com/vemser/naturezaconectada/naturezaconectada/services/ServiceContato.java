 package br.com.vemser.naturezaconectada.naturezaconectada.services;


 import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ErroNoBancoDeDados;
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

     public void adicionarContato(Contato contato, Integer idUsuario) throws ErroNoBancoDeDados {


            this.contatoRepository.novoContato(contato,idUsuario);
            System.out.println("Contato adicionado com sucesso");




    }
     public List<Contato> listaDeContatoPorCliente(Integer idCliente) throws ErroNoBancoDeDados {
        List<Contato> listaDeContatos = new ArrayList<>();

            listaDeContatos = this.contatoRepository.contatosPorCliente(idCliente);

       return listaDeContatos;
     }
    public void EditarContato(Integer idContato, Contato contato) throws ErroNoBancoDeDados {

            this.contatoRepository.editar(idContato,contato);



    }

    public void remover(Integer idContato) throws ErroNoBancoDeDados {

            this.contatoRepository.excluirContato(idContato);

    }

}

