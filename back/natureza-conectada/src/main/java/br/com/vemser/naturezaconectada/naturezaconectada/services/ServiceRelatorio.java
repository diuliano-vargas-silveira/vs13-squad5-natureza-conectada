package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.request.RelatorioRequestDTO;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.RelatorioClienteDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Cliente;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Especialista;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Muda;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Relatorio;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.RelatorioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceRelatorio {

    private final RelatorioRepository relatorioRepository;

    private final ServiceCliente serviceCliente;

    private final ServiceMudas serviceMudas;

    private final ObjectMapper objectMapper;




    public RelatorioClienteDTO adicionar(RelatorioRequestDTO dto) throws Exception {
        Relatorio novoRelatorio = new Relatorio();
        Cliente clienteEncontrado = this.serviceCliente.buscarPorIdEntidade(dto.getIdCliente());
        this.serviceMudas.confereMudaCliente(dto.getIdMuda(),dto.getIdCliente());
        Muda mudaEncontrada = this.serviceMudas.buscarMudaAtiva(dto.getIdMuda());

        novoRelatorio.setCliente(clienteEncontrado);
        novoRelatorio.setMuda(mudaEncontrada);
        novoRelatorio.setAvaliacao(null);
        novoRelatorio.setEspecialista(null);
        novoRelatorio.setSugestoes(null);
        novoRelatorio.setEstadoMuda(dto.getEstadoMuda());

        this.relatorioRepository.save(novoRelatorio);

        return retornarDtoCliente(novoRelatorio);



    }

    public RelatorioClienteDTO editarRelatorio(Integer id,RelatorioRequestDTO dto) throws Exception {
            Relatorio relatorioAtualizado = procurarPorID(id);
            if(relatorioAtualizado.getAvaliacao() != null){
                throw  new RegraDeNegocioException("Este relatório ja foi avaliado e não pode ser editado");
            }else{
                this.serviceMudas.confereMudaCliente(dto.getIdMuda(),dto.getIdCliente());
                relatorioAtualizado.setEstadoMuda(relatorioAtualizado.getEstadoMuda());
                relatorioAtualizado.setMuda(this.serviceMudas.getByEntidade(id));
                return retornarDtoCliente(relatorioAtualizado);
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
    public Relatorio procurarPorID(Integer id) throws Exception {

        Relatorio relatorio = this.relatorioRepository.findById(id).orElseThrow(()->new RegraDeNegocioException("Não existe relatório com este id no banco de dados"));

        return relatorio;

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
    private RelatorioClienteDTO retornarDtoCliente(Relatorio relatorio) {
//        RelatorioClienteDTO retorno = new RelatorioClienteDTO();
//        retorno.setCliente(relatorio.getCliente());
//        retorno.setSugestoes(relatorio.getSugestoes());
//        retorno.setMuda(relatorio.getMuda());
//        retorno.setSugestoes(relatorio.getSugestoes());
//        retorno.setAvaliacao(relatorio.getAvaliacao());
//        retorno.setId(relatorio.getId());
        return this.objectMapper.convertValue(relatorio, RelatorioClienteDTO.class);
    }

    private Relatorio retornarEntidade(RelatorioClienteDTO relatorio) {
        return this.objectMapper.convertValue(relatorio, Relatorio.class);
    }

}