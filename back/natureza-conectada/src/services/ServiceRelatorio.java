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

        Optional<Relatorio> relatorioExistente = procurar(relatorio.getId());

        if (relatorioExistente.isPresent()) {
            throw new RelatorioExistente();
        }

        relatorio.setId(BancoDeDados.gerarNovoIdRelatorio());
        BancoDeDados.relatorios.add(relatorio);
    }

    @Override
    public void deletar(int id) {
        Relatorio relatorioExistente = procurarPorID(id);

        BancoDeDados.relatorios.remove(relatorioExistente);
    }

    @Override
    public boolean editar(int id, Relatorio relatorio) {
        Relatorio relatorioEncontrado = procurarPorID(id);

        int indexRelatorio = BancoDeDados.relatorios.indexOf(relatorioEncontrado);

        relatorioEncontrado.setDono(relatorio.getDono());
        relatorioEncontrado.setAvaliador(relatorio.getAvaliador());
        relatorioEncontrado.setMuda(relatorio.getMuda());
        relatorioEncontrado.setEstadoMuda(relatorio.getEstadoMuda());
        relatorioEncontrado.setSugestoes(relatorio.getSugestoes());
        relatorioEncontrado.setAvaliacaoEspecialista(relatorio.getAvaliacaoEspecialista());

        BancoDeDados.relatorios.set(indexRelatorio, relatorioEncontrado);

        return true;
    }

    @Override
    public Relatorio procurarPorID(int id) {
        Optional<Relatorio> relatorioPorID = procurar(id);

        if(relatorioPorID.isEmpty()){
            throw  new InformacaoNaoEncontrada("Não existe nenhum relatório com este ID");
        }
        return relatorioPorID.get();
    }

    @Override
    public List<Relatorio> listarTodos() {
        return BancoDeDados.relatorios;
    }

    @Override
    public Optional<Relatorio> procurar(int id) {
        return BancoDeDados.relatorios.stream().filter(relatorio -> relatorio.getId() == id).findFirst();
    }
}