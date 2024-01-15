package services;

import exceptions.BancoDeDadosException;
import exceptions.InformacaoNaoEncontrada;
import interfaces.IService;
import models.Relatorio;
import repository.RelatorioRepository;

import java.sql.SQLException;
import java.util.List;

public class ServiceRelatorio implements IService<Relatorio> {

    RelatorioRepository relatorioRepository = new RelatorioRepository();
    @Override
    public void adicionar(Relatorio relatorio) throws BancoDeDadosException {
        try{
            this.relatorioRepository.adicionar(relatorio);
            System.out.println("Relatório adicionado com sucesso");

        }catch (BancoDeDadosException ex){
            System.out.println("Erro: "+ ex.getMessage());
            ex.printStackTrace();

        }
    }

    @Override
    public void deletar(int id) throws BancoDeDadosException {
        try {
            this.relatorioRepository.remover(id);
            System.out.println("Relatório " + id + " excluído com sucesso");
        }catch (BancoDeDadosException e){
            System.out.println("Erro ao excluir relatório : " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
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

    @Override
    public Relatorio procurarPorID(int id) throws SQLException {

        Relatorio relatorio = procurar(id);

        if (relatorio == null) {
            throw new InformacaoNaoEncontrada("Não existe nenhum relatório com este ID!");
        }

        return relatorio;

    }

    @Override
    public List<Relatorio> listarTodos() throws BancoDeDadosException {
        return relatorioRepository.listar();
    }
    @Override
    public Relatorio procurar(int id) throws SQLException {
        return relatorioRepository.procurarPorId(id);
    }

    /*

    public List<Relatorio>buscarPorCliente (Cliente cliente){
        List<Relatorio> relatorios = BancoDeDados.relatorios.stream().filter(relatorio -> relatorio.getDono().getId() == cliente.getId()).collect(Collectors.toList());

        for (Relatorio r: relatorios) {
            System.out.printf("""
                    Id Relatório: %d
                    muda: %s
                    Relatório feito: %s
                    Sugestão: %s
                    Avaliador: %s
                    Avaliação: %.1f
                    
                    
                    
                    """,r.getId(),r.getMuda().toString(),r.getEstadoMuda(),r.getSugestoes(),r.getAvaliador(),r.getAvaliacaoEspecialista());
        }

        return relatorios;
    }

    public void imprimirRelatorio(Relatorio relatorio) {
        System.out.printf("""
                Id Relatório: %d
                muda: %s
                Relatório feito: %s
                Sugestão: %s
                Avaliador: %s
                Avaliação: %.1f
                                    
                                    
                                    
                """, relatorio.getId(), relatorio.getMuda().toString(), relatorio.getEstadoMuda(), relatorio.getSugestoes(), relatorio.getAvaliacaoEspecialista(), relatorio.getAvaliador());

    }

    public List<Relatorio> procurarRelatoriosSemAvaliador() {
        return BancoDeDados.relatorios.stream().filter(relatorio -> relatorio.getAvaliador() == null).toList();

    }
*/
}