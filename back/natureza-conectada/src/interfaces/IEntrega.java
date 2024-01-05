package interfaces;

import enums.Status;

public interface IEntrega {
    public void atualizarEntrega(Status status);
    public void consultarMuldaEntrega();
}