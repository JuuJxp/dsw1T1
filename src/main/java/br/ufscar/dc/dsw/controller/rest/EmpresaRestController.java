package br.ufscar.dc.dsw.controller.rest;

import java.io.IOException;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.ufscar.dc.dsw.domain.Empresa;
import br.ufscar.dc.dsw.service.spec.IEmpresaService;

@CrossOrigin
@RestController
public class EmpresaRestController {

    @Autowired
    private IEmpresaService empresaService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    private void parse(Empresa empresa, JSONObject json) {
        empresa.setNome((String) json.get("nome"));
        empresa.setEmail((String) json.get("email"));
        empresa.setCnpj((String) json.get("cnpj"));
        empresa.setDescricao((String) json.get("descricao"));
        empresa.setCidade((String) json.get("cidade"));
        empresa.setEstado((String) json.get("estado"));
        empresa.setPais((String) json.get("pais"));

        if (json.get("senha") != null) {
            empresa.setSenha(passwordEncoder.encode((String) json.get("senha")));
        }
    }

    @GetMapping("/api/empresas")
    public ResponseEntity<List<Empresa>> lista() {
        List<Empresa> lista = empresaService.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/api/empresas/{id}")
    public ResponseEntity<Empresa> lista(@PathVariable("id") long id) {
        Empresa empresa = empresaService.buscarPorId(id);
        if (empresa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empresa);
    }

    @GetMapping("/api/empresas/cidades/{nome}")
    public ResponseEntity<List<Empresa>> listaPorCidade(@PathVariable("nome") String nome) {
        List<Empresa> lista = empresaService.buscarTodasPorCidade(nome);
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/api/empresas")
    @ResponseBody
    public ResponseEntity<Empresa> cria(@RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Empresa empresa = new Empresa();
                parse(empresa, json);
                empresaService.salvar(empresa);
                return ResponseEntity.ok(empresa);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping("/api/empresas/{id}")
    public ResponseEntity<Empresa> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Empresa empresa = empresaService.buscarPorId(id);
                if (empresa == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    parse(empresa, json);
                    empresaService.salvar(empresa);
                    return ResponseEntity.ok(empresa);
                }
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @DeleteMapping("/api/empresas/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {
        Empresa empresa = empresaService.buscarPorId(id);
        if (empresa == null) {
            return ResponseEntity.notFound().build();
        } else {
            empresaService.excluir(id);
            return ResponseEntity.noContent().build();
        }
    }
}