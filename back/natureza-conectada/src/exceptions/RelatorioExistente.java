package exceptions;

public class RelatorioExistente extends RuntimeException {
    public RelatorioExistente() {
        super("Este relatório já existe");
    }
}
