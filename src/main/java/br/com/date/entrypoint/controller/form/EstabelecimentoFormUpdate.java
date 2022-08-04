package br.com.date.entrypoint.controller.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EstabelecimentoFormUpdate {

    @NotBlank
    private String nome;

}
