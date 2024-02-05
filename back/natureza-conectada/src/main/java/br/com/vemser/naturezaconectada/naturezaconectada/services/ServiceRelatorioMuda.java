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

//    public void deletar(int id) throws Exception {
//        try {
//            this.relatorioRepository.remover(id);
//            System.out.println("Relatório " + id + " excluído com sucesso");
//        }catch (Exception e){
//            System.out.println("Erro ao excluir relatório : " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    public void avaliarRelatorio(Relatorio relatorio){
//        try {
//
//            this.relatorioRepository.avaliarRelatorio(relatorio);
//        }catch (Exception ex){
//            System.out.println("Erro ao Avaliar o relatorio ERRO:" + ex.getMessage());
//            ex.printStackTrace();
//        }
//
//    }
//
//    public void listarRelatorioPorCliente(Integer idCliente){
//        try {
//            this.relatorioRepository.listarRelatorioPorCliente(idCliente);
//        }catch (Exception ex){
//            System.out.println("Erro ao Listar relatorios do cliente , ERRO: "+ ex.getMessage());
//            ex.printStackTrace();
//        }
//    }
//
//
//    public boolean editar(int id, Relatorio relatorio) throws Exception {
//        boolean resultado = false;
//        try {
//            resultado = relatorioRepository.editar(id, relatorio);
//        }catch (Exception ex){
//        System.out.println("Erro: "+ ex.getMessage());
//        ex.printStackTrace();
//    }
//        return resultado;
//    }
//
    public RelatorioMuda procurarPorID(Integer id) throws Exception {

        RelatorioMuda relatorioMuda = this.relatorioMudaRepository.findById(id).orElseThrow(()->new RegraDeNegocioException("Não existe relatório com este id no banco de dados"));

        return relatorioMuda;

    }
//
//    public List<Relatorio> listarTodos() throws Exception {
//        return relatorioRepository.listar();
//    }
//    public Relatorio procurar(int id) throws SQLException {
//        return relatorioRepository.procurarPorId(id);
//    }
//
//    public List<Relatorio> buscarRelatorioAbertos(){
//        try {
//            List<Relatorio> relatorios = this.relatorioRepository.buscarRelatorioAbertos();
//            return relatorios;
//        }catch (Exception ex){
//            System.out.println("Erro ao busar relatorios ERRO: " + ex.getMessage());
//            ex.printStackTrace();
//        }
//        return null;
//    }
    private RelatorioClienteDTO retornarDtoCliente(RelatorioMuda relatorioMuda) {
//        RelatorioClienteDTO retorno = new RelatorioClienteDTO();
//        retorno.setCliente(relatorio.getCliente());
//        retorno.setSugestoes(relatorio.getSugestoes());
//        retorno.setMuda(relatorio.getMuda());
//        retorno.setSugestoes(relatorio.getSugestoes());
//        retorno.setAvaliacao(relatorio.getAvaliacao());
//        retorno.setId(relatorio.getId());
        return this.objectMapper.convertValue(relatorioMuda, RelatorioClienteDTO.class);
    }

    private RelatorioMuda retornarEntidade(RelatorioClienteDTO relatorio) {
        return this.objectMapper.convertValue(relatorio, RelatorioMuda.class);
    }

}