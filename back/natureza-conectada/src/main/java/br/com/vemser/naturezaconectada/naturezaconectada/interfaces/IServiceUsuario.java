package br.com.vemser.naturezaconectada.naturezaconectada.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO;
import br.com.vemser.naturezaconectada.naturezaconectada.exceptions.RegraDeNegocioException;

public interface IServiceUsuario {
    UsuarioResponseDTO procurarPorEmail(String email) throws Exception;
}