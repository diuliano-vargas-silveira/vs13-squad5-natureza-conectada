package br.com.vemser.naturezaconectada.naturezaconectada.services;

import exceptions.BancoDeDadosException;
import exceptions.InformacaoNaoEncontrada;
import interfaces.IService;
import models.Admin;
import models.Usuario;
import repository.AdminRepository;

import java.sql.SQLException;
import java.util.List;

public class ServiceAdmin implements IService<Admin> {

    AdminRepository adminRepository = new AdminRepository();

    ServiceUsuario serviceUsuario = new ServiceUsuario();

    @Override
    public void adicionar(Admin admin) throws BancoDeDadosException {
        Usuario usuarioCriado = serviceUsuario.adicionarUsuario(admin);

        admin.setId(usuarioCriado.getId());
        adminRepository.adicionar(admin);
    }

    @Override
    public void deletar(int id) throws SQLException {
        Admin admin = procurarPorID(id);

        adminRepository.remover(id);
        serviceUsuario.remover(admin.getId());
    }

    @Override
    public boolean editar(int id, Admin adminEditado) throws BancoDeDadosException {
        return serviceUsuario.editar(adminEditado.getId(), adminEditado);
    }

    @Override
    public Admin procurarPorID(int id) throws SQLException {
        Admin admin = procurar(id);

        if (admin == null) {
            throw new InformacaoNaoEncontrada("NÃ£o existe nenhum administrador com este ID!");
        }

        return admin;
    }

    @Override
    public List<Admin> listarTodos() throws BancoDeDadosException {
        return adminRepository.listar();
    }

    @Override
    public Admin procurar(int id) throws SQLException {
        return adminRepository.procurarPorId(id);
    }
}