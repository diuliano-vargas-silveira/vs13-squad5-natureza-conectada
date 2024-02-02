package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MudaRepository  {

    public Muda adicionar(Muda muda) throws Exception {
        return null;
    }

    public boolean mudarAtivoMuda(Integer id,Ativo ativo) throws Exception {
        return false;
    }

    public Muda editar(Integer id, Muda muda) throws Exception {
        return null;
    }

    public List<Muda> listarMudasAtivas() throws Exception {
        return null;
    }

    public List<Muda> listar() throws Exception {
        return null;
    }

    public Muda procurarPorIdMuda(Integer idMuda) throws Exception {
        return null;
    }

    public Muda procurarPorEco(Ecossistema ecossistema) throws Exception {
        return null;
    }

    public List<Muda> procurarMudasEntrega(int idEntrega) throws Exception {
        return null;
    }
}