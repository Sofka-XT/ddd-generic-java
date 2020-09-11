package co.com.sofka.business.generic;


/**
 * The type Reply business exception.
 */
public class ReplyBusinessException extends UnexpectedException {


    /**
     * Instantiates a new Reply business exception.
     *
     * @param identify  the identify
     * @param message   the message
     * @param throwable the throwable
     */
    public ReplyBusinessException(String identify, String message, Throwable throwable) {
        super(identify, message, throwable);
    }


    /**
     * Instantiates a new Reply business exception.
     *
     * @param identify the identify
     * @param message  the message
     */
    public ReplyBusinessException(String identify, String message) {
        this(identify, message, null);
    }

}
