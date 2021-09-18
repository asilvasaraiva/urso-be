package br.com.urso.user.exception;

public class EmailAlreadyExistException extends RuntimeException{


    private static final long serialVersionUID = -6832380186810977226L;

    public EmailAlreadyExistException() {
        super();
    }

    public EmailAlreadyExistException(Exception e) {
        super(e);
    }

    public EmailAlreadyExistException(String message) {
        super(message);
    }

    public EmailAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
