package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.RelatorioRequestDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioClienteDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import br.com.vemser.naturezaconectada.naturezaconectada.models.RelatorioMuda;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.RelatorioMudaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceRelatorioMuda {

    private final RelatorioMudaRepository relatorioMudaRepository;
    private final ServiceCliente serviceCliente;
    private final ServiceMudas serviceMudas;
    private final ObjectMapper objectMapper;


    public RelatorioClienteDTO adicionar(RelatorioRequestDTO dto) throws Exception {
        RelatorioMuda novoRelatorioMuda = new RelatorioMuda();
        Cliente clienteEncontrado = this.serviceCliente.buscarPorIdEntidade(dto.getIdCliente());
        this.serviceMudas.confereMudaCliente(dto.getIdMuda(),dto.getIdCliente());
        Muda mudaEncontrada = this.serviceMudas.buscarMudaAtiva(dto.getIdMuda());

        novoRelatorioMuda.setCliente(clienteEncontrado);
        novoRelatorioMuda.setMuda(mudaEncontrada);
        novoRelatorioMuda.setAvaliacao(null);
        novoRelatorioMuda.setEspecialista(null);
        novoRelatorioMuda.setSugestoes(null);
        novoRelatorioMuda.setEstadoMuda(dto.getEstadoMuda());

        this.relatorioMudaRepository.save(novoRelatorioMuda);

        return retornarDtoCliente(novoRelatorioMuda);

    }

    public RelatorioClienteDTO editarRelatorio(Integer id,RelatorioRequestDTO dto) throws Exception {
            RelatorioMuda relatorioMudaAtualizado = procurarPorID(id);
            if(relatorioMudaAtualizado.getAvaliacao() != null){
                throw  new RegraDeNegocioException("Este relatório ja foi avaliado e não pode ser editado");
            }else{
                this.serviceMudas.confereMudaCliente(dto.getIdMuda(),dto.getIdCliente());
                relatorioMudaAtualizado.setEstadoMuda(relatorioMudaAtualizado.getEstadoMuda());
                relatorioMudaAtualizado.setMuda(this.serviceMudas.getByEntidade(id));
                return retornarDtoCliente(relatorioMudaAtualizado);
            }
    }

    public RelatorioMuda procurarPorID(Integer id) throws Exception {

        RelatorioMuda relatorioMuda = this.relatorioMudaRepository.findById(id).orElseThrow(()->new RegraDeNegocioException("Não existe relatório com este id no banco de dados"));

        return relatorioMuda;

    }

    private RelatorioClienteDTO retornarDtoCliente(RelatorioMuda relatorioMuda) {

        return this.objectMapper.convertValue(relatorioMuda, RelatorioClienteDTO.class);
    }

    private RelatorioMuda retornarEntidade(RelatorioClienteDTO relatorio) {
        return this.objectMapper.convertValue(relatorio, RelatorioMuda.class);
    }

}