package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.relatorios.RelatorioMudasDoadas;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.MudaCreateDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.MudaDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ecossistema;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.MudaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceMudas {
    private final MudaRepository mudaRepository;
    private final ObjectMapper objectMapper;
    private final ServiceCliente serviceCliente;


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

    public Muda procurarPorIDEntidade(int id) throws Exception {
        Muda mudaEncontrada = this.mudaRepository.findById(id).orElseThrow(() -> new InformacaoNaoEncontrada("Não foi encontrado a muda com id " + id));
        return mudaEncontrada;
    }

    public MudaDTO procurarPorIdDto(int id) throws Exception {
        Muda mudaEncontrada = this.mudaRepository.findById(id).orElseThrow(() -> new InformacaoNaoEncontrada("Não foi encontrado a muda com id " + id));
        return this.objectMapper.convertValue(mudaEncontrada, MudaDTO.class);
    }

    public Muda buscarMudaAtiva(Integer id) throws Exception {
        Muda mudaEncontrada = this.mudaRepository.findByAtivoAndId(Ativo.A, id).orElseThrow(() -> new RegraDeNegocioException("Não foi possível encontrar a muda no banco de dados"));
        return mudaEncontrada;
    }


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

    public Page<MudaCreateDTO> listarTodasMudas(Pageable paginacao) throws Exception {
        var mudasPaginadas = mudaRepository.findAll(paginacao);


        return mudasPaginadas.map(muda -> objectMapper.convertValue(muda, MudaCreateDTO.class));
    }

    public List<MudaDTO> listarMudasAtivas() throws Exception {
        List<MudaDTO> listaDeMudas = new ArrayList<>();

        this.mudaRepository.findByAtivoIs(Ativo.A).forEach(muda -> listaDeMudas.add(this.objectMapper.convertValue(muda, MudaDTO.class)));

        return listaDeMudas;
    }


    public List<MudaDTO> buscarPorEco(Ecossistema ecossistema) throws Exception {
        List<MudaDTO> listaDeMudas = new ArrayList<>();

        this.mudaRepository.findByEcossistemaIs(ecossistema).stream().filter((muda -> muda.getAtivo().equals(Ativo.A))).toList().forEach(muda -> listaDeMudas.add(this.objectMapper.convertValue(muda, MudaDTO.class)));

        return listaDeMudas;

    }

    public MudaCreateDTO novaMuda(MudaCreateDTO mudaDto) throws Exception {
        Muda novaMuda = retornarEntidade(mudaDto);
        novaMuda.setAtivo(Ativo.A);

        return retornarDto(this.mudaRepository.save(novaMuda));
    }

    public Muda getByEntidade(Integer id) throws Exception {
        Muda retornoMuda = this.mudaRepository.findById(id).orElseThrow(() -> new InformacaoNaoEncontrada("Não existe muda com este id no banco de dados"));
        return retornoMuda;
    }

    public void confereMudaCliente(Integer idMuda, Integer idCliente) throws Exception {
        Cliente cliente = this.serviceCliente.buscarPorIdEntidade(idCliente);
        Optional<List<Muda>> isPertence = this.mudaRepository.findByClienteAndIdIs(cliente, idMuda);
        if (isPertence.get().isEmpty()) {
            throw new RegraDeNegocioException("A muda não pertence ao cliente");
        }
    }

    private MudaCreateDTO retornarDto(Muda muda) {
        return this.objectMapper.convertValue(muda, MudaCreateDTO.class);
    }

    private Muda retornarEntidade(MudaCreateDTO muda) {
        return this.objectMapper.convertValue(muda, Muda.class);
    }

    public List<RelatorioMudasDoadas> mudasDoadas() {
        return this.mudaRepository.mudasDoadas();
    }


}
