package br.com.date.entrypoint.controller.dto;

public class TokenDto {

    private String token;
    private String tipo;
    private UsuarioDto usuarioDto;
    //private List<String> authorities;

    /*public TokenDto(String token, String tipo, List<String> authorities) {
        this.token = token;
        this.tipo = tipo;
        this.authorities = authorities;
    }*/

    public TokenDto(String token, String tipo, UsuarioDto usuarioDto) {
        this.token = token;
        this.tipo = tipo;
        this.usuarioDto = usuarioDto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public UsuarioDto getUsuarioDto() {
        return usuarioDto;
    }

    public void setUsuarioDto(UsuarioDto usuarioDto) {
        this.usuarioDto = usuarioDto;
    }
}
