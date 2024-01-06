package interfaces;

import enums.StatusEntrega;

public interface IEntrega {
    public void atualizarEntrega(StatusEntrega status);
    public void consultarMuldaEntrega();
}