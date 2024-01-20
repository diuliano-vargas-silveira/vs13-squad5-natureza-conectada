
package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.BancoDeDadosException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Entrega;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EntregaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceEntrega {

    private EntregaRepository entregaRepository = new EntregaRepository();

    public void adicionar(Entrega entrega, Integer idEndereco) {
        try{
            this.entregaRepository.adicionar(entrega, idEndereco);
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