package br.com.vemser.naturezaconectada.naturezaconectada.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.dto.response.UsuarioResponseDTO;

public interface IServiceUsuario {
    UsuarioResponseDTO procurarPorEmail(String email) throws Exception;
}