package br.ufscar.dc.dsw.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.ufscar.dc.dsw.conversor.BigDecimalConversor;
import br.ufscar.dc.dsw.conversor.EmpresaConversor;
import br.ufscar.dc.dsw.conversor.ProfissionalConversor;

@Configuration
@ComponentScan(basePackages = "br.ufscar.dc.dsw.config")
public class MvcConfig implements WebMvcConfigurer {   
  
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/home").setViewName("home");
    registry.addViewController("/").setViewName("index");
    registry.addViewController("/login").setViewName("login");
    registry.addViewController("/perfilAdministrador").setViewName("perfilAdministrador");
    registry.addViewController("/perfilEmpresa").setViewName("perfilEmpresa");
    registry.addViewController("/perfilProfissional").setViewName("perfilProfissional");
  }

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(new BigDecimalConversor());
    registry.addConverter(new EmpresaConversor());
    registry.addConverter(new ProfissionalConversor());
  }
}