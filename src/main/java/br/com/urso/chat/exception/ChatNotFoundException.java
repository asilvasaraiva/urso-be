package br.com.urso.chat.exception;

public class ChatNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1407207534678855997L;

    public ChatNotFoundException() {
        super();
    }

    public ChatNotFoundException(Exception e) {
        super(e);
    }

    public ChatNotFoundException(String message) {
        super(message);
    }

    public ChatNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
