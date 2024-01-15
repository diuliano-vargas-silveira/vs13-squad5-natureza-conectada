
package services;


import exceptions.BancoDeDadosException;
import models.Muda;
import repository.ConexaoBancoDeDados;
import repository.MudaRepository;

import java.util.ArrayList;
import java.util.List;

public class ServiceMudas  {
    MudaRepository mudaRepository = new MudaRepository();


    public void adicionar(Muda muda){
        try{
            this.mudaRepository.adicionar(muda);
            System.out.println("*** Muda adicionada com sucesso ****");
        }catch(BancoDeDadosException ex){
            System.out.println("Erro ao adicionar a muda, Erro: " + ex.getCause());
            ex.printStackTrace();
            ex.getMessage();
        }

    }

    public void remover (Integer idMuda){
        try {

            this.mudaRepository.remover(idMuda);
            System.out.println("***** muda Removida *****");

    }catch(BancoDeDadosException ex){
            System.out.println("Erro ao adicionar ao remover a Muda, Erro: " + ex.getCause());
            ex.printStackTrace();
            ex.getMessage();
        }

    }

    public void editarmuda(Integer idMuda,Muda muda)  {
        try{
            this.mudaRepository.editar(idMuda,muda);
            System.out.println("***** Mudada Editada ********");
        }catch (BancoDeDadosException e){
            System.out.println("Erro ao editar Muda ERRO: "+ e.getMessage());
            e.printStackTrace();

        }
    }

    public List<Muda> listarMudas(){
        List listaDeMudas = new ArrayList<>();
        try {
            listaDeMudas = this.mudaRepository.listar();
        }catch(BancoDeDadosException e){
            System.out.println("Ocorreu um erro ao LISTAR as mudas, ERRO: "+e.getMessage());

        }
        return listaDeMudas;
    }

    public Muda buscarPorId(Integer idMuda){
        try {
            Muda muda = this.mudaRepository.buscarPorId(idMuda);
            return muda;
        }catch (BancoDeDadosException e){
            System.out.println("Erro ao buscar no Banco de dados");
            e.printStackTrace();
        }
        return null;
    }
}
