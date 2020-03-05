package co.com.sofka.infraestructure.repository;

/**
 * The type Query fault exception.
 *
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public class QueryFaultException extends Exception {
    /**
     * Instantiates a new Query fault exception.
     *
     * @param message the message
     */
    public QueryFaultException(String message) {
        super(message);
    }
}
