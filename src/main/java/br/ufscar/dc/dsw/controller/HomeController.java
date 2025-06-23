package br.ufscar.dc.dsw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}