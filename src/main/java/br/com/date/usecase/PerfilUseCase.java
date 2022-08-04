package br.com.date.usecase;

import br.com.date.dataprovider.entities.Perfil;
import br.com.date.dataprovider.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilUseCase {

    @Autowired
    PerfilRepository perfilRepository;

    public Perfil getPerfil(String nomePerfil){
        return perfilRepository.findByNome(nomePerfil);
    }

    public List<Perfil> findAll(){
        return perfilRepository.findAll().stream().filter(
                p -> !p.getNome().equalsIgnoreCase("cidadao")
        ).collect(Collectors.toList());
    }

}
