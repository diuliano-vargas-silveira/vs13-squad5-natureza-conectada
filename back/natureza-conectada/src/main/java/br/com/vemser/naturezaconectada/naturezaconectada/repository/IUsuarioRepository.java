package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByEmail(String email);

    @Query("SELECT new br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO" +
            "(u.id, u.nome, u.email, u.tipoUsuario, u.ativo) " +
            "FROM Usuario u " +
            "WHERE u.ativo = 'A'")
    List<UsuarioResponseDTO> findAllUsuariosAtivos();
}
