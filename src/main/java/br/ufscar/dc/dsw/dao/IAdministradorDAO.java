package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Administrador;

@SuppressWarnings("unchecked")
public interface IAdministradorDAO extends CrudRepository<Administrador, Long> {
    Administrador findById(long id);
    Administrador findByEmail(String email);
    List<Administrador> findAll();
    Administrador save(Administrador administrador);
    void deleteById(Long id);
}