package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Empresa;
import br.ufscar.dc.dsw.domain.Vaga;

public interface IVagaService {
    Vaga buscarPorId(Long id);
    List<Vaga> buscarTodos();
    List<Vaga> buscarPorEmpresa(Empresa empresa);
    List<Vaga> buscarTodasVagasEmAberto();
    List<Vaga> buscarVagasEmAbertoPorCidade(String cidade);
    void salvar(Vaga vaga);
    void excluir(Long id);
}