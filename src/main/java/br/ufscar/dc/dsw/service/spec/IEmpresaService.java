package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import org.springframework.validation.Errors;

import br.ufscar.dc.dsw.domain.Empresa;

public interface IEmpresaService {
    Empresa buscarPorId(Long id);
    List<Empresa> buscarTodos();
    void salvar(Empresa empresa);
    void excluir(Long id);
    Empresa buscarPorEmail(String email);
    Empresa buscarPorCnpj(String cnpj);
    void validarCamposUnicos(Empresa empresa, Errors errors);
    List<Empresa> buscarTodasPorCidade(String cidade);
}