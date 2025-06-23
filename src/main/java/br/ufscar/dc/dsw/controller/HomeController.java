package br.ufscar.dc.dsw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class HomeController {
	@GetMapping("/")
	public String home() {
		return "home";
	}
	@GetMapping("/perfilAdministrador")
	public String perfilAdministrador() {
		return "perfilAdministrador";
	}
	@GetMapping("/perfilEmpresa")
	public String perfilEmpresa() {
		return "perfilEmpresa";
	}
	@GetMapping("/perfilProfissional")
	public String perfilProfissional() {
		return "perfilProfissional";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/vagas")
	public String vagas() {
		return "vagas";
	}
	@GetMapping("/acessoNegado")
    public String acessoNegado(RedirectAttributes attr) {
        return "acessoNegado";
    }
	@GetMapping("/erro")
	public String paginaDeErro() {
			return "erro";
	}
}