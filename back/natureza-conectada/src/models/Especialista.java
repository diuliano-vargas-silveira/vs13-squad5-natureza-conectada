package models;

public class Especialista {

        private int ID;
        private String especialista;
        private Contato contato;
        private String documento;
        private String especializacao;
        private String regiaoResponsavel;

        public Especialista(int ID, String especialista, Contato contato, String documento,
                            String especializacao, String regiaoResponsavel) {
            this.ID = ID;
            this.especialista = especialista;
            this.contato = contato;
            this.documento = documento;
            this.especializacao = especializacao;
            this.regiaoResponsavel = regiaoResponsavel;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getEspecialista() {
            return especialista;
        }

        public void setEspecialista(String especialista) {
            this.especialista = especialista;
        }

        public Contato getContato() {
            return contato;
        }

        public void setContato(Contato contato) {
            this.contato = contato;
        }

        public String getDocumento() {
            return documento;
        }

        public void setDocumento(String documento) {
            this.documento = documento;
        }

        public String getEspecializacao() {
            return especializacao;
        }

        public void setEspecializacao(String especializacao) {
            this.especializacao = especializacao;
        }

        public String getRegiaoResponsavel() {
            return regiaoResponsavel;
        }

        public void setRegiaoResponsavel(String regiaoResponsavel) {
            this.regiaoResponsavel = regiaoResponsavel;
        }

        @Override
        public String toString() {
            return "Especialista{" +
                    "ID=" + ID +
                    ", especialista='" + especialista + '\'' +
                    ", contato=" + contato +
                    ", documento='" + documento + '\'' +
                    ", especializacao='" + especializacao + '\'' +
                    ", regiaoResponsavel='" + regiaoResponsavel + '\'' +
                    '}';
        }
}
