package br.com.date.entrypoint.controller.form;

import br.com.date.dataprovider.entities.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EstabelecimentoForm {

    @NotBlank
    private String nome;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String senha;

    public Usuario converterUsuario(){
        return Usuario.builder()
                .nome(nome)
                .email(email)
                .senha(senha)
                .build();
    }

}
