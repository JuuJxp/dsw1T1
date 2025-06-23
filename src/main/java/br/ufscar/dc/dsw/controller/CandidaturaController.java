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
import br.ufscar.dc.dsw.service.spec.IEmailService;
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
    private IEmailService emailService;

    @Autowired
    private IEmpresaService empresaService;

    // Exibir formulário de candidatura para uma vaga específica
    @GetMapping("/candidatar/{idVaga}")
    public String exibirFormularioCandidatura(@PathVariable("idVaga") Long idVaga, ModelMap model, Principal principal, RedirectAttributes attr) {
        // Encontra a vaga pelo ID
        Vaga vaga = vagaService.buscarPorId(idVaga);
        
        // Verifica se a vaga existe
        if (vaga == null) {
            attr.addFlashAttribute("fail", "Vaga não encontrada.");
            return "redirect:/erro?msg=Vaga não encontrada.";
        }
        // Verifica se o usuário está logado
        if (principal == null) {
            attr.addFlashAttribute("fail", "Você precisa estar logado para se candidatar.");
            return "redirect:/acessoNegado"; 
        }
        // Tenta buscar o profissional pelo email do usuário autenticado
        // Se o usuário não for um profissional ou nao estiver logado, redireciona com mensagem de erro
        Profissional profissional = profissionalService.buscarPorEmail(principal.getName());
        if (profissional == null) {
            attr.addFlashAttribute("fail", "Você precisa ser um profissional para se candidatar.");
            return "redirect:/acessoNegado"; 
        }

        // Verifica se o profissional já se candidatou a esta vaga
        if (candidaturaService.jaCandidatou(profissional, vaga)) {
            attr.addFlashAttribute("fail", "Você já se candidatou a esta vaga.");
            return "redirect:/erro?msg=Você já se candidatou para essa vaga!"; 
        }

        model.addAttribute("vaga", vaga);
        
        return "candidatura/cadastro";
    }

    @PostMapping("/salvar/{idVaga}")
    public String candidatar(@PathVariable("idVaga") Long idVaga, @RequestParam("file") MultipartFile file, RedirectAttributes attr, Principal principal) {
        
        Profissional profissional = profissionalService.buscarPorEmail(principal.getName());
        Vaga vaga = vagaService.buscarPorId(idVaga);

        if (profissional == null || vaga == null) {
            attr.addFlashAttribute("fail", "Profissional ou Vaga não encontrados.");
            return "redirect:/erro?msg=Não encontramos essa vaga ou profissional";
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
             return "redirect:/acessoNegado";
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

        String emailCandidato = candidatura.getProfissional().getEmail();
        String nomeCandidato = candidatura.getProfissional().getNome();
        String tituloVaga = candidatura.getVaga().getTitulo();
        String nomeEmpresa = empresa.getNome();
        String assunto = "Atualização sobre sua candidatura para a vaga: " + tituloVaga;

        if (status == StatusCandidatura.ENTREVISTA) {
            String corpoEmail = String.format(
                "Olá, %s!\n\nÓtimas notícias! A empresa %s gostaria de te convidar para uma entrevista para a vaga de %s.\n\nA entrevista será realizada através do seguinte link: %s\n\nBoa sorte!\n\nAtenciosamente,\nEquipe Betwin Vagas",
                nomeCandidato, nomeEmpresa, tituloVaga, linkEntrevista
            );
            emailService.sendEmail(emailCandidato, assunto, corpoEmail);
            attr.addFlashAttribute("sucess", "Status atualizado para ENTREVISTA e e-mail enviado ao candidato.");
        
        } else if (status == StatusCandidatura.NAO_SELECIONADO) {
            String corpoEmail = String.format(
                "Olá, %s.\n\nAgradecemos seu interesse na vaga de %s na empresa %s. No momento, optamos por seguir com outros candidatos.\n\nAgradecemos sua participação e desejamos sucesso em sua busca por novas oportunidades.\n\nAtenciosamente,\nEquipe Betwin Vagas",
                nomeCandidato, tituloVaga, nomeEmpresa
            );
            emailService.sendEmail(emailCandidato, assunto, corpoEmail);
            attr.addFlashAttribute("sucess", "Status atualizado para NÃO SELECIONADO e e-mail de notificação enviado.");
        
        } else {
             attr.addFlashAttribute("sucess", "Status da candidatura atualizado para " + status.name() + ".");
        }
        
        return "redirect:/candidaturas/gerenciar/" + candidatura.getVaga().getId();
    }
}