package br.com.vemser.naturezaconectada.naturezaconectada.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity(name = "Admin")
@Table(name = "ADMIN")
@DiscriminatorValue("ADMIN")
public class Admin extends Usuario {

}
