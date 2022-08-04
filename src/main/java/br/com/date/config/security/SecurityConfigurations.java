package br.com.date.config.security;

import br.com.date.dataprovider.repository.UsuarioRepository;
import br.com.date.usecase.AutenticacaoUseCase;
import br.com.date.usecase.TokenUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

	@Autowired
	private AutenticacaoUseCase autenticacaoUseCase;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private TokenUseCase tokenUseCase;

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	//Configuracoes de autenticacao
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(autenticacaoUseCase).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	//Configuracoes de autorizacao
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/").permitAll()
				.antMatchers(HttpMethod.POST, "/auth").permitAll()
				.antMatchers(HttpMethod.POST, "/pass").permitAll()
				.antMatchers(HttpMethod.POST, "/pass/{id}/{email}/{senha}").permitAll()
				.antMatchers(HttpMethod.POST, "/usuario").permitAll()
				.antMatchers(HttpMethod.POST, "/usuario/cidadao").permitAll()
				.antMatchers(HttpMethod.POST, "/atualizaSenha").permitAll()
				.antMatchers(HttpMethod.POST, "/estabelecimento").permitAll()
				.anyRequest().authenticated()
				.and().cors()
				.and().csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenUseCase, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
	}
	
	//Configuracoes de recursos estaticos(js, css, imagens, etc.)
	@Override
	public void configure(WebSecurity web) throws Exception {
	}
	
}
