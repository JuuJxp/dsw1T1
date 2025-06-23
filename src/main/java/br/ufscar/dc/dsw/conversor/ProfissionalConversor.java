package br.ufscar.dc.dsw.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.service.spec.IProfissionalService;

@Component
public class ProfissionalConversor implements Converter<String, Profissional> {

  @Autowired
  private IProfissionalService service;

  @Override
  public Profissional convert(String text) {
    if (text.isEmpty()) {
      return null;
    }
    Long id = Long.valueOf(text);
    return service.buscarPorId(id);
  }
}