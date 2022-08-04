package br.com.date.usecase.domain.exception;

public class EmailExistenteException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailExistenteException(String msg){
        super(msg);
    }
}

