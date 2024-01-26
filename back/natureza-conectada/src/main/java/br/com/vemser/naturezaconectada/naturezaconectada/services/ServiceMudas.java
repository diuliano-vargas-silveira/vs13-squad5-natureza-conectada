
package br.com.vemser.naturezaconectada.naturezaconectada.services;


import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.MudaCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.MudaDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.ErroNoBancoDeDados;
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


    public MudaCreateDTO adicionar(MudaCreateDTO mudadto) throws Exception {
        Muda muda = objectMapper.convertValue(mudadto,Muda.class);

        Muda mudaCriada = this.mudaRepository.adicionar(muda);

        MudaCreateDTO retorno = objectMapper.convertValue(mudaCriada, MudaCreateDTO.class);

        return retorno;
    }

    public void remover(Integer idMuda) throws Exception {
        this.mudaRepository.remover(idMuda);

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

    public MudaCreateDTO novaMuda(MudaCreateDTO mudaDto) throws Exception {
        Muda novaMuda = this.objectMapper.convertValue(mudaDto,Muda.class);
        return this.objectMapper.convertValue(this.mudaRepository.adicionar(novaMuda), MudaCreateDTO.class);
    }
}
