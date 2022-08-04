package br.com.date.config.security;

import br.com.date.dataprovider.entities.Usuario;
import br.com.date.dataprovider.repository.UsuarioRepository;
import br.com.date.usecase.TokenUseCase;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenUseCase tokenUseCase;

    private UsuarioRepository repository;

    public AutenticacaoViaTokenFilter(TokenUseCase tokenUseCase, UsuarioRepository repository) {
        this.tokenUseCase = tokenUseCase;
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);

        boolean valido = tokenUseCase.isTokenValido(token);

        if (valido){
            autenticarCliente(token);
        }
        filterChain.doFilter(request, response);
    }

    private void autenticarCliente(String token) {
        //Pegou id do token
        Long idUsuario = tokenUseCase.getIdUsuario(token);
        //Recuperou o objeto de usuario
        Usuario usuario = repository.findById(idUsuario).get();
        //
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

        //Força a autenticação
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")){
            return null;
        } else {
            return token.substring(7, token.length());
        }
    }
}
