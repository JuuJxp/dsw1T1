package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Empresa;

public interface IEmpresaService {
    Empresa buscarPorId(Long id);
    List<Empresa> buscarTodos();
    void salvar(Empresa empresa);
    void excluir(Long id);
    Empresa buscarPorEmail(String email);
    Empresa buscarPorCnpj(String cnpj);
}