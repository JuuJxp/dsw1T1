package br.ufscar.dc.dsw.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IProfissionalDAO;
import br.ufscar.dc.dsw.dao.ICandidaturaDAO;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.service.spec.IProfissionalService;

@Service
@Transactional(readOnly = false)
public class ProfissionalService implements IProfissionalService {

    @Autowired
    IProfissionalDAO profissionalDAO;

    @Autowired
    ICandidaturaDAO candidaturaDAO;

    @Override
    public void salvar(Profissional profissional) {
        profissionalDAO.save(profissional);
    }

    @Override
    public void excluir(Long id) {
        if (profissionalPossuiCandidaturas(id)) {
            throw new RuntimeException("Profissional não pode ser excluído. Possui candidaturas associadas.");
        }
        profissionalDAO.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Profissional buscarPorId(Long id) {
        return profissionalDAO.findById(id.longValue());
    }

    @Override
    @Transactional(readOnly = true)
    public Profissional buscarPorCpf(String cpf) {
        return profissionalDAO.findByCpf(cpf);
    }

    @Override
    @Transactional(readOnly = true)
    public Profissional buscarPorEmail(String email) {
        return profissionalDAO.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Profissional> buscarTodos() {
        return (List<Profissional>) profissionalDAO.findAll();
    }

    @Transactional(readOnly = true)
    public boolean profissionalPossuiCandidaturas(Long id) {
        Profissional profissional = profissionalDAO.findById(id).orElse(null);
        return profissional != null && !candidaturaDAO.findByProfissional(profissional).isEmpty();
    }
}