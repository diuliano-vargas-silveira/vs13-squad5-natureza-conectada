package services;

import database.BancoDeDados;
import exceptions.InformacaoNaoEncontrada;
import exceptions.ObjetoExistente;
import interfaces.IService;
import models.Admin;

import java.util.List;
import java.util.Optional;

public class ServiceAdmin implements IService<Admin> {
    @Override
    public void adicionar(Admin admin) {
        Optional<Admin> adminEncontrado = procurarPorEmail(admin.getEmail());

        if (adminEncontrado.isPresent()) {
            throw new ObjetoExistente("Um administrador com este email já foi criado!");
        }

        admin.setId(BancoDeDados.gerarNovoIdAdmin());
        BancoDeDados.usuarios.add(admin);
        BancoDeDados.admins.add(admin);
    }

    @Override
    public void deletar(int id) {
        Optional<Admin> adminEncontrado = procurar(id);
        if (adminEncontrado.isPresent()) {
            BancoDeDados.admins.remove(adminEncontrado.get());
        } else {
            throw new InformacaoNaoEncontrada("Não existe um administrador com este ID");
        }
    }

    @Override
    public boolean editar(int id, Admin adminEditado) {
        Optional<Admin> adminEncontrado = procurar(id);

        if (adminEncontrado.isPresent()) {
            Admin admin = adminEncontrado.get();
            admin.setNome(adminEditado.getNome());
            admin.setEmail(adminEditado.getEmail());
            admin.setSenha(adminEditado.getSenha());
        } else {
            throw new InformacaoNaoEncontrada("Não existe um administrador com este ID");
        }

        return true;
    }

    @Override
    public Admin procurarPorID(int id) {
        Optional<Admin> admin = procurar(id);

        if(admin.isEmpty()){
            throw new InformacaoNaoEncontrada("Não existe nenhum administrador com este ID!");
        }

        return admin.get();
    }

    @Override
    public List<Admin> listarTodos() {
        return BancoDeDados.admins;
    }

    @Override
    public Optional<Admin> procurar(int id) {
        return BancoDeDados.admins.stream().filter(admin -> admin.getId() == id).findFirst();
    }

    public Optional<Admin> procurarPorEmail(String email) {
        return BancoDeDados.admins.stream().filter(admin -> admin.getEmail().equals(email)).findFirst();
    }
}
