package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Empresa;

public interface IEmpresaDAO extends CrudRepository<Empresa, Long> {
    Empresa findByCnpj(String cnpj);

    @Override
    List<Empresa> findAll();
}