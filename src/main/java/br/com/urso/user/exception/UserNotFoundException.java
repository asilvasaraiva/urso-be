package br.com.urso.user.exception;

public class UserNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 308677525390455739L;

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(Exception e) {
        super(e);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
