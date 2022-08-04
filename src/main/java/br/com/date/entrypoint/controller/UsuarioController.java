package br.com.date.entrypoint.controller;

import br.com.date.entrypoint.controller.form.UsuarioForm;
import br.com.date.usecase.TokenUseCase;
import br.com.date.entrypoint.controller.dto.UsuarioDto;
import br.com.date.dataprovider.entities.Usuario;
import br.com.date.entrypoint.controller.form.UsuarioFormUpdate;
import br.com.date.usecase.UsuarioUseCase;
import br.com.date.entrypoint.controller.validation.ErroDeFormularioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioUseCase usuarioUseCase;
    @Autowired
    TokenUseCase tokenUseCase;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    private void salvaUsuario(@RequestBody @Valid UsuarioForm form, UriComponentsBuilder uriBuilder){
        usuarioUseCase.salvaUsuario(form);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> buscaPeloId(@RequestHeader String authorization, @PathVariable Long id){

        Optional<Usuario> usuario = usuarioUseCase.findById(id);
        if(usuario.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new UsuarioDto(usuario.get()));
    }

    @GetMapping
    private ResponseEntity<?> buscaPeloToken(@RequestHeader String authorization){
        Long id = tokenUseCase.getIdUsuario(authorization.substring(7));
        Optional<Usuario> usuario = usuarioUseCase.findById(id);
        if(usuario.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new UsuarioDto(usuario.get()));
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> atualizaUsuario(@PathVariable Long id,
                                     @RequestBody @Valid UsuarioFormUpdate usuarioForm,
                                     @RequestHeader String authorization){
        if(!tokenUseCase.sameId(authorization, id)){
            return ResponseEntity
                    .badRequest()
                    .body(new ErroDeFormularioDto("Token inválido", "Token não coincide com o usuário"));
        }

        Optional<Usuario> usuario = usuarioUseCase.findById(id);
        if(usuario.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        usuarioUseCase.atualizaUsuario(usuarioForm, usuario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deletaUsuario(@PathVariable Long id,
                                            @RequestHeader String authorization){
        if(!tokenUseCase.sameId(authorization, id)){
            return ResponseEntity
                    .badRequest()
                    .body(new ErroDeFormularioDto("Token inválido", "Token não coincide com o usuário"));
        }

        Optional<Usuario> usuarioAdmin = usuarioUseCase.findById(id);
        if(usuarioAdmin.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        usuarioUseCase.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
