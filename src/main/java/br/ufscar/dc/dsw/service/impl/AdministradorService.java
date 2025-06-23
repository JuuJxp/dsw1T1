package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IAdministradorDAO;
import br.ufscar.dc.dsw.domain.Administrador;
import br.ufscar.dc.dsw.service.spec.IAdministradorService;

@Service
@Transactional(readOnly = false)
public class AdministradorService implements IAdministradorService {

    @Autowired
    IAdministradorDAO dao;

    public void salvar(Administrador administrador) {
        dao.save(administrador);
    }

    public void excluir(Long id) {
        dao.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Administrador buscarPorId(Long id) {
        return dao.findById(id.longValue());
    }

    @Transactional(readOnly = true)
    public List<Administrador> buscarTodos() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public Administrador buscarPorEmail(String email) {
        return dao.findByEmail(email);
    }
}