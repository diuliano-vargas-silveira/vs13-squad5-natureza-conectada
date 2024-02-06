package br.com.vemser.naturezaconectada.naturezaconectada.controllers;

import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;
import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;
import br.com.vemser.naturezaconectada.naturezaconectada.security.LoginDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.security.TokenService;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {
    private final ServiceUsuario usuarioService;
    private final TokenService tokenService;

    @PostMapping
    public String auth(@RequestBody @Valid LoginDTO loginDTO) throws RegraDeNegocioException {
        Optional<Usuario> byEmailAndSenha = usuarioService.findByEmailAndSenha(loginDTO.getEmail(), loginDTO.getSenha());
        if (byEmailAndSenha.isPresent()) {
            return tokenService.getToken(byEmailAndSenha.get());
        } else {
            throw new RegraDeNegocioException("Usuário ou senha inválidos");
        }
    }
}
