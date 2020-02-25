package co.com.sofka.infraestructure;

public class QueryFaultException extends RuntimeException {
    public QueryFaultException() {
    }
    public QueryFaultException(String message) {
        super(message);
    }
}
