package co.com.sofka.business.generic;


/**
 * Business exception
 * <p>
 * Business exceptions thrown by the use case
 */
public class ReplyBusinessException extends UnexpectedException {


    /**
     * Instantiates a new Business exception.
     *
     * @param identify  the identify
     * @param message   the message
     * @param throwable the throwable and cause of the problem
     */
    public ReplyBusinessException(String identify, String message, Throwable throwable) {
        super(identify, message, throwable);
    }


    /**
     * Instantiates a new Business exception.
     *
     * @param identify the identify
     * @param message  the message
     */
    public ReplyBusinessException(String identify, String message) {
        this(identify, message, null);
    }

}
