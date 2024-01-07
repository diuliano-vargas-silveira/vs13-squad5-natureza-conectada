package models;

import enums.StatusEntrega;
import interfaces.IEntrega;

import java.util.ArrayList;
import java.util.List;

public class Entrega implements IEntrega {
    private int id;
    private List<Muda> mudas = new ArrayList<>();
    private StatusEntrega status;
    private Cliente cliente;

    private Endereco enderecoDeEntrega;

    public Entrega(){

    }
    public Entrega( List<Muda> mudas, StatusEntrega status, Cliente cliente){

        this.mudas = mudas;
        this.status = status;
        this.cliente = cliente ;
    }

    public Endereco getEnderecoDeEntrega() {
        return enderecoDeEntrega;
    }

    public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
        this.enderecoDeEntrega = enderecoDeEntrega;
    }

    @Override
    public void atualizarEntrega(StatusEntrega status) {
        this.status = status;
        System.out.printf("O Status da entrega: %d, foi atualizada para: %s. \n",
                this.id, String.valueOf(this.status).toLowerCase());
    }

    @Override
    public void consultarMudaEntrega() {
        System.out.printf("Descrições da(s) muda(s) da entrega %d:\n", this.id);

        for(Muda muda : this.mudas){
            System.out.println(muda.toString());
        }
    }

    @Override
    public String toString(){

        String mensagemMudas = "";
        for(Muda muda : this.mudas){ mensagemMudas += muda.toString() + "\n";}
        return "ID da entrega: " + this.id
        + "\nDescrições da(s) muda(s) da entrega: " + mensagemMudas+
                "\n Endereço de entrega: "+this.enderecoDeEntrega

        +"\nStatus: " + String.valueOf(this.status)
        + "\nDados do cliente: " + this.cliente.toString() + "\n";
    }

    public void setId(int id) {this.id = id;}
    public void setMudas(List<Muda> mudas) {this.mudas = mudas;}
    public void setStatus(StatusEntrega status) {this.status = status;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public int getId() {return this.id;}
    public List<Muda> getMudas() {return this.mudas;}
    public StatusEntrega getStatus() {return this.status;}
    public Cliente getCliente() {return this.cliente;}
}