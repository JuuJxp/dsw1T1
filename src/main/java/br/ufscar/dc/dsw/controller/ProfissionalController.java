package br.ufscar.dc.dsw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.domain.SexoProfissional;
import br.ufscar.dc.dsw.service.spec.IProfissionalService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private IProfissionalService profissionalService;
     
    @Autowired 
    @Lazy
    private BCryptPasswordEncoder encoder;

    @GetMapping("/cadastrar")
    public String cadastrar(Profissional profissional, ModelMap model) {
        model.addAttribute("sexos", SexoProfissional.values());
        return "profissional/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("profissionais", profissionalService.buscarTodos());
        return "profissional/lista";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Profissional profissional, BindingResult result, RedirectAttributes attr, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("sexos", SexoProfissional.values());
            return "profissional/cadastro";
        }
        if (profissional.getId() == null) {
            profissional.setSenha(encoder.encode(profissional.getSenha()));
        }
        profissionalService.salvar(profissional);
        attr.addFlashAttribute("sucess", "Profissional inserido com sucesso.");
        return "redirect:/profissionais/listar";
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("profissional", profissionalService.buscarPorId(id));
        model.addAttribute("sexos", SexoProfissional.values());
        return "profissional/cadastro";
    }

    @PostMapping("/editar")
    public String editar(@Valid Profissional profissional, BindingResult result, RedirectAttributes attr, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("sexos", SexoProfissional.values());
            return "profissional/cadastro";
        }
        Profissional profissionalExistente = profissionalService.buscarPorId(profissional.getId());
        if (profissional.getSenha() == null || profissional.getSenha().isEmpty()) { 
            profissional.setSenha(profissionalExistente.getSenha());
        } else {
            profissional.setSenha(encoder.encode(profissional.getSenha()));
        }
        profissionalService.salvar(profissional);
        attr.addFlashAttribute("sucess", "Profissional editado com sucesso.");
        return "redirect:/profissionais/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        profissionalService.excluir(id);
        attr.addFlashAttribute("sucess", "Profissional excluído com sucesso.");
        return "redirect:/profissionais/listar";
    }
}