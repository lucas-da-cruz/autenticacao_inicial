package br.com.date.entrypoint.controller.form;

import br.com.date.dataprovider.entities.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class UsuarioForm {

    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String senha;
    @NotBlank
    private String genero;
    @NotNull
    private LocalDate dataNascimento;

}
