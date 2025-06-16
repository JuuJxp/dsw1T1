package br.ufscar.dc.dsw.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import br.ufscar.dc.dsw.domain.Usuario;

@SuppressWarnings("unchecked")

public interface IUsuarioDAO extends CrudRepository<Usuario, Long> {
    Usuario findById(long id);

    Usuario findByEmail(String email);

    List<Usuario> findAll();

    Usuario save(Usuario usuario);
    
    void deleteById(Long id);
}
