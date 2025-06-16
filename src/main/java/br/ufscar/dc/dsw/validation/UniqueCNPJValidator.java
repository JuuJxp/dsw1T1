// package br.ufscar.dc.dsw.validation;

// import jakarta.validation.ConstraintValidator;
// import jakarta.validation.ConstraintValidatorContext;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;

// import br.ufscar.dc.dsw.dao; //colocar o dao correto
// import br.ufscar.dc.dsw.domain; //colocar o domain correto para validar o CNPJ da tabela que o utilizará

// @Component
// public class UniqueCNPJValidator implements ConstraintValidator<UniqueCNPJ, String> {

// 	@Autowired
// 	private nome_do_dao_Correto dao;

// 	@Override
// 	public boolean isValid(String CNPJ, ConstraintValidatorContext context) {
// 		if (dao != null) {
// 			Tabela_correta Tabela_correta = dao.findByCNPJ(CNPJ);
// 			return Tabela_correta == null;
// 		} else {
//             // Não necessidade de validação
// 			// Durante a execução da classe LivrariaMvcApplication
// 			// não há injeção de dependência. 
// 			return true;
// 		}
// 	}
// }