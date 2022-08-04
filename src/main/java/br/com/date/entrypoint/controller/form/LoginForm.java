package br.com.date.entrypoint.controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginForm {

    @Email(message = "{email.not.valid}")
    @NotBlank(message = "{email.not.blank}")
    private String email;
    @NotBlank(message = "{senha.not.blank}")
    private String senha;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(email, senha);
    }
}
