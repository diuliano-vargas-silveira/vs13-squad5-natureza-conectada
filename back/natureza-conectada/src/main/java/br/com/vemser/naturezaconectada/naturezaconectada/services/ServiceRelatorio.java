package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.BancoDeDadosException;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.InformacaoNaoEncontrada;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Relatorio;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.RelatorioRepository;


import java.sql.SQLException;
import java.util.List;

public class ServiceRelatorio {

    RelatorioRepository relatorioRepository = new RelatorioRepository();
    public void adicionar(Relatorio relatorio, Integer idCliente, Integer idEspecialista, Integer idMuda) throws BancoDeDadosException {
        try{
            this.relatorioRepository.adicionar(relatorio, idCliente, idEspecialista, idMuda);
            System.out.println("Relatório adicionado com sucesso");

        }catch (BancoDeDadosException ex){
            System.out.println("Erro: "+ ex.getMessage());
            ex.printStackTrace();

        }
    }

    public void deletar(int id) throws BancoDeDadosException {
        try {
            this.relatorioRepository.remover(id);
            System.out.println("Relatório " + id + " excluído com sucesso");
        }catch (BancoDeDadosException e){
            System.out.println("Erro ao excluir relatório : " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void avaliarRelatorio(Relatorio relatorio){
        try {

            this.relatorioRepository.avaliarRelatorio(relatorio);
        }catch (BancoDeDadosException ex){
            System.out.println("Erro ao Avaliar o relatorio ERRO:" + ex.getMessage());
            ex.printStackTrace();
        }

    }

    public void listarRelatorioPorCliente(Integer idCliente){
        try {
            this.relatorioRepository.listarRelatorioPorCliente(idCliente);
        }catch (BancoDeDadosException ex){
            System.out.println("Erro ao Listar relatorios do cliente , ERRO: "+ ex.getMessage());
            ex.printStackTrace();
        }
    }


    public boolean editar(int id, Relatorio relatorio) throws BancoDeDadosException {
        boolean resultado = false;
        try {
            resultado = relatorioRepository.editar(id, relatorio);
        }catch (BancoDeDadosException ex){
        System.out.println("Erro: "+ ex.getMessage());
        ex.printStackTrace();
    }
        return resultado;
    }

    public Relatorio procurarPorID(int id) throws SQLException {

        Relatorio relatorio = procurar(id);

        if (relatorio == null) {
            throw new InformacaoNaoEncontrada("Não existe nenhum relatório com este ID!");
        }

        return relatorio;

    }

    public List<Relatorio> listarTodos() throws BancoDeDadosException {
        return relatorioRepository.listar();
    }
    public Relatorio procurar(int id) throws SQLException {
        return relatorioRepository.procurarPorId(id);
    }

    public List<Relatorio> buscarRelatorioAbertos(){
        try {
            List<Relatorio> relatorios = this.relatorioRepository.buscarRelatorioAbertos();
            return relatorios;
        }catch (BancoDeDadosException ex){
            System.out.println("Erro ao busar relatorios ERRO: " + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

}