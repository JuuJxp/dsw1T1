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
          .requestMatchers("/", "/home", "/error", "/login/**", "/usuarios/novo", "/profissionais/cadastrar", "/empresas/cadastrar").permitAll()
          .requestMatchers("/profissionais/salvar", "/empresas/salvar").permitAll() // Permite POST para salvar novos cadastros
          .requestMatchers("/vagas/listar**").permitAll()

          .requestMatchers("/administradores/**", "/empresas/listar", "/empresas/editar/**", "/empresas/excluir/**", "/profissionais/listar", "/profissionais/editar/**", "/profissionais/excluir/**").hasRole("ADMIN")

          .requestMatchers("/vagas/cadastrar", "/vagas/salvar", "/vagas/minhasVagas", "/vagas/editar/**", "/vagas/excluir/**").hasRole("EMPRESA")
          .requestMatchers("/candidaturas/gerenciar/**", "/candidaturas/atualizarStatus").hasRole("EMPRESA")

          .requestMatchers("/candidaturas/candidatar/**", "/candidaturas/minhasCandidaturas").hasRole("PROFISSIONAL")

          .anyRequest().authenticated()
        )
        .formLogin(form -> form
          .loginPage("/login") 
          .defaultSuccessUrl("/", true)
          .failureUrl("/login?error=true") 
          .permitAll()
        )
        .logout(logout -> logout
          .logoutSuccessUrl("/") 
          .permitAll()
        );
    return http.build();
  }
}