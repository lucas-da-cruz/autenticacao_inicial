package br.com.date.usecase;

import br.com.date.entrypoint.controller.dto.UsuarioDto;
import br.com.date.dataprovider.entities.Perfil;
import br.com.date.dataprovider.entities.Usuario;
import br.com.date.usecase.domain.enums.PerfilUsuarioEnum;
import br.com.date.usecase.domain.exception.EmailExistenteException;
import br.com.date.usecase.domain.exception.UpdatePasswordException;
import br.com.date.entrypoint.controller.form.*;
import br.com.date.dataprovider.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Configuration
public class UsuarioUseCase {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PerfilUseCase perfilUseCase;
    @Autowired
    TokenUseCase tokenUseCase;

    public void salvaUsuario(UsuarioForm form) {
        if (isEmailExist(form.getEmail())) {
            throw new EmailExistenteException("A conta: " + form.getEmail() +  " já existe!");
        }
        Usuario usuario = getUsuarioEntity(form);
        usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findById(Long id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public void atualizaUsuario(UsuarioFormUpdate usuarioAtualizado, Optional<Usuario> usuarioAtual){
        usuarioAtual.get().setNome(usuarioAtualizado.getNome());
        usuarioAtual.get().setGenero(usuarioAtualizado.getGenero());
        usuarioAtual.get().setDataNascimento(usuarioAtualizado.getDataNascimento());
        usuarioRepository.save(usuarioAtual.get());
    }

    public Usuario updatePassword(NewPasswordForm newPasswordForm){
        Usuario usuario = findById(newPasswordForm.getId()).get();

        if(!newPasswordForm.getCodigo().equals(usuario.getSenha().replace("/", ""))){
            throw new UpdatePasswordException("Requisição de ativação de senha inválida");
        }
        //Adicionando hash na senha
        usuario.setSenha(new BCryptPasswordEncoder().encode(newPasswordForm.getSenha()));
        Usuario usuarioUpdated = usuarioRepository.save(usuario);
        return usuarioUpdated;
    }

    public void deleteById(Long id){
        usuarioRepository.deleteById(id);
    }

    public boolean isEmailExist(String email){
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.isPresent();
    }

    public Optional<Usuario> findByToken(String token){
        Long idUsuario = tokenUseCase.getIdUsuario(token);
        return findById(idUsuario);
    }

    public UsuarioDto getIdByToken(String token){
        Usuario usuarioDto = findByToken(token).get();
        return new UsuarioDto(findById(usuarioDto.getId()).get());
    }

    private Usuario getUsuarioEntity(UsuarioForm form){
        List<Perfil> perfilUsuario = new ArrayList<>();
        perfilUsuario.add(perfilUseCase.getPerfil(PerfilUsuarioEnum.USUARIO.getPerfil()));

        return Usuario.builder()
                .nome(form.getNome())
                .email(form.getEmail())
                .senha(new BCryptPasswordEncoder().encode(form.getSenha()))
                .dataNascimento(form.getDataNascimento())
                .genero(form.getGenero())
                .perfis(perfilUsuario.isEmpty() ? null : perfilUsuario)
                .build();
    }

}