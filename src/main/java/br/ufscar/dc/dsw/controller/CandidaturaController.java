package br.ufscar.dc.dsw.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Candidatura;
import br.ufscar.dc.dsw.domain.Empresa;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.domain.StatusCandidatura;
import br.ufscar.dc.dsw.domain.Vaga;
import br.ufscar.dc.dsw.service.spec.ICandidaturaService;
import br.ufscar.dc.dsw.service.spec.IEmpresaService;
import br.ufscar.dc.dsw.service.spec.IProfissionalService;
import br.ufscar.dc.dsw.service.spec.IVagaService;

@Controller
@RequestMapping("/candidaturas")
public class CandidaturaController {

    @Autowired
    private ICandidaturaService candidaturaService;

    @Autowired
    private IProfissionalService profissionalService;

    @Autowired
    private IVagaService vagaService;

    @Autowired
    private IEmpresaService empresaService;

    @PostMapping("/candidatar/{idVaga}")
    public String candidatar(@PathVariable("idVaga") Long idVaga,
                             @RequestParam("file") MultipartFile file,
                             RedirectAttributes attr, Principal principal) {
        
        Profissional profissional = profissionalService.buscarPorEmail(principal.getName());
        Vaga vaga = vagaService.buscarPorId(idVaga);

        if (profissional == null || vaga == null) {
            attr.addFlashAttribute("fail", "Profissional ou Vaga não encontrados.");
            return "redirect:/vagas/listar";
        }

        if (candidaturaService.jaCandidatou(profissional, vaga)) {
            attr.addFlashAttribute("fail", "Você já se candidatou a esta vaga.");
            return "redirect:/vagas/listar";
        }

            Candidatura candidatura = new Candidatura();
            candidatura.setProfissional(profissional);
            candidatura.setVaga(vaga);
            candidatura.setStatusVaga(StatusCandidatura.ABERTO);

            candidaturaService.salvar(candidatura);
            attr.addFlashAttribute("sucess", "Candidatura realizada com sucesso!");
        
        return "redirect:/candidaturas/minhasCandidaturas";
    }

    @GetMapping("/minhasCandidaturas")
    public String minhasCandidaturas(ModelMap model, Principal principal) {
        Profissional profissional = profissionalService.buscarPorEmail(principal.getName());
        model.addAttribute("candidaturas", candidaturaService.buscarPorProfissional(profissional));
        return "candidatura/minhasCandidaturas"; 
    }

    @GetMapping("/gerenciar/{idVaga}")
    public String gerenciarCandidaturas(@PathVariable("idVaga") Long idVaga, ModelMap model, Principal principal) {
        Empresa empresa = empresaService.buscarPorEmail(principal.getName());
        Vaga vaga = vagaService.buscarPorId(idVaga);

        if (vaga == null || !vaga.getEmpresa().getId().equals(empresa.getId())) {
             model.addAttribute("fail", "Acesso não autorizado ou vaga não encontrada.");
             return "redirect:/vagas/minhasVagas";
        }

        model.addAttribute("vaga", vaga);
        model.addAttribute("candidaturas", candidaturaService.buscarPorVaga(vaga));
        model.addAttribute("statusOptions", StatusCandidatura.values());
        return "candidatura/gerenciar"; 
    }

    @PostMapping("/atualizarStatus")
    public String atualizarStatus(@RequestParam("idCandidatura") Long idCandidatura,
                                  @RequestParam("status") StatusCandidatura status,
                                  @RequestParam(value = "linkEntrevista", required = false) String linkEntrevista,
                                  RedirectAttributes attr, Principal principal) {
        Candidatura candidatura = candidaturaService.buscarPorId(idCandidatura);

        Empresa empresa = empresaService.buscarPorEmail(principal.getName());
        if (candidatura == null || !candidatura.getVaga().getEmpresa().getId().equals(empresa.getId())) {
             attr.addFlashAttribute("fail", "Acesso não autorizado ou candidatura não encontrada.");
             return "redirect:/vagas/minhasVagas";
        }

        candidatura.setStatusVaga(status);
        candidaturaService.salvar(candidatura);

        if (status == StatusCandidatura.ENTREVISTA) {
            attr.addFlashAttribute("sucess", "Status atualizado para ENTREVISTA. E-mail com link será enviado.");
        } else {
            attr.addFlashAttribute("sucess", "Status da candidatura atualizado para " + status.name() + ".");
        }
        
        return "redirect:/candidaturas/gerenciar/" + candidatura.getVaga().getId();
    }
}