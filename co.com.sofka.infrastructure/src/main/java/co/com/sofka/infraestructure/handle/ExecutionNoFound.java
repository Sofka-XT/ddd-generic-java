package co.com.sofka.infraestructure.handle;



/**
 * The type Execution no found.
 */
public class ExecutionNoFound extends RuntimeException {
    /**
     * Instantiates a new Execution no found.
     *
     * @param command the command
     */
    public ExecutionNoFound(String type) {
        super(String.format("The type [%s] to be executed does not have a handler", type));
    }
}
