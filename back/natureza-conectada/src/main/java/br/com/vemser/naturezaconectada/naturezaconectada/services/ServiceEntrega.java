
package br.com.vemser.naturezaconectada.naturezaconectada.services;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.Exception;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Entrega;
import br.com.vemser.naturezaconectada.naturezaconectada.repository.EntregaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceEntrega {

    private EntregaRepository entregaRepository;

    public ServiceEntrega(EntregaRepository entregaRepository) {
        this.entregaRepository = entregaRepository;
    }

    public void adicionar(Entrega entrega, Integer idEndereco) {
        try{
            this.entregaRepository.adicionar(entrega, idEndereco);
            System.out.println("*** Entrega adicionada com sucesso ****");
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    public void deletar(Integer id) {
        try {
            this.entregaRepository.remover(id);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void editar(int id, Entrega entregaAtualizada) {
        try{
            this.entregaRepository.editar(id, entregaAtualizada);
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public void listarTodos() {
        List<Entrega> listaEntrega = new ArrayList<>();
        try {
            listaEntrega = this.entregaRepository.listar();
        }catch(Exception e) {
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