package co.com.sofka.business.generic;

/**
 * The type Unexpected exception.
 *
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public class UnexpectedException extends RuntimeException {
    private String request;

    /**
     * Instantiates a new Unexpected exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UnexpectedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Gets request.
     *
     * @return the request
     */
    public String getRequest() {
        return request;
    }

    /**
     * Sets request.
     *
     * @param request the request
     */
    public void setRequest(String request) {
        this.request = request;
    }
}
