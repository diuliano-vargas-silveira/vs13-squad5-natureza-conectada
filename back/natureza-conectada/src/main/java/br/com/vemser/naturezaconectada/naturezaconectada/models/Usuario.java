package br.com.vemser.naturezaconectada.naturezaconectada.models;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.Ativo;
import br.com.vemser.naturezaconectada.naturezaconectada.enums.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "Usuario")
@Table(name = "USUARIO")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO_USUARIO", discriminatorType = DiscriminatorType.STRING)
public abstract class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "ID_USUARIO")
    private Integer id;
    @Column(name = "NOME")
    private String nome;
    @Column(name = "EMAIL")
    private String email;

    @JsonIgnore
    @Column(name = "SENHA")
    private String senha;
    @Column(name = "TIPO_USUARIO", updatable = false, insertable = false)
    @Enumerated(EnumType.STRING)
    private TipoUsuario tipoUsuario;

    @Column(name = "ATIVO")
    @Enumerated(EnumType.STRING)
    private Ativo ativo;

    @Column(name = "CPF")
    private String cpf;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(tipoUsuario.getNome()));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return senha;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        if(this.ativo == Ativo.D){
            return  false;
        }else {
            return true;
        }
    }
}