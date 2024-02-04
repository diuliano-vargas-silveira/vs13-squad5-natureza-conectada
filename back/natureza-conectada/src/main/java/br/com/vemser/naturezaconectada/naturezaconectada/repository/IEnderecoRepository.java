package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEnderecoRepository extends JpaRepository<Endereco, Integer> {


//    List<Endereco> listarEnderecosPorAtivo(String ativo);
//
//    List<Endereco> procurarEnderecoPorIdCliente(@Param("idCliente") Integer idCliente);
//
//    boolean ativarEndereco(Integer idEndereco, String eco);
}
