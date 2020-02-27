package co.com.sofka.infraestructure.repository;

public class QueryFaultException extends RuntimeException {
    public QueryFaultException() {
    }
    public QueryFaultException(String message) {
        super(message);
    }
}
