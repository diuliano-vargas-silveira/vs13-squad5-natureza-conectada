
package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exceptions.BancoDeDadosException;
import exceptions.InformacaoNaoEncontrada;
import exceptions.ObjetoExistente;
import interfaces.IService;
import models.Entrega;
import repository.EntregaRepository;

public class ServiceEntrega {

    private EntregaRepository entregaRepository = new EntregaRepository();

    public void adicionar(Entrega entrega) {
        try{
            this.entregaRepository.adicionar(entrega);
            System.out.println("*** Entrega adicionada com sucesso ****");
        }catch(BancoDeDadosException ex){
            ex.printStackTrace();
        }
    }


    public void deletar(Integer id) {
        try {
            this.entregaRepository.remover(id);
        }catch(BancoDeDadosException ex){
            ex.printStackTrace();
        }
    }

    public void editar(int id, Entrega entregaAtualizada) {
        try{
            this.entregaRepository.editar(id, entregaAtualizada);
        }catch (BancoDeDadosException e){
            e.printStackTrace();

        }
    }

    public void listarTodos() {
        List<Entrega> listaEntrega = new ArrayList<>();
        try {
            listaEntrega = this.entregaRepository.listar();
        }catch(BancoDeDadosException e) {
            e.printStackTrace();
        }
        if(listaEntrega.size() == 0){
            System.out.println("Não existe entregas registradas.");
        }
        else{
            for(Entrega entregaAtual : listaEntrega){
                System.out.println(entregaAtual.toString());
            }
        }
    }
}