package br.ufscar.dc.dsw.config;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

public class UserDetailsServiceImpl implements UserDetailsService {

  private final IUsuarioService usuarioService;

  public UserDetailsServiceImpl(IUsuarioService usuarioService) {
    this.usuarioService = usuarioService;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Usuario usuario = usuarioService.buscarPorEmail(email);

    if (usuario == null) {
      throw new UsernameNotFoundException("Usuário não encontrado: " + email);
    }

    return new org.springframework.security.core.userdetails.User(
      usuario.getEmail(),
      usuario.getSenha(),
      Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getPapel().name()))
    );
  }
}