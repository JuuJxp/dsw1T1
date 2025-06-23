package br.ufscar.dc.dsw.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Empresa;
import br.ufscar.dc.dsw.domain.Vaga;

@SuppressWarnings("unchecked")
public interface IVagaDAO extends CrudRepository<Vaga, Long> {
    Vaga findById(long id);
    List<Vaga> findAll();
    List<Vaga> findByEmpresa(Empresa empresa);
    List<Vaga> findByDataLimiteInscricaoAfter(Date data);
    List<Vaga> findByDataLimiteInscricaoAfterAndEmpresaCidade(Date data, String cidade);
    List<Vaga> findByAtivaTrueAndDataLimiteInscricaoBefore(Date data);
    long countByEmpresaAndAtivaTrue(Empresa empresa);
    Vaga save(Vaga vaga);
    void deleteById(Long id);
}