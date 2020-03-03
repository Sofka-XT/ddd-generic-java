package co.com.sofka.infraestructure.repository;

public class QueryFaultException extends Exception {
    public QueryFaultException(String message) {
        super(message);
    }
}
