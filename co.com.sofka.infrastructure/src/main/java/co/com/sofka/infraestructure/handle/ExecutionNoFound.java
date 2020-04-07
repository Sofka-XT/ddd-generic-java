package co.com.sofka.infraestructure.handle;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.domain.generic.Query;

/**
 * The type Execution no found.
 */
public class ExecutionNoFound extends RuntimeException {
    /**
     * Instantiates a new Execution no found.
     *
     * @param command the command
     */
    public ExecutionNoFound(Command command) {
        super(String.format("The command [%s] to be executed does not have a handler", command.getClass().getCanonicalName()));
    }

    /**
     * Instantiates a new Execution no found.
     *
     * @param query the query
     */
    public ExecutionNoFound(Query query) {
        super(String.format("The query [%s] to be executed does not have a handler", query.getClass().getCanonicalName()));
    }
}
