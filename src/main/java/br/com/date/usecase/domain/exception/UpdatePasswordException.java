package br.com.date.usecase.domain.exception;

public class UpdatePasswordException extends RuntimeException  {
    private static final long serialVersionUID = 1L;

    public UpdatePasswordException(String msg){
        super(msg);
    }
}