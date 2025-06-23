package br.ufscar.dc.dsw.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Empresa;
import br.ufscar.dc.dsw.domain.Vaga;
import br.ufscar.dc.dsw.service.spec.IEmpresaService; 
import br.ufscar.dc.dsw.service.spec.IVagaService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/vagas")
public class VagaController {

    @Autowired
    private IVagaService vagaService;

    @Autowired
    private IEmpresaService empresaService;

    @GetMapping("/listar")
    public String listar(@RequestParam(value = "cidade", required = false) String cidade, ModelMap model) {
        List<Vaga> vagas;
        if (cidade != null && !cidade.isEmpty()) {
            vagas = vagaService.buscarVagasEmAbertoPorCidade(cidade);
        } else {
            vagas = vagaService.buscarTodasVagasEmAberto();
        }
        model.addAttribute("vagas", vagas);
        return "vaga/lista"; 
    }

    @GetMapping("/cadastrar")
    public String cadastrar(Vaga vaga) {
        return "vaga/cadastro";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid Vaga vaga, BindingResult result, RedirectAttributes attr, Principal principal) {
        if (result.hasErrors()) {
            return "vaga/cadastro";
        }
        
        Empresa empresa = empresaService.buscarPorEmail(principal.getName());
        if (empresa == null) {
            attr.addFlashAttribute("fail", "Erro: Empresa não encontrada para o usuário logado.");
            return "redirect:/vagas/listar";
        }
        
        vaga.setEmpresa(empresa); // Associa a vaga à empresa
        vaga.setAtiva(true); // Vagas recém-cadastradas são ativas por padrão
        vaga.setDataLimiteInscricao(vaga.getDataLimiteInscricao()); // Garante que a data é a do formulário

        vagaService.salvar(vaga);
        attr.addFlashAttribute("sucess", "Vaga cadastrada com sucesso!");
        return "redirect:/vagas/minhasVagas";
    }
 
    @GetMapping("/minhasVagas")
    public String minhasVagas(ModelMap model, Principal principal) {
        Empresa empresa = empresaService.buscarPorEmail(principal.getName());
        model.addAttribute("vagas", vagaService.buscarPorEmpresa(empresa));
        return "vaga/minhasVagas"; 
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        Vaga vaga = vagaService.buscarPorId(id);
        model.addAttribute("vaga", vaga);
        return "vaga/cadastro";
    }

    @PostMapping("/editar")
    public String editar(@Valid Vaga vaga, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "vaga/cadastro";
        }
        vagaService.salvar(vaga);
        attr.addFlashAttribute("sucess", "Vaga editada com sucesso!");
        return "redirect:/vagas/minhasVagas";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        vagaService.excluir(id);
        attr.addFlashAttribute("sucess", "Vaga excluída com sucesso!");
        return "redirect:/vagas/minhasVagas";
    }
}