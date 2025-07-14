package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import br.ufscar.dc.dsw.dao.IEmpresaDAO;
import br.ufscar.dc.dsw.domain.Empresa;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.spec.IEmpresaService;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

@Service
@Transactional(readOnly = false)
public class EmpresaService implements IEmpresaService {

    @Autowired
    IEmpresaDAO dao;

    @Autowired
    private IUsuarioService usuarioService;

    public void salvar(Empresa empresa) {
        usuarioService.salvar(empresa);
    }

    public void validarCamposUnicos(Empresa empresa, Errors errors) {
        usuarioService.validarEmailUnico(empresa, errors);

        Empresa empresaPorCnpj = dao.findByCnpj(empresa.getCnpj());
        if (empresaPorCnpj != null && !empresaPorCnpj.getId().equals(empresa.getId())) {
            errors.rejectValue("cnpj", "Unique.empresa.cnpj", "CNPJ já cadastrado.");
        }
    }
    
    public void excluir(Long id) {
        dao.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Empresa buscarPorId(Long id) {
        return dao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Empresa> buscarTodos() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public Empresa buscarPorEmail(String email) {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        
        if (usuario instanceof Empresa) {
            return (Empresa) usuario;
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Empresa buscarPorCnpj(String cnpj) {
        return dao.findByCnpj(cnpj);
    }

    @Transactional(readOnly = true)
    public List<Empresa> buscarTodasPorCidade(String cidade) {
        return dao.findAllByCidade(cidade);
    }
}