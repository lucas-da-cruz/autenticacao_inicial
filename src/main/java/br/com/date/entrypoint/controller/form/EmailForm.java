package br.com.date.entrypoint.controller.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class EmailForm {

    @Email(message = "{email.not.valid}")
    @NotBlank(message = "{email.not.blank}")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
