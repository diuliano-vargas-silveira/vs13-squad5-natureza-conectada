package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.MudaCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.MudaDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
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

//    private final ServiceCliente serviceCliente;


    public MudaCreateDTO adicionar(MudaCreateDTO mudadto) throws Exception {
        Muda muda = retornarEntidade(mudadto);
        muda.setAtivo(Ativo.A);

        Muda mudaCriada = this.mudaRepository.save(muda);



        return retornarDto(mudaCriada);
    }

    public void mudarAtivoMuda(int id) throws Exception {
        Muda mudaEncontrada = procurarPorIDEntidade(id);

        if (mudaEncontrada.getAtivo() == Ativo.A) {
            mudaEncontrada.setAtivo(Ativo.D);
        } else {
            mudaEncontrada.setAtivo(Ativo.A);
        }

        this.mudaRepository.save(mudaEncontrada);

    }

    private Muda procurarPorIDEntidade(int id) throws Exception {
        Muda mudaEncontrada = this.mudaRepository.findById(id).orElseThrow(()->new InformacaoNaoEncontrada("Não foi encontrado a muda com id "+ id));
        return mudaEncontrada;
    }

    public MudaDTO procurarPorIdDto(int id) throws Exception {
        Muda mudaEncontrada = this.mudaRepository.findById(id).orElseThrow(()->new InformacaoNaoEncontrada("Não foi encontrado a muda com id "+ id));
        return this.objectMapper.convertValue(mudaEncontrada,MudaDTO.class);
    }

//    public List<MudaDTO> obterMudasDaEntrega (int idEntrega) throws Exception { todo:verificar se é necessario, ou se o entrega ja consegue anexar automaticamente
//
//        return this.mudaRepository.procurarMudasEntrega(idEntrega).stream().map(muda -> this.objectMapper.convertValue(muda,MudaDTO.class)).toList();
//
//    }


    public MudaCreateDTO editarmuda(Integer idMuda, MudaCreateDTO mudaEditada) throws Exception {

        Muda mudaAtualizada = this.procurarPorIDEntidade(idMuda);

        mudaAtualizada.setPorte(mudaEditada.getPorte());
        mudaAtualizada.setDescricao(mudaEditada.getDescricao());
        mudaAtualizada.setEcossistema(mudaEditada.getEcossistema());
        mudaAtualizada.setEstoque(mudaEditada.getEstoque());
        mudaAtualizada.setNome(mudaEditada.getNome());
        mudaAtualizada.setNomeCientifico(mudaEditada.getNomeCientifico());
        mudaAtualizada.setTipo(mudaEditada.getTipo());

        this.mudaRepository.save(mudaAtualizada);
   ;
        return retornarDto(mudaAtualizada);
    }

    public List<MudaCreateDTO> listarTodasMudas() throws Exception {
        List<MudaCreateDTO> listaDeMudas = new ArrayList<>();


        this.mudaRepository.findAll().forEach(muda -> listaDeMudas.add(retornarDto(muda)));

        return listaDeMudas;
    }
    public List<MudaDTO> listarMudasAtivas() throws Exception {
        List<MudaDTO> listaDeMudas = new ArrayList<>();


        this.mudaRepository.findByAtivoIs(Ativo.A).forEach(muda -> listaDeMudas.add(this.objectMapper.convertValue(muda,MudaDTO.class)));

        return listaDeMudas;
    }



    public List<MudaDTO> buscarPorEco(Ecossistema ecossistema) throws Exception {
        List<MudaDTO> listaDeMudas = new ArrayList<>();


        this.mudaRepository.findByEcossistemaIs(ecossistema).forEach(muda -> listaDeMudas.add(this.objectMapper.convertValue(muda,MudaDTO.class)));

        return listaDeMudas;

    }

    public MudaCreateDTO novaMuda(MudaCreateDTO mudaDto) throws Exception {
        Muda novaMuda = retornarEntidade(mudaDto);
        novaMuda.setAtivo(Ativo.A);

        return retornarDto(this.mudaRepository.save(novaMuda));
    }


    private MudaCreateDTO retornarDto(Muda muda){
        return this.objectMapper.convertValue(muda,MudaCreateDTO.class);
    }
    private Muda retornarEntidade(MudaCreateDTO muda){
        return this.objectMapper.convertValue(muda,Muda.class);
    }

}
