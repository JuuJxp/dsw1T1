package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import org.springframework.validation.Errors;

import br.ufscar.dc.dsw.domain.Usuario;

public interface IUsuarioService {
    Usuario buscarPorId(Long id);
    Usuario buscarPorEmail(String email);
    List<Usuario> buscarTodos();
    void salvar(Usuario usuario);
    void excluir(Long id);
    void validarEmailUnico(Usuario usuario, Errors errors);
}
