package br.ufscar.dc.dsw.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.ufscar.dc.dsw.domain.Usuario;

@SuppressWarnings("serial")
public class UsuarioDetails implements UserDetails {

  private Usuario usuario;

  public UsuarioDetails(Usuario usuario) {
    this.usuario = usuario;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getPapel().name());
    return Arrays.asList(authority);
  }

  @Override
  public String getPassword() {
    return usuario.getSenha();
  }

  @Override
  public String getUsername() {
    return usuario.getEmail();
  }

  @Override
  public boolean isEnabled() {
    return usuario.isAtivo(); 
  }

  public Usuario getUsuario() {
    return usuario;
  }
}