package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Candidatura;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.domain.Vaga;

public interface ICandidaturaService {
    Candidatura buscarPorId(Long id);
    List<Candidatura> buscarPorProfissional(Profissional profissional); 
    Candidatura buscarPorProfissionalEVaga(Profissional profissional, Vaga vaga);
    List<Candidatura> buscarPorVaga(Vaga vaga); 
    void salvar(Candidatura candidatura); 
    void excluir(Long id);
    boolean jaCandidatou(Profissional profissional, Vaga vaga);
}