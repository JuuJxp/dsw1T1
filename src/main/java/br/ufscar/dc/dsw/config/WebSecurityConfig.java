package br.ufscar.dc.dsw.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; 

import br.ufscar.dc.dsw.service.spec.IUsuarioService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Autowired
  private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth, IUsuarioService usuarioService) throws Exception {
    auth.userDetailsService(new UserDetailsServiceImpl(usuarioService));
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
          .requestMatchers("/webjars/**", "/css/**", "/image/**", "/js/**").permitAll()
          // por enquanto deixar sem precisar logar essas rotas (depois precisa de login como admin)
          .requestMatchers("/", "/error", "/login/**", "/usuarios/novo", "/profissionais/cadastrar", "/empresas/cadastrar").permitAll()
          .requestMatchers("/profissionais/salvar", "/empresas/salvar").permitAll()
          .requestMatchers("/vagas/listar**").permitAll()

          .requestMatchers("/perfilAdministrador", "/profissionais/cadastrar", "/empresas/cadastrar", "/usuarios/novo", "/profissionais/salvar", "/empresas/salvar", "/empresas/listar", "/empresas/editar/**", "/empresas/excluir/**", "/profissionais/listar", "/profissionais/excluir/**").hasRole("ADMIN")

          .requestMatchers("/vagas/cadastrar", "/vagas/salvar", "/vagas/minhasVagas", "/vagas/editar/**", "/vagas/excluir/**", "/perfilEmpresa").hasRole("EMPRESA")
          .requestMatchers("/candidaturas/gerenciar/**", "/candidaturas/atualizarStatus").hasRole("EMPRESA")

          .requestMatchers("/candidaturas/candidatar/**", "/perfilProfissional","/candidaturas/minhasCandidaturas", "/candidaturas/salvar/**").hasRole("PROFISSIONAL")

          .anyRequest().authenticated()
        )
        .formLogin(form -> form
          .loginPage("/login") 
          .successHandler(customAuthenticationSuccessHandler)
          .failureUrl("/erro?msg=Usuario ou senha invalida") 
          .permitAll()
        )
        .logout(logout -> logout
          .logoutSuccessUrl("/") 
          .permitAll()
        )
        .exceptionHandling(exceptions -> exceptions
          .accessDeniedPage("/acessoNegado") 
        );
    return http.build();
  }
}