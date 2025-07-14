package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Usuario;

public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {
    Usuario findByEmail(String email);

    @Override
    List<Usuario> findAll();
}
