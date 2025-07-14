package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Profissional;

public interface IProfissionalDAO extends CrudRepository<Profissional, Long> {
    Profissional findByCpf(String cpf);

    @Override
    List<Profissional> findAll();
}