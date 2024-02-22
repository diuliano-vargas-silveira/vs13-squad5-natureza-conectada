package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.AvaliacaoDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.RelatorioRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioClienteDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioResponseAdmin;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioResponseEspecialista;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Especialista;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import br.com.vemser.naturezaconectada.naturezaconectada.models.RelatorioMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.RelatorioMudaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor


public class ServiceRelatorioMuda {

    private final RelatorioMudaRepository relatorioMudaRepository;

    private final ServiceEspecialista serviceEspecialista;
    private final ServiceCliente serviceCliente;
    private final ServiceMudas serviceMudas;
    private final ObjectMapper objectMapper;


    public RelatorioClienteDTO adicionar(RelatorioRequestDTO dto) throws Exception {
        RelatorioMuda novoRelatorioMuda = new RelatorioMuda();
        Cliente clienteEncontrado = this.serviceCliente.getUsuarioLogado();
        this.serviceMudas.confereMudaCliente(dto.getIdMuda(), clienteEncontrado.getId());
        Muda mudaEncontrada = this.serviceMudas.buscarMudaAtiva(dto.getIdMuda());

        novoRelatorioMuda.setCliente(clienteEncontrado);
        novoRelatorioMuda.setMuda(mudaEncontrada);
        novoRelatorioMuda.setAvaliacao(null);
        novoRelatorioMuda.setEspecialista(null);
        novoRelatorioMuda.setSugestoes(" ");
        novoRelatorioMuda.setEstadoMuda(dto.getEstadoMuda());

        this.relatorioMudaRepository.save(novoRelatorioMuda);

        return retornarDtoCliente(novoRelatorioMuda);

    }

    public RelatorioClienteDTO editarRelatorio(Integer id, RelatorioRequestDTO dto) throws Exception {
        Cliente cliente = this.serviceCliente.getUsuarioLogado();
        RelatorioMuda relatorioMudaAtualizado = this.relatorioMudaRepository.findByClienteIs(cliente).stream().filter(relatorioMuda -> relatorioMuda.getId() == id).findFirst().orElseThrow(() -> new InformacaoNaoEncontrada("Não foi encontrado nenhum relatorio com este id"));
        if (relatorioMudaAtualizado.getAvaliacao() != null) {
            throw new RegraDeNegocioException("Este relatório ja foi avaliado e não pode ser editado");
        } else {
            this.serviceMudas.confereMudaCliente(dto.getIdMuda(), relatorioMudaAtualizado.getCliente().getId());
            relatorioMudaAtualizado.setEstadoMuda(dto.getEstadoMuda());
            relatorioMudaAtualizado.setMuda(this.serviceMudas.getByEntidade(id));
            this.relatorioMudaRepository.save(relatorioMudaAtualizado);
            return retornarDtoCliente(relatorioMudaAtualizado);
        }
    }

    public List<RelatorioResponseAdmin> listar() {
        List<RelatorioMuda> relatorios = this.relatorioMudaRepository.findAll();
        List<RelatorioResponseAdmin> dtos = new ArrayList<>();

        for (RelatorioMuda relatorio : relatorios) {
            RelatorioResponseAdmin dto = new RelatorioResponseAdmin();
            dto.setId(relatorio.getId());
            dto.setIdcliente(relatorio.getCliente().getId());
            dto.setNomeCliente(relatorio.getCliente().getNome());
            dto.setMuda(relatorio.getMuda());
            if (relatorio.getEspecialista() != null) {
                dto.setIdAvaliador(relatorio.getEspecialista().getId());
                dto.setNomeAvaliador(relatorio.getEspecialista().getNome());
            } else {
                dto.setIdAvaliador(null);
                dto.setNomeAvaliador(null);
            }
            dto.setEstadoMuda(relatorio.getEstadoMuda());
            dto.setAvaliacao(relatorio.getAvaliacao());

            dtos.add(dto);
        }

        return dtos;
    }

