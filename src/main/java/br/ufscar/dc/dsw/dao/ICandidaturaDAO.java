package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Candidatura;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.domain.Vaga;

@SuppressWarnings("unchecked")
public interface ICandidaturaDAO extends CrudRepository<Candidatura, Long> {
    Candidatura findById(long id);
    List<Candidatura> findByProfissional(Profissional profissional);
    Candidatura findByProfissionalAndVaga(Profissional profissional, Vaga vaga);
    List<Candidatura> findByVaga(Vaga vaga);
    Candidatura save(Candidatura candidatura);
    void deleteById(Long id);
}