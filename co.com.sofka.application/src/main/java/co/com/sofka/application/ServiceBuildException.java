package co.com.sofka.application;

public class ServiceBuildException extends RuntimeException {
    public ServiceBuildException(Throwable throwable){
        super(throwable);
    }
}
