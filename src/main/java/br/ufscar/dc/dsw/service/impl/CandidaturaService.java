package br.ufscar.dc.dsw.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.ICandidaturaDAO;
import br.ufscar.dc.dsw.domain.Candidatura;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.domain.Vaga;
import br.ufscar.dc.dsw.service.spec.ICandidaturaService;

@Service
@Transactional(readOnly = false)
public class CandidaturaService implements ICandidaturaService {

    @Autowired
    ICandidaturaDAO dao;

    @Override
    public void salvar(Candidatura candidatura) {
        dao.save(candidatura);
    }

    @Override
    public void excluir(Long id) {
        dao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Candidatura buscarPorId(Long id) {
        return dao.findById(id.longValue());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidatura> buscarPorProfissional(Profissional profissional) {
        return dao.findByProfissional(profissional);
    }

    @Override
    @Transactional(readOnly = true)
    public Candidatura buscarPorProfissionalEVaga(Profissional profissional, Vaga vaga) {
        return dao.findByProfissionalAndVaga(profissional, vaga);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidatura> buscarPorVaga(Vaga vaga) {
        return dao.findByVaga(vaga);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean jaCandidatou(Profissional profissional, Vaga vaga) {
        return dao.findByProfissionalAndVaga(profissional, vaga) != null;
    }
}