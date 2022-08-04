package br.com.date.entrypoint.controller;

import br.com.date.usecase.PerfilUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    PerfilUseCase perfilUseCase;

}
