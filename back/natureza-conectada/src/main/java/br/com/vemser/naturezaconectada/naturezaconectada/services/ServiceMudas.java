
package br.com.vemser.naturezaconectada.naturezaconectada.services;


import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.MudaCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.MudaDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.MudaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceMudas {
    private final MudaRepository mudaRepository;
    private final ObjectMapper objectMapper;

    private final ServiceCliente serviceCliente;


    public MudaCreateDTO adicionar(MudaCreateDTO mudadto) throws Exception {
        Muda muda = objectMapper.convertValue(mudadto,Muda.class);

        Muda mudaCriada = this.mudaRepository.adicionar(muda);

        MudaCreateDTO retorno = objectMapper.convertValue(mudaCriada, MudaCreateDTO.class);

        return retorno;
    }

    public void mudarAtivoMuda(Integer idMuda, Ativo ativo) throws Exception {
        this.mudaRepository.mudarAtivoMuda(idMuda,ativo);

    }

    public List<MudaDTO> obterMudasDaEntrega (int idEntrega) throws Exception {

        return this.mudaRepository.obterMudasDaEntrega(idEntrega).stream().map(muda -> this.objectMapper.convertValue(muda,MudaDTO.class)).toList();

    }



    public MudaCreateDTO editarmuda(Integer idMuda, MudaCreateDTO muda) throws Exception {
        Muda mudaEditada = objectMapper.convertValue(muda,Muda.class);
        this.mudaRepository.editar(idMuda, mudaEditada);
        mudaEditada.setId(idMuda);
        MudaCreateDTO mudaRetorno = objectMapper.convertValue(mudaEditada, MudaCreateDTO.class);
        return mudaRetorno;
    }

    public List<MudaDTO> listarMudas() throws Exception {
        List<MudaDTO> listaDeMudas = new ArrayList<>();

        this.mudaRepository.listar().forEach(muda -> listaDeMudas.add(this.objectMapper.convertValue(muda,MudaDTO.class)));

        return listaDeMudas;
    }

    public MudaDTO buscarPorId(Integer idMuda) throws Exception {
        Muda muda = this.mudaRepository.buscarPorId(idMuda);

            return this.objectMapper.convertValue(muda, MudaDTO.class);

    }
    public MudaDTO buscarPorEco(Ecossistema ecossistema) throws Exception {
        Muda muda = this.mudaRepository.buscarPorEco(ecossistema);

        return this.objectMapper.convertValue(muda, MudaDTO.class);

    }

    public MudaCreateDTO novaMuda(MudaCreateDTO mudaDto) throws Exception {
        Muda novaMuda = this.objectMapper.convertValue(mudaDto,Muda.class);
        return this.objectMapper.convertValue(this.mudaRepository.adicionar(novaMuda), MudaCreateDTO.class);
    }

    public List<MudaDTO> listarMudasAtivas() throws Exception {
        List<MudaDTO> listaDeMudasAtivas = new ArrayList<>();

        this.mudaRepository.listarMudasAtivas().forEach(muda -> listaDeMudasAtivas.add(this.objectMapper.convertValue(muda,MudaDTO.class)));

        return listaDeMudasAtivas;
    }
}
