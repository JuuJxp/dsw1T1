package br.ufscar.dc.dsw.controller.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.ufscar.dc.dsw.domain.Empresa;
import br.ufscar.dc.dsw.domain.Vaga;
import br.ufscar.dc.dsw.service.spec.IEmpresaService;
import br.ufscar.dc.dsw.service.spec.IVagaService;

@CrossOrigin
@RestController
public class VagaRestController {

    @Autowired
    private IVagaService vagaService;

    @Autowired
    private IEmpresaService empresaService;

    @GetMapping("/api/vagas")
    public ResponseEntity<List<Vaga>> lista() {
        List<Vaga> lista = vagaService.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/api/vagas/{id}")
    public ResponseEntity<Vaga> lista(@PathVariable("id") long id) {
        Vaga vaga = vagaService.buscarPorId(id);
        if (vaga == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(vaga);
    }

    @GetMapping("/api/vagas/empresas/{id}")
    public ResponseEntity<List<Vaga>> listaPorEmpresa(@PathVariable("id") long id) {
        Empresa empresa = empresaService.buscarPorId(id);
        if (empresa == null) {
            return ResponseEntity.notFound().build();
        }
        List<Vaga> lista = vagaService.buscarVagasAbertasPorEmpresa(empresa);
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }
}