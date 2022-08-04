package br.com.date.dataprovider.repository;

import br.com.date.dataprovider.entities.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Perfil findByNome(String nome);

}