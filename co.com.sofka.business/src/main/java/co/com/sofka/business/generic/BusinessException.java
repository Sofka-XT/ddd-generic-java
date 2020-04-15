package co.com.sofka.business.generic;

public class BusinessException extends RuntimeException {
    public BusinessException(String message, Throwable throwable){
        super(message, throwable);
    }

    public BusinessException(String message){
        super(message);
    }
}
