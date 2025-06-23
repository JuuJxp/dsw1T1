package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
 
import br.ufscar.dc.dsw.dao.ICandidaturaDAO;
import br.ufscar.dc.dsw.dao.IProfissionalDAO;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.spec.IProfissionalService;

@Service
@Transactional(readOnly = false)
public class ProfissionalService implements IProfissionalService {

    @Autowired
    IProfissionalDAO profissionalDAO;

    @Autowired
    IUsuarioDAO usuarioDAO; 

    @Autowired
    ICandidaturaDAO candidaturaDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void salvar(Profissional profissional) {
        if (profissional.getId() == null) {
            profissional.setSenha(passwordEncoder.encode(profissional.getSenha()));
        }else {
            Profissional profissionalExistente = profissionalDAO.findById(profissional.getId()).orElse(null);
             if (profissional.getSenha() == null || profissional.getSenha().isEmpty()) {
                if (profissionalExistente != null) {
                    profissional.setSenha(profissionalExistente.getSenha());
                }
            } else {
                profissional.setSenha(passwordEncoder.encode(profissional.getSenha())); 
            }
        }
        profissionalDAO.save(profissional);
    }

    public void validarCamposUnicos(Profissional profissional, Errors errors) {
        Usuario usuarioPorEmail = usuarioDAO.findByEmail(profissional.getEmail());
        
        if (profissional.getId() == null) {
            if (usuarioPorEmail != null) {
                errors.rejectValue("email", "Unique.usuario.email", "E-mail já cadastrado.");
            }
        } else {
            if (usuarioPorEmail != null && !usuarioPorEmail.getId().equals(profissional.getId())) {
                errors.rejectValue("email", "Unique.usuario.email", "E-mail já cadastrado.");
            }
        }

        Profissional profissionalPorCpf = profissionalDAO.findByCpf(profissional.getCpf());

        if (profissional.getId() == null) {
            if (profissionalPorCpf != null) {
                errors.rejectValue("cpf", "Unique.profissional.cpf", "CPF já cadastrado.");
            }
        } else {
            if (profissionalPorCpf != null && !profissionalPorCpf.getId().equals(profissional.getId())) {
                errors.rejectValue("cpf", "Unique.profissional.cpf", "CPF já cadastrado.");
            }
        }
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