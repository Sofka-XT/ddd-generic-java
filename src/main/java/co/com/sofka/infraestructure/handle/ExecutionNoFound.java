package co.com.sofka.infraestructure.handle;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.domain.generic.Query;

public class ExecutionNoFound extends RuntimeException {
 public ExecutionNoFound(Command command){
     super(String.format("The command [%s] to be executed does not have a handler", command.getClass().getCanonicalName()));
 }
    public ExecutionNoFound(Query query){
        super(String.format("The query [%s] to be executed does not have a handler", query.getClass().getCanonicalName()));
    }
}
