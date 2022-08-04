package br.com.date.entrypoint.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class UsuarioFormUpdate {

    @NotBlank
    private String nome;
    @NotBlank
    private String genero;
    @NotNull
    private LocalDate dataNascimento;

}
