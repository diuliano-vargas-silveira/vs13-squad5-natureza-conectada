package br.com.vemser.naturezaconectada.naturezaconectada.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "Cliente")
@Table(name = "CLIENTE")
public class Cliente extends Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENTE_SEQ")
    @SequenceGenerator(name = "CLIENTE_SEQ", sequenceName = "seq_cliente", allocationSize = 1)
    @Column(name = "ID_CLIENTE")
    private Integer idCliente;
    @Column(name = "CPF")
    private String cpf;
    private List<Endereco> enderecos = new ArrayList<>();
    private List<Contato> contatos = new ArrayList<>();
    private List<Muda> mudas = new ArrayList<>();
    private List<Entrega> entregas = new ArrayList<>();
}