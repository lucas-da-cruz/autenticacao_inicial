package br.com.date.usecase;

import br.com.date.dataprovider.entities.Perfil;
import br.com.date.dataprovider.entities.Usuario;
import br.com.date.dataprovider.repository.UsuarioRepository;
import br.com.date.entrypoint.controller.form.EstabelecimentoForm;
import br.com.date.entrypoint.controller.form.EstabelecimentoFormUpdate;
import br.com.date.usecase.domain.enums.PerfilUsuarioEnum;
import br.com.date.usecase.domain.exception.EmailExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Configuration
public class EstabelecimentoUseCase {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PerfilUseCase perfilUseCase;
    @Autowired
    TokenUseCase tokenUseCase;

    public void salvaEstabelecimento(EstabelecimentoForm form) {
        if (emailExistente(form.getEmail())) {
            throw new EmailExistenteException("A conta: " + form.getEmail() +  " já existe!");
        }
        Usuario usuario = getUsuarioEntity(form);
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findById(Long id){
        return usuarioRepository.findById(id);
    }

    public void atualizaEstabelecimento(EstabelecimentoFormUpdate estabelecimentoForm, Optional<Usuario> usuarioAtual){
        usuarioAtual.get().setNome(estabelecimentoForm.getNome());
        usuarioRepository.save(usuarioAtual.get());
    }

    public void deleteById(Long id){
        usuarioRepository.deleteById(id);
    }

    private Usuario getUsuarioEntity(EstabelecimentoForm form){
        List<Perfil> perfilUsuario = new ArrayList<>();
        perfilUsuario.add(perfilUseCase.getPerfil(PerfilUsuarioEnum.ESTABELECIMENTO.getPerfil()));

        return Usuario.builder()
                .nome(form.getNome())
                .email(form.getEmail())
                .senha(new BCryptPasswordEncoder().encode(form.getSenha()))
                .perfis(perfilUsuario.isEmpty() ? null : perfilUsuario)
                .build();
    }

    private boolean emailExistente(String email){
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.isPresent();
    }
}
