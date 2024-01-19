package br.com.vemser.naturezaconectada.naturezaconectada.interfaces;

import br.com.vemser.naturezaconectada.naturezaconectada.enums.StatusEntrega;

public interface IEntrega {
    public void atualizarEntrega(StatusEntrega status);
    public void consultarMudaEntrega();
}