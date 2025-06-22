package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Administrador;

public interface IAdministradorService {
    Administrador buscarPorId(Long id);
    List<Administrador> buscarTodos();
    void salvar(Administrador administrador);
    void excluir(Long id);
    Administrador buscarPorEmail(String email);
}