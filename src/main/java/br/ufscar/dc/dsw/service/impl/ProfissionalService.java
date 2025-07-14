package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
 
import br.ufscar.dc.dsw.dao.ICandidaturaDAO;
import br.ufscar.dc.dsw.dao.IProfissionalDAO;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.spec.IProfissionalService;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

@Service
@Transactional(readOnly = false)
public class ProfissionalService implements IProfissionalService {

    @Autowired
    IProfissionalDAO profissionalDAO;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    ICandidaturaDAO candidaturaDAO;

    @Override
    public void salvar(Profissional profissional) {
        usuarioService.salvar(profissional);
    }

    public void validarCamposUnicos(Profissional profissional, Errors errors) {
        usuarioService.validarEmailUnico(profissional, errors);
        Profissional profissionalPorCpf = profissionalDAO.findByCpf(profissional.getCpf());
        if (profissionalPorCpf != null && !profissionalPorCpf.getId().equals(profissional.getId())) {
            errors.rejectValue("cpf", "Unique.profissional.cpf", "CPF já cadastrado.");
        }
    }

    @Override
    public void excluir(Long id) {
        if (profissionalPossuiCandidaturas(id)) {
            throw new RuntimeException("Profissional não pode ser excluído. Possui candidaturas associadas.");
        }
        profissionalDAO.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Profissional buscarPorId(Long id) {
        return profissionalDAO.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Profissional buscarPorCpf(String cpf) {
        return profissionalDAO.findByCpf(cpf);
    }

    @Override
    @Transactional(readOnly = true)
    public Profissional buscarPorEmail(String email) {
        Usuario usuario = usuarioService.buscarPorEmail(email);

        if (usuario instanceof Profissional) {
            return (Profissional) usuario;
        }
        return null;
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