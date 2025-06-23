package br.ufscar.dc.dsw.config;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    String redirectUrl = "/"; 
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

    // Verifica o papel e define a URL de redirecionamento
    if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
        redirectUrl = "/perfilAdministrador";
    } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_EMPRESA"))) {
        redirectUrl = "/perfilEmpresa";
    } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROFISSIONAL"))) {
        redirectUrl = "/perfilProfissional";
    }

    response.sendRedirect(request.getContextPath() + redirectUrl);
  }
}