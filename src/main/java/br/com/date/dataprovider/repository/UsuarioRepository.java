package br.com.date.dataprovider.repository;

import br.com.date.dataprovider.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findById(Long id);

	List<Usuario> findAll();

	Usuario save(Usuario usuario);

	void deleteById(Long id);
	
	Optional<Usuario> findByEmail(String email);

}
