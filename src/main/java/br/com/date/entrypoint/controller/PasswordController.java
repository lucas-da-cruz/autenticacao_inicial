package br.com.date.entrypoint.controller;

import br.com.date.usecase.UsuarioUseCase;
import br.com.date.usecase.domain.exception.UpdatePasswordException;
import br.com.date.entrypoint.controller.form.EmailForm;
import br.com.date.entrypoint.controller.form.NewPasswordForm;
import br.com.date.entrypoint.controller.validation.ErroDeFormularioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PasswordController {

    @Autowired
    UsuarioUseCase usuarioUseCase;

    @PostMapping("/pass")
    public ResponseEntity newPassword(@RequestBody @Valid EmailForm email){
        try{
            if(!usuarioUseCase.isEmailExist(email.getEmail())){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().build();
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(new ErroDeFormularioDto("Email não enviado", e.getMessage()));
        }
    }

    @PostMapping("/atualizaSenha")
    public ResponseEntity updatePassword(@RequestBody @Valid NewPasswordForm newPasswordForm){
        try{
            usuarioUseCase.updatePassword(newPasswordForm);
            return ResponseEntity.ok().build();
        }
        catch (UpdatePasswordException e){
            return ResponseEntity.badRequest().body(new ErroDeFormularioDto("URL inválida: ", e.getMessage()));
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body(new ErroDeFormularioDto("Email não enviado", e.getMessage()));
        }
    }

}
