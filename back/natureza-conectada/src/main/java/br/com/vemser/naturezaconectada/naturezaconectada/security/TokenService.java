package br.com.vemser.naturezaconectada.naturezaconectada.security;

import br.com.vemser.naturezaconectada.naturezaconectada.models.Usuario;
import br.com.vemser.naturezaconectada.naturezaconectada.services.ServiceUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    static final String HEADER_STRING = "Authorization";
    private final ServiceUsuario usuarioService;

    public String getToken(Usuario usuarioEntity) {
        String tokenTexto = usuarioEntity.getEmail() + ";" + usuarioEntity.getSenha();
        String token = Base64.getEncoder().encodeToString(tokenTexto.getBytes());
        return token;
    }
    // token = yeAGieha9eH(E8 = rafa;123
    public Optional<Usuario> isValid(String token) {
        if(token == null){
            return Optional.empty();
        }
        byte[] decodedBytes = Base64.getUrlDecoder().decode(token);
        String decoded = new String(decodedBytes);
        String[] split = decoded.split(";");
        return usuarioService.findByEmailAndSenha(split[0], split[1]);
    }
}
