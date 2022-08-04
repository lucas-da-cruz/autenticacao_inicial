package br.com.date.entrypoint.controller.dto;

import br.com.date.dataprovider.entities.Perfil;
import br.com.date.dataprovider.entities.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDto {

    private Long id;
    private String nome;
    private String email;
    private String genero;
    private LocalDate dataNascimento;
    private List<Perfil> perfis;

    public UsuarioDto(Usuario usuario) {
        id = usuario.getId();
        nome = usuario.getNome();
        email = usuario.getEmail();
        genero = usuario.getGenero();
        dataNascimento = usuario.getDataNascimento();
        perfis = usuario.getPerfis();
    }

    public static List<UsuarioDto> converterList(List<Usuario> usuario) {
        List<UsuarioDto> usuarioDto = new ArrayList<>();
        usuario.forEach(a -> {
            usuarioDto.add(new UsuarioDto(a));
        });
        return usuarioDto;
    }
}
