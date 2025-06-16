package br.ufscar.dc.dsw.service.spec;

import java.util.List;
import br.ufscar.dc.dsw.domain.Usuario;

public interface IUsuarioService {
    Usuario buscarPorId(Long id);
    Usuario buscarPorEmail(String email);
    List<Usuario> buscarTodos();
    void salvar(Usuario usuario);
    void excluir(Long id);
    // boolean usuarioJaExiste(String email); // Exemplo de outros serviços
}
