package enums;


public enum TipoMuda {
    PLANTA,
    ARVORE;



        public static TipoMuda ofTipo(Integer tipo) {
            for(TipoMuda tm : TipoMuda.values()) {
                if(tm.ordinal() == tipo) {
                    return tm;
                }
            }
            return null;
        }
    }

