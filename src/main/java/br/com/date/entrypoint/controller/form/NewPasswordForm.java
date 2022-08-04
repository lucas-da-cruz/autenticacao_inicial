package br.com.date.entrypoint.controller.form;

import javax.validation.constraints.NotBlank;

public class NewPasswordForm {

    private Long id;
    @NotBlank(message = "{email.not.blank}")
    private String codigo;
    @NotBlank(message = "{senha.not.blank}")
    private String senha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
