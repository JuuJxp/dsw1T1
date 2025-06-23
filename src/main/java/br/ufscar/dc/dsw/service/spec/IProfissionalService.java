package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Profissional;

public interface IProfissionalService {
    Profissional buscarPorId(Long id);
    Profissional buscarPorCpf(String cpf);
    Profissional buscarPorEmail(String email);
    List<Profissional> buscarTodos();
    void salvar(Profissional profissional);
    void excluir(Long id);
}