package br.ufscar.dc.dsw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Administrador;
import br.ufscar.dc.dsw.service.spec.IAdministradorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/administradores")
public class AdministradorController {

    @Autowired
    private IAdministradorService service;

    @GetMapping("/cadastrar")
    public String cadastrar(Administrador administrador) {
        return "administrador/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("administradores", service.buscarTodos());
        return "administrador/lista";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Administrador administrador, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "administrador/cadastro";
        }
        service.salvar(administrador);
        attr.addFlashAttribute("sucess", "Administrador inserido com sucesso.");
        return "redirect:/administradores/listar";
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("administrador", service.buscarPorId(id));
        return "administrador/cadastro";
    }

    @PostMapping("/editar")
    public String editar(@Valid Administrador administrador, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "administrador/cadastro";
        }
        service.salvar(administrador);
        attr.addFlashAttribute("sucess", "Administrador editado com sucesso.");
        return "redirect:/administradores/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, ModelMap model) {
        service.excluir(id);
        model.addAttribute("sucess", "Administrador excluído com sucesso.");
        return listar(model);
    }
}