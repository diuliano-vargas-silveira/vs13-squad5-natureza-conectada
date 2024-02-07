package br.com.vemser.naturezaconectada.naturezaconectada.repository;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.relatorios.RelatorioQuantidadeUsuario;
import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT new br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO" +
            "(u.id, u.nome, u.email, u.tipoUsuario, u.ativo, u.cpf, u.senha) " +
            "FROM Usuario u " +
            "WHERE u.ativo = 'A'")
    List<UsuarioResponseDTO> findAllUsuariosAtivos();


    @Query(value = "SELECT TIPO_USUARIO AS Usuario,COUNT(TIPO_USUARIO)AS Quantidade  FROM VS_13_EQUIPE_5.USUARIO u GROUP BY TIPO_USUARIO  ",nativeQuery = true)
    List<RelatorioQuantidadeUsuario> relatorioParaAdmin();

    Optional<Usuario> findByEmailAndSenha(String email, String senha);
    Optional<Usuario> findByEmail(String email);
}