    public void avaliarRelatorio(Integer idRelatorio, AvaliacaoDTO dto) throws Exception {
        Especialista especialista = this.serviceEspecialista.getUsuarioLogado();
        RelatorioMuda relatorio = procurarPorID(idRelatorio);
        relatorio.setAvaliacao(dto.getAvaliacao());
        relatorio.setSugestoes(dto.getSugestoes());
        relatorio.setEspecialista(especialista);
        this.relatorioMudaRepository.save(relatorio);

    }

    public RelatorioMuda procurarPorID(Integer id) throws Exception {

        RelatorioMuda relatorioMuda = this.relatorioMudaRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Não existe relatório com este id no banco de dados"));

        return relatorioMuda;

    }

    public RelatorioClienteDTO buscarRelatorio(Integer id) throws Exception {
        RelatorioClienteDTO relatorioCliente = retornarDtoCliente(procurarPorID(id));
        return relatorioCliente;
    }


    public RelatorioClienteDTO retornarDtoCliente(RelatorioMuda relatorioMuda) {
        RelatorioClienteDTO relatorioCliente = new RelatorioClienteDTO();
        relatorioCliente.setCliente(relatorioMuda.getCliente());
        relatorioCliente.setMuda(relatorioMuda.getMuda());
        relatorioCliente.setEstadoMuda(relatorioMuda.getEstadoMuda());
        relatorioCliente.setSugestoes(relatorioMuda.getSugestoes());
        relatorioCliente.setAvaliacao(relatorioMuda.getAvaliacao());
        if (relatorioMuda.getEspecialista() != null) {
            relatorioCliente.setNomeEspecialista(relatorioMuda.getEspecialista().getNome());
        } else {
            relatorioCliente.setNomeEspecialista("Aguardando avaliação");
        }
        relatorioCliente.setId(relatorioMuda.getId());

        return relatorioCliente;
    }

    private RelatorioMuda retornarEntidade(RelatorioClienteDTO relatorio) {
        return this.objectMapper.convertValue(relatorio, RelatorioMuda.class);
    }

    public List<RelatorioResponseEspecialista> relatorioEspecialista(Integer idEspecialista) throws Exception {
        Especialista especialista = this.serviceEspecialista.procurarPorIDEntidade(idEspecialista);
        List<RelatorioResponseEspecialista> retorno = new ArrayList<>();
        List<RelatorioMuda> relatorioEspecialista = this.relatorioMudaRepository.findByEspecialistaIs(especialista);

        for (RelatorioMuda relatorio : relatorioEspecialista) {
            RelatorioResponseEspecialista dto = new RelatorioResponseEspecialista();
            dto.setId(relatorio.getId());
            dto.setIdCliente(relatorio.getCliente().getId());
            dto.setNomeCliente(relatorio.getCliente().getNome());
            dto.setSugestoes(relatorio.getSugestoes());
            dto.setAvaliacao(relatorio.getAvaliacao());
            dto.setMuda(relatorio.getMuda());
            dto.setEstadoMuda(relatorio.getEstadoMuda());
            dto.setEspecialista(relatorio.getEspecialista());
            retorno.add(dto);
        }
        return retorno;
    }

    public List<RelatorioResponseEspecialista> buscarAbertos() {
        List<RelatorioResponseEspecialista> retorno = new ArrayList<>();
        List<RelatorioMuda> relatorioEspecialista = this.relatorioMudaRepository.findByAvaliacaoIsNull();

        for (RelatorioMuda relatorio : relatorioEspecialista) {
            RelatorioResponseEspecialista dto = new RelatorioResponseEspecialista();
            dto.setId(relatorio.getId());
            dto.setIdCliente(relatorio.getCliente().getId());
            dto.setNomeCliente(relatorio.getCliente().getNome());
            dto.setSugestoes(relatorio.getSugestoes());
            dto.setAvaliacao(relatorio.getAvaliacao());
            dto.setMuda(relatorio.getMuda());
            dto.setEstadoMuda(relatorio.getEstadoMuda());
            dto.setEspecialista(relatorio.getEspecialista());
            retorno.add(dto);
        }
        return retorno;
    }


}