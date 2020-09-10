package co.com.sofka.application;

/**
 * Service Build Exception
 *
 * Runtime exception for build service
 */
public class ServiceBuildException extends RuntimeException {
    public ServiceBuildException(Throwable throwable) {
        super(throwable);
    }
}
