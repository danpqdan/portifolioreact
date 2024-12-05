package br.com.microservices.microservices.servico.exceptions;

public class SuccessResponseException extends RuntimeException {
    private final int status;
    private final String message;
    private final Object data;

    public SuccessResponseException(int status, String message, Object data) {
        super(message);
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }
}
