// src/main/java/br/ufscar/dc/dsw/controller/rest/ProfissionalRestController.java

package br.ufscar.dc.dsw.controller.rest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.domain.SexoProfissional;
import br.ufscar.dc.dsw.service.spec.IProfissionalService;

@CrossOrigin
@RestController
public class ProfissionalRestController {

    @Autowired
    private IProfissionalService profissionalService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private boolean isJSONValid(String jsonInString) {
        try {
            return new ObjectMapper().readTree(jsonInString) != null;
        } catch (IOException e) {
            return false;
        }
    }

    private void parse(Profissional profissional, JSONObject json) {
        profissional.setNome((String) json.get("nome"));
        profissional.setEmail((String) json.get("email"));
        profissional.setCpf((String) json.get("cpf"));
        profissional.setTelefone((String) json.get("telefone"));
        profissional.setCidade((String) json.get("cidade"));
        profissional.setEstado((String) json.get("estado"));
        profissional.setPais((String) json.get("pais"));

        if (json.get("senha") != null) {
            profissional.setSenha(passwordEncoder.encode((String) json.get("senha")));
        }
        if (json.get("sexo") != null) {
            profissional.setSexo(SexoProfissional.valueOf((String) json.get("sexo")));
        }
        if (json.get("dataNascimento") != null) {
            profissional.setDataNascimento(LocalDate.parse((String) json.get("dataNascimento")));
        }
    }

    @GetMapping("/api/profissionais")
    public ResponseEntity<List<Profissional>> lista() {
        List<Profissional> lista = profissionalService.buscarTodos();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/api/profissionais/{id}")
    public ResponseEntity<Profissional> lista(@PathVariable("id") long id) {
        Profissional profissional = profissionalService.buscarPorId(id);
        if (profissional == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profissional);
    }

    @PostMapping("/api/profissionais")
    @ResponseBody
    public ResponseEntity<Profissional> cria(@RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Profissional profissional = new Profissional();
                parse(profissional, json);
                profissionalService.salvar(profissional);
                return ResponseEntity.ok(profissional);
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @PutMapping("/api/profissionais/{id}")
    public ResponseEntity<Profissional> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
        try {
            if (isJSONValid(json.toString())) {
                Profissional profissional = profissionalService.buscarPorId(id);
                if (profissional == null) {
                    return ResponseEntity.notFound().build();
                } else {
                    parse(profissional, json);
                    profissionalService.salvar(profissional);
                    return ResponseEntity.ok(profissional);
                }
            } else {
                return ResponseEntity.badRequest().body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @DeleteMapping("/api/profissionais/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {
        Profissional profissional = profissionalService.buscarPorId(id);
        if (profissional == null) {
            return ResponseEntity.notFound().build();
        } else {
            profissionalService.excluir(id);
            return ResponseEntity.noContent().build();
        }
    }
}