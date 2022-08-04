package br.com.date.config.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings( CorsRegistry registry ) {
        //Quando for para produção
        //registry.addMapping("/**").allowedOrigins("http://localhost:3000", "https://front-end-3cs.herokuapp.com/").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}

