package br.ufscar.dc.dsw.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Empresa;
import br.ufscar.dc.dsw.domain.Vaga;

@SuppressWarnings("unchecked")
public interface IVagaDAO extends CrudRepository<Vaga, Long> {
    Vaga findById(long id);
    List<Vaga> findAll();
    List<Vaga> findByEmpresa(Empresa empresa);
    List<Vaga> findByTitulo(String titulo);
    List<Vaga> findByDataLimiteInscricaoAfter(LocalDate data);
    List<Vaga> findByDataLimiteInscricaoAfterAndEmpresaCidade(LocalDate data, String cidade);
    Vaga save(Vaga vaga);
    void deleteById(Long id);
}