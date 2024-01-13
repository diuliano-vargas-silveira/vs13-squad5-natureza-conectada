package repository;

import enums.Estados;
import models.Contato;
import models.Especialista;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EspecialistaRepository {

    private static final List<Especialista> especialistas = new ArrayList<>();
    private static int nextId = 1;

    public Especialista adicionarEspecialista(Especialista especialista) {
        especialista.setId(nextId++);
        especialistas.add(especialista);
        return especialista;
    }

    public Optional<Especialista> buscarEspecialistaPorID(int id) {
        return especialistas.stream().filter(e -> e.getId() == id).findFirst();
    }

    public List<Especialista> listarTodosEspecialistas() {
        return new ArrayList<>(especialistas);
    }

    public boolean atualizarEspecialista(int id, Especialista especialistaAtualizado) {
        Optional<Especialista> especialistaExistente = buscarEspecialistaPorID(id);

        if (especialistaExistente.isPresent()) {
            int index = especialistas.indexOf(especialistaExistente.get());
            especialistaAtualizado.setId(id);
            especialistas.set(index, especialistaAtualizado);
            return true;
        }

        return false;
    }

    public boolean excluirEspecialista(int id) {
        Optional<Especialista> especialistaExistente = buscarEspecialistaPorID(id);

        if (especialistaExistente.isPresent()) {
            especialistas.remove(especialistaExistente.get());
            return true;
        }

        return false;
    }

    public static void main(String[] args) {

        EspecialistaRepository especialistaRepository = getEspecialistaRepository();

        System.out.println("Lista de Especialistas:");
        especialistaRepository.listarTodosEspecialistas().forEach(System.out::println);

        Optional<Especialista> espEncontrado = especialistaRepository.buscarEspecialistaPorID(1);
        espEncontrado.ifPresent(especialista -> System.out.println("\nEspecialista encontrado por ID: " + especialista));

        especialistaRepository.atualizarEspecialista(1, new Especialista("NovoNome", "emailAtualizado@example.com", "novaSenha",
                new Contato("NovoTelefone", "NovoEndereco"), "NovoDocumento", "NovaEspecializacao", Estados.MG));

        System.out.println("\nLista de Especialistas após Atualização:");
        especialistaRepository.listarTodosEspecialistas().forEach(System.out::println);


        especialistaRepository.excluirEspecialista(2);


        System.out.println("\nLista de Especialistas após Exclusão:");
        especialistaRepository.listarTodosEspecialistas().forEach(System.out::println);
    }

    private static EspecialistaRepository getEspecialistaRepository() {
        EspecialistaRepository especialistaRepository = new EspecialistaRepository();

        // Adicionar especialistas
        Especialista esp1 = new Especialista("Especialista1", "email1@example.com", "senha1",
                new Contato("Telefone1", "Endereco1"), "Documento1", "Especializacao1", Estados.SP);
        especialistaRepository.adicionarEspecialista(esp1);

        Especialista esp2 = new Especialista("Especialista2", "email2@example.com", "senha2",
                new Contato("Telefone2", "Endereco2"), "Documento2", "Especializacao2", Estados.RJ);
        especialistaRepository.adicionarEspecialista(esp2);
        return especialistaRepository;
    }
}
