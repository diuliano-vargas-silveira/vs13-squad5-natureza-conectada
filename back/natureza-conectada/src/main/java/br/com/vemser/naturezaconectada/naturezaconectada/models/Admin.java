package br.com.vemser.naturezaconectada.naturezaconectada.models;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Admin")
@Table(name = "ADMIN")
public class Admin extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADMIN_SEQ")
    @SequenceGenerator(name = "ADMIN_SEQ", sequenceName = "seq_admin", allocationSize = 1)
    @Column(name = "ID_ADMIN")
    private Integer idAdmin;

}
