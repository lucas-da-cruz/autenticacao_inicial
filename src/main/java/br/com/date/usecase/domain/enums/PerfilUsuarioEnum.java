package br.com.date.usecase.domain.enums;

import lombok.Getter;

@Getter
public enum PerfilUsuarioEnum {

    USUARIO("usuario"),
    ESTABELECIMENTO("estabelecimento");

    private String perfil;

    PerfilUsuarioEnum(String perfil) {
        this.perfil = perfil;
    }

}
