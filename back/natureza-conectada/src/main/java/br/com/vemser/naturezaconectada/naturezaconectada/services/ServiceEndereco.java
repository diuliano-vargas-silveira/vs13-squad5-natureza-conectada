
package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.BancoDeDadosException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceEndereco {

    EnderecoRepository enderecoRepository;

    public ServiceEndereco(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public void adicionar(Endereco endereco, Integer idCliente) throws BancoDeDadosException {
        try{
            this.enderecoRepository.adicionar(endereco,idCliente);
            System.out.println("Endereço adicionado com sucesso");

        }catch (BancoDeDadosException ex){
            ex.printStackTrace();

        }
    }

    public void deletar(int id) {
        Endereco endereco = procurarPorID(id);

//        BancoDeDados.enderecos.remove(endereco);
    }


    public boolean editar(int id, Endereco enderecoEditado) {
//        Endereco endereco = procurarPorID(id);
//
//        int indexContato = BancoDeDados.enderecos.indexOf(endereco);
//        endereco.setCidade(endereco.getCidade());
//        endereco.setCep(endereco.getCep());
//        endereco.setComplemento(endereco.getComplemento());
//        endereco.setEstado(endereco.getEstado());
//        endereco.setLogradouro(endereco.getLogradouro());
//        endereco.setTipo(endereco.getTipo().ordinal());
//
//        BancoDeDados.enderecos.set(indexContato, enderecoEditado);

        return true;
    }

    public Endereco procurarPorID(int id) {
//        Optional<Endereco> endereco = procurar(id);
//
//        if (endereco.isEmpty()) {
//            throw new InformacaoNaoEncontrada("Não existe nenhum endereço com este ID!");
//        }

        return new Endereco();
    }

    public List<Endereco> listarTodos() {
        return new ArrayList<>();
    }

    public Endereco procurar(int id) {
        return new Endereco();
    }

//    public List<Endereco> buscarEnderecoPorCliente(int id){
  //TODO:TERMNAR
//    }
}
