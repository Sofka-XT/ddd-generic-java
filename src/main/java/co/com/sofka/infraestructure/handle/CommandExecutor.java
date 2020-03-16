package co.com.sofka.infraestructure.handle;

import co.com.sofka.domain.generic.Command;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;


public abstract class CommandExecutor implements CommandHandler<Command> {
    protected Set<Consumer<? super Command>> handles = new HashSet<>();
    protected void add(Consumer<? extends Command> consumer) {
         handles.add((Consumer<? super Command>) consumer);
    }

    @Override
    public final void execute(Command command){
        for(var consumer :  handles){
            try {
                consumer.accept(command);
                return;
            } catch (ClassCastException ignored) {
            }
        }
        throw new ExecutionNoFound(command);
    }
}
