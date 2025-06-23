package br.ufscar.dc.dsw;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.dao.IAdministradorDAO;
import br.ufscar.dc.dsw.dao.IEmpresaDAO;
import br.ufscar.dc.dsw.dao.IProfissionalDAO;
import br.ufscar.dc.dsw.domain.Administrador;
import br.ufscar.dc.dsw.domain.Empresa;
import br.ufscar.dc.dsw.domain.PapelUsuario;
import br.ufscar.dc.dsw.domain.Profissional;
import br.ufscar.dc.dsw.domain.SexoProfissional;

@SpringBootApplication
public class BetwinVagasApplication {

	public static void main(String[] args) {
			SpringApplication.run(BetwinVagasApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IAdministradorDAO administradorDAO, IEmpresaDAO empresaDAO, IProfissionalDAO profissionalDAO, BCryptPasswordEncoder encoder) {
		return (args) -> {
			if (administradorDAO.findByEmail("admin@example.com") == null) {
				Administrador adm = new Administrador();
				adm.setEmail("admin@example.com");
				adm.setSenha(encoder.encode("admin_pass_123"));
				adm.setNome("Administrador Principal");
				adm.setPapel(PapelUsuario.ADMIN);
				administradorDAO.save(adm);
			}

			if (empresaDAO.findByCnpj("00.000.000/0001-01") == null) {
				Empresa emp1 = new Empresa();
				emp1.setEmail("empresa1@example.com");
				emp1.setSenha(encoder.encode("empresa1_pass_xyz"));
				emp1.setCnpj("00.000.000/0001-01");
				emp1.setNome("Tech Solutions Ltda.");
				emp1.setDescricao("Empresa líder em soluções de software e tecnologia.");
				emp1.setCidade("São Paulo");
				emp1.setEstado("SP");
				emp1.setPais("Brasil");
				empresaDAO.save(emp1);
			}

			if (empresaDAO.findByCnpj("11.111.111/0001-11") == null) {
				Empresa emp2 = new Empresa();
				emp2.setEmail("empresa2@example.com");
				emp2.setSenha(encoder.encode("empresa2_pass_abc"));
				emp2.setCnpj("11.111.111/0001-11");
				emp2.setNome("Global Innovations S.A.");
				emp2.setDescricao("Consultoria especializada em estratégias de marketing e inovação.");
				emp2.setCidade("Rio de Janeiro");
				emp2.setEstado("RJ");
				emp2.setPais("Brasil");
				empresaDAO.save(emp2);
			}

			if (profissionalDAO.findByCpf("111.222.333-44") == null) {
				Profissional prof1 = new Profissional();
				prof1.setEmail("profissional1@example.com");
				prof1.setSenha(encoder.encode("prof1_pass_789"));
				prof1.setNome("Ana Paula Silva");
				prof1.setCpf("111.222.333-44");
				prof1.setTelefone("(11) 98765-4321");
				prof1.setSexo(SexoProfissional.F);
				prof1.setDataNascimento(LocalDate.of(1995, 3, 10));
				prof1.setCidade("Belo Horizonte");
				prof1.setEstado("MG");
				prof1.setPais("Brasil");
				profissionalDAO.save(prof1);
			}

			if (profissionalDAO.findByCpf("555.666.777-88") == null) {
				Profissional prof2 = new Profissional();
				prof2.setEmail("profissional2@example.com");
				prof2.setSenha(encoder.encode("prof2_pass_456"));
				prof2.setNome("Carlos Eduardo Santos");
				prof2.setCpf("555.666.777-88");
				prof2.setTelefone("(21) 99887-6655");
				prof2.setSexo(SexoProfissional.M);
				prof2.setDataNascimento(LocalDate.of(1992, 11, 25));
				prof2.setCidade("Curitiba");
				prof2.setEstado("PR");
				prof2.setPais("Brasil");
				profissionalDAO.save(prof2);
			}
		};
	}
}