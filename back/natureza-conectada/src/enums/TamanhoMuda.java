package enums;

public enum TamanhoMuda {
    PEQUENO,
    MEDIO,
    GRANDE;

    public static TamanhoMuda ofTipo(Integer tipo) {
        for(TamanhoMuda tm : TamanhoMuda.values()) {
            if(tm.ordinal() == tipo) {
                return tm;
            }
        }
        return null;
    }
}
