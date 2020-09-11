package co.com.sofka.business.generic;

/**
 * The type Unexpected exception.
 */
public class UnexpectedException extends RuntimeException {
    private final String identify;
    private String request;

    /**
     * Instantiates a new Unexpected exception.
     *
     * @param identify the identify
     * @param message  the message
     * @param cause    the cause
     */
    public UnexpectedException(String identify, String message, Throwable cause) {
        super(message, cause);
        this.identify = identify;
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

    /**
     * Gets identify.
     *
     * @return the identify
     */
    public String getIdentify() {
        return identify;
    }
}
