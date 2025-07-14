package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

@Service
@Transactional(readOnly = false)
public class UsuarioService implements IUsuarioService {

	@Autowired
	IUsuarioDAO dao;

	@Autowired
  private BCryptPasswordEncoder passwordEncoder;

	public void salvar(Usuario usuario) {
		if (usuario.getId() == null) {
			usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		} else {
			Usuario usuarioExistente = dao.findById(usuario.getId()).orElse(null);
			if (usuario.getSenha() == null || usuario.getSenha().isEmpty()) {
				if (usuarioExistente != null) {
					usuario.setSenha(usuarioExistente.getSenha());
				}
			} else {
				usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
			}
		}
		dao.save(usuario);
	}
	
	public void validarEmailUnico(Usuario usuario, Errors errors) {
		Usuario usuarioPorEmail = dao.findByEmail(usuario.getEmail());

		if (usuarioPorEmail != null && !usuarioPorEmail.getId().equals(usuario.getId())) {
			errors.rejectValue("email", "Unique.usuario.email", "E-mail já cadastrado.");
		}
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}
	
	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return dao.findById(id).orElse(null);
	}
	
	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos() {
		return dao.findAll();
	}

    @Transactional(readOnly = true)
    public Usuario buscarPorEmail(String email) {
        return dao.findByEmail(email);
    }
}