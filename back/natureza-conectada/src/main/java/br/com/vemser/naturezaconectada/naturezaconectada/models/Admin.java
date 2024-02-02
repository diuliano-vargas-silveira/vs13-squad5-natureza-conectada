package br.com.vemser.naturezaconectada.naturezaconectada.models;

import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity(name = "Admin")
@Table(name = "ADMIN")
@DiscriminatorValue("ADMIN")
public class Admin extends Usuario {

}
