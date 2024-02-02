package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EnderecoRepository {

    public Endereco adicionar(Endereco endereco, Integer idUsuario) throws Exception {
        return null;
    }

    public void remover(Integer id) throws Exception {
    }

    public Endereco editar(Integer id, Endereco endereco) throws Exception {
        return null;
    }

    public List<Endereco> listar() throws Exception {
        return null;
    }

    public List<Endereco> listarEnderecosPorAtivo(String ativo) throws Exception {
        return null;
    }


    public Endereco procurarEnderecoPorIdEndereco(Integer idEndereco) throws Exception {
        return null;
    }

    public List<Endereco> procurarEnderecoPorIdCliente(Integer idCliente) throws Exception {
        return null;
    }

    public boolean ativarEndereco(Integer id,String eco) throws Exception {
        return false;
    }
}