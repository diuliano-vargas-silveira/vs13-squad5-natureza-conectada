package services;

import database.BancoDeDados;
import exceptions.InformacaoNaoEncontrada;
import exceptions.RelatorioExistente;
import interfaces.IService;
import models.Endereco;
import models.Especialista;
import models.Muda;
import models.Relatorio;

import java.util.List;
import java.util.Optional;

public class ServiceRelatorio implements IService<Relatorio> {

    @Override
    public void adicionar(Relatorio relatorio) {
        if (relatorio == null) {
            throw new IllegalArgumentException("Relatório não pode ser nulo");
        }

        Optional<Relatorio> relatorioExistente = procurarPorID(relatorio.getId());

        if (relatorioExistente.isPresent()) {
            throw new RelatorioExistente();
        }

        relatorio.setId(BancoDeDados.gerarNovoIdRelatorio());
        BancoDeDados.relatorios.add(relatorio);
    }

    @Override
    public void deletar(int id) {
        Optional<Relatorio> relatorioExistente = procurarPorID(id);

        if (relatorioExistente.isEmpty())
            throw new InformacaoNaoEncontrada("Este relatório não existe.");

        BancoDeDados.relatorios.remove(relatorioExistente.get());
    }

    @Override
    public boolean editar(int id, Relatorio relatorio) {
        Optional<Relatorio> relatorioExistente = procurarPorID(id);

        if (relatorioExistente.isEmpty())
            throw new InformacaoNaoEncontrada("Este relatório não existe.");

        int indexRelatorio = BancoDeDados.relatorios.indexOf(relatorioExistente.get());

        relatorioExistente.get().setDono(relatorio.getDono());
        relatorioExistente.get().setAvaliador(relatorio.getAvaliador());
        relatorioExistente.get().setAvaliador(relatorio.getAvaliador());
        relatorioExistente.get().setMuda(relatorio.getMuda());
        relatorioExistente.get().setEstadoMuda(relatorio.getEstadoMuda());
        relatorioExistente.get().setSugestoes(relatorio.getSugestoes());
        relatorioExistente.get().setAvaliacaoEspecialista(relatorio.getAvaliacaoEspecialista());

        BancoDeDados.relatorios.set(indexRelatorio, relatorioExistente.get());

        return true;
    }

    @Override
    public Optional<Relatorio> procurarPorID(int id) {
        Optional<Relatorio> relatorioPorID = BancoDeDados.relatorios.stream().filter(md -> md.getId() == id).findFirst();

        if(relatorioPorID.isEmpty()){
            throw  new InformacaoNaoEncontrada("Não existe nenhum relatório com este ID");
        }
        return relatorioPorID;
    }

    @Override
    public List<Relatorio> listarTodos() {
        return BancoDeDados.relatorios;
    }

}
