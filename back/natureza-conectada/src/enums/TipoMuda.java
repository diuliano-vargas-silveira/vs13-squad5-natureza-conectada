package enums;


import exceptions.EnumNaoExistente;

public enum TipoMuda {
    PLANTA,
    ARVORE;



        public static TipoMuda ofTipo(Integer tipo) {
            return switch (tipo) {
                case 1 -> TipoMuda.PLANTA;
                case 2 -> TipoMuda.ARVORE;
                default -> throw new EnumNaoExistente("Este tipo de muda não existe!");
            };
        }
    }

