package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Contato;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Endereco;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Muda> listarMudasPorcliente(@Param("idCliente")Integer idCliente);

    List<Contato> procurarContatosPorIdCliente(@Param("idCliente") Integer idCliente);

    List<Endereco> procurarEnderecosPorIdCliente(@Param("idCliente") Integer idCliente);

    Cliente listarPorEmail(String email);

    void inserirMudaEmCliente(Integer idCliente, Integer idMuda);
}
