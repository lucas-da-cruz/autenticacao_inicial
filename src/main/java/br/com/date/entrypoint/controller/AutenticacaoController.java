package br.com.date.entrypoint.controller;

import br.com.date.entrypoint.controller.dto.UsuarioDto;
import br.com.date.dataprovider.entities.Perfil;
import br.com.date.dataprovider.entities.Usuario;
import br.com.date.usecase.TokenUseCase;
import br.com.date.entrypoint.controller.dto.TokenDto;
import br.com.date.entrypoint.controller.form.LoginForm;
import br.com.date.usecase.UsuarioUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private TokenUseCase tokenUseCase;
    @Autowired
    private UsuarioUseCase usuarioUseCase;

    @PostMapping("/auth")
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginForm user){

        UsernamePasswordAuthenticationToken dadosLogin = user.converter();

        try{
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenUseCase.gerarToken(authentication);

            UsuarioDto usuario = usuarioUseCase.getIdByToken(token);

            /*List<String> authorities = authentication.getAuthorities().stream()
                    .map(a -> ((GrantedAuthority) a).getAuthority()).collect(Collectors.toList());*/

            return ResponseEntity.ok(new TokenDto(token, "Bearer", usuario));
        } catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/isAuthenticated")
    public ResponseEntity isAuthenticated(@RequestHeader String authorization){
        Optional<Usuario> usuario = usuarioUseCase.findByToken(authorization.substring(7));
        List<Perfil> perfil = usuario.get().getPerfis();
        return ResponseEntity.ok(perfil);
    }

    @PostMapping("/sendEmailToPassword")
    public ResponseEntity sendEmailToPassword(){
        /*Optional<Usuario> usuario = usuarioUseCase.findByToken(authorization.substring(7));
        List<Perfil> perfil = usuario.get().getPerfis();
        return ResponseEntity.ok(perfil);*/
        return null;
    }

    @GetMapping()
    public ResponseEntity up(){
        return ResponseEntity.ok().build();
    }
}
