package br.ufscar.dc.dsw.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService service;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Usuario usuario) {
		return "usuario/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("usuarios", service.buscarTodos());
		return "usuario/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "usuario/cadastro";
		}
		
		service.salvar(usuario);
		attr.addFlashAttribute("sucess", "Usuario inserido com sucesso.");
		return "redirect:/usuarios/listar";

		// Fazer logica de redirecionamento para continuar o cadastro de usuario dependendo do papel (olha o cadastro.html de usuario que vai entender)
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("usuario", service.buscarPorId(id));
		return "usuario/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attr) {
		// diferencia de salvar pelo ID
		if (result.hasErrors()) {
			return "usuario/cadastro";
		}

		service.salvar(usuario);
		attr.addFlashAttribute("sucess", "Usuário editado com sucesso.");
		return "redirect:/usuarios/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		// Implementar a lógica de verificação se o usuário possui vínculos de vaga ou candidatura antes de excluir, fazer na camada de serviço (IUsuarioService e UsuarioService).
		// if (service.usuarioPossuiVinculos(id)) {
		// 	model.addAttribute("fail", "Usuário não excluído. Possui vaga/candidatura aberta.");
		// } else {
			service.excluir(id);
			model.addAttribute("sucess", "Usuário excluído com sucesso.");
		// }
		return listar(model);
	}
}