package br.ufscar.dc.dsw.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IVagaDAO;
import br.ufscar.dc.dsw.domain.Empresa;
import br.ufscar.dc.dsw.domain.Vaga;
import br.ufscar.dc.dsw.service.spec.IVagaService;

@Service
@Transactional(readOnly = false)
public class VagaService implements IVagaService {

    @Autowired
    IVagaDAO dao;

    public void salvar(Vaga vaga) {
        dao.save(vaga);
    }

    public void excluir(Long id) {
        dao.deleteById(id);
    }

    @Transactional
    public void desativarVagasExpiradas() {
        Date hoje = Date.valueOf(LocalDate.now());
        List<Vaga> vagasExpiradasAtivas = dao.findByAtivaTrueAndDataLimiteInscricaoBefore(hoje);

        for (Vaga vaga : vagasExpiradasAtivas) {
            vaga.setAtiva(false);
            dao.save(vaga);
        }
        System.out.println("Desativadas " + vagasExpiradasAtivas.size() + " vagas expiradas.");
    }
    @Transactional(readOnly = true)
    public Vaga buscarPorId(Long id) {
        return dao.findById(id.longValue());
    }

    @Transactional(readOnly = true)
    public List<Vaga> buscarTodos() {
        desativarVagasExpiradas(); // Desativa vagas expiradas antes de buscar todas
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public List<Vaga> buscarPorEmpresa(Empresa empresa) {
        return dao.findByEmpresa(empresa);
    }

    @Transactional(readOnly = true)
    public List<Vaga> buscarTodasVagasEmAberto() {
        desativarVagasExpiradas(); // Desativa vagas expiradas antes de buscar vagas em aberto
        return dao.findByDataLimiteInscricaoAfter(Date.valueOf(LocalDate.now()));
    }

    @Transactional(readOnly = true)
    public List<Vaga> buscarVagasEmAbertoPorCidade(String cidade) {
        desativarVagasExpiradas();
        return dao.findByDataLimiteInscricaoAfterAndCidadeLikeIgnoreCase(Date.valueOf(LocalDate.now()), cidade);
    }
    
    @Transactional(readOnly = true)
    public long contarVagasAtivasPorEmpresa(Empresa empresa) {
        desativarVagasExpiradas();
        return dao.countByEmpresaAndAtivaTrue(empresa);
    }
}