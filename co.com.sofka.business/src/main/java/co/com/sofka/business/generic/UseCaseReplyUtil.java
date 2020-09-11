package co.com.sofka.business.generic;


import java.util.Arrays;
import java.util.function.Supplier;
import java.util.logging.Logger;

/**
 * The type Use case reply util.
 */
public final class UseCaseReplyUtil {
    private static final Logger logger = Logger.getLogger(UseCaseReplyUtil.class.getName());

    private UseCaseReplyUtil() {
    }

    /**
     * Retry t.
     *
     * @param <T>                     the type parameter
     * @param function                the function
     * @param maxRetries              the max retries
     * @param timeoutExceptionClasses the timeout exception classes
     * @return the t
     */
    public static <T> T retry(Supplier<T> function, int maxRetries, Class<? extends Exception>... timeoutExceptionClasses) {
        timeoutExceptionClasses = timeoutExceptionClasses.length == 0 ? new Class[]{Exception.class} : timeoutExceptionClasses;
        int retryCounter = 0;
        Exception lastException = null;
        while (retryCounter < maxRetries) {
            try {
                return function.get();
            } catch (RuntimeException e) {
                UtilReply utilReply = new UtilReply(maxRetries, retryCounter, e, timeoutExceptionClasses).invoke();
                if (utilReply.is()) break;
                retryCounter = utilReply.getRetryCounter();
                lastException = utilReply.getLastException();
            }
        }
        throw lastException != null ?
                ((RuntimeException) lastException) :
                new RuntimeException();
    }


    private static class UtilReply {
        private final int maxRetries;
        private final Class<? extends Exception>[] timeoutExceptionClasses;
        private final Exception exception;
        private boolean myResult;
        private int retryCounter;
        private Exception lastException;


        /**
         * Instantiates a new Util reply.
         *
         * @param maxRetries              the max retries
         * @param retryCounter            the retry counter
         * @param exception               the exception
         * @param timeoutExceptionClasses the timeout exception classes
         */
        @SafeVarargs
        public UtilReply(int maxRetries, int retryCounter, Exception exception, Class<? extends Exception>... timeoutExceptionClasses) {
            this.maxRetries = maxRetries;
            this.retryCounter = retryCounter;
            this.exception = exception;
            this.timeoutExceptionClasses = timeoutExceptionClasses;
        }

        /**
         * Is boolean.
         *
         * @return the boolean
         */
        boolean is() {
            return myResult;
        }

        /**
         * Gets retry counter.
         *
         * @return the retry counter
         */
        public int getRetryCounter() {
            return retryCounter;
        }

        /**
         * Gets last exception.
         *
         * @return the last exception
         */
        public Exception getLastException() {
            return lastException;
        }

        /**
         * Invoke util reply.
         *
         * @return the util reply
         */
        public UtilReply invoke() {
            lastException = exception;
            if (Arrays.stream(timeoutExceptionClasses).noneMatch(tClass ->
                    tClass.isAssignableFrom(exception.getClass())
            )) throw (RuntimeException) lastException;
            else {
                retryCounter++;
                logger.info("-- Reply(" + retryCounter + "/" + maxRetries + ") => " + exception.getMessage());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                if (retryCounter >= maxRetries) {
                    myResult = true;
                    return this;
                }
            }
            myResult = false;
            return this;
        }
    }
}