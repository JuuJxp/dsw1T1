package br.ufscar.dc.dsw.config;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import br.ufscar.dc.dsw.conversor.BigDecimalConversor;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import br.ufscar.dc.dsw.conversor.EmpresaConversor;
import br.ufscar.dc.dsw.conversor.ProfissionalConversor;

@Configuration
@ComponentScan(basePackages = "br.ufscar.dc.dsw.config")
public class MvcConfig implements WebMvcConfigurer {   

  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver slr = new SessionLocaleResolver();
    slr.setDefaultLocale(new Locale("pt","BR"));
    return slr;
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
    lci.setParamName("lang");
    return lci;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }
  
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