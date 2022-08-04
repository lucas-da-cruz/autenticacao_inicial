package br.com.date.entrypoint.controller;

import br.com.date.dataprovider.entities.Usuario;
import br.com.date.entrypoint.controller.dto.UsuarioDto;
import br.com.date.entrypoint.controller.form.EstabelecimentoForm;
import br.com.date.entrypoint.controller.form.EstabelecimentoFormUpdate;
import br.com.date.entrypoint.controller.validation.ErroDeFormularioDto;
import br.com.date.usecase.EstabelecimentoUseCase;
import br.com.date.usecase.TokenUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/estabelecimento")
public class EstabelecimentoController {

    @Autowired
    EstabelecimentoUseCase estabelecimentoUseCase;
    @Autowired
    TokenUseCase tokenUseCase;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    private void salvaEstabelecimento(@RequestBody @Valid EstabelecimentoForm form,
                                      UriComponentsBuilder uriBuilder){
        estabelecimentoUseCase.salvaEstabelecimento(form);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> buscaPeloId(@RequestHeader String authorization, @PathVariable Long id){

        Optional<Usuario> estabelecimentoUsuario = estabelecimentoUseCase.findById(id);
        if(estabelecimentoUsuario.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new UsuarioDto(estabelecimentoUsuario.get()));
    }

    @GetMapping
    private ResponseEntity<?> buscaPeloToken(@RequestHeader String authorization){
        Long id = tokenUseCase.getIdUsuario(authorization.substring(7));
        Optional<Usuario> estabelecimentoUsuario = estabelecimentoUseCase.findById(id);
        if(estabelecimentoUsuario.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new UsuarioDto(estabelecimentoUsuario.get()));
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> atualizaEstabelecimento(@PathVariable Long id,
                                     @RequestBody @Valid EstabelecimentoFormUpdate estabelecimentoForm,
                                     @RequestHeader String authorization){
        if(!tokenUseCase.sameId(authorization, id)){
            return ResponseEntity
                    .badRequest()
                    .body(new ErroDeFormularioDto("Token inválido", "Token não coincide com o usuário"));
        }

        Optional<Usuario> estabelecimentoUsuario = estabelecimentoUseCase.findById(id);
        if(estabelecimentoUsuario.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        estabelecimentoUseCase.atualizaEstabelecimento(estabelecimentoForm, estabelecimentoUsuario);
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

        Optional<Usuario> usuarioAdmin = estabelecimentoUseCase.findById(id);
        if(usuarioAdmin.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        estabelecimentoUseCase.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
