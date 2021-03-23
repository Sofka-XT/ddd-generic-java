package co.com.sofka.infraestructure.handle;


import co.com.sofka.domain.generic.Command;
import co.com.sofka.infraestructure.controller.CommandWrapperSerializer;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.lang.reflect.Type;

/**
 * The type Command wrapper.
 */
public class CommandWrapper {
    private final String aggregateId;
    private final String commandType;
    private final Object payLoad;

    /**
     * Instantiates a new Command wrapper.
     *
     * @param aggregateId the aggregate id
     * @param commandType the command type
     * @param payLoad     the pay load
     */
    public CommandWrapper(String aggregateId, String commandType, Object payLoad) {
        this.aggregateId = aggregateId;
        this.commandType = commandType;
        this.payLoad = payLoad;
    }

    /**
     * Gets pay load.
     *
     * @return the pay load
     */
    public Object getPayLoad() {
        return CommandWrapperSerializer.instance().getGson().toJson(payLoad);
    }

    /**
     * Gets aggregate id.
     *
     * @return the aggregate id
     */
    public String getAggregateId() {
        return aggregateId;
    }

    /**
     * Gets command type.
     *
     * @return the command type
     */
    public String getCommandType() {
        return commandType;
    }

    /**
     * Value of t.
     *
     * @param <T> the type parameter
     * @param zzz the zzz
     * @return the t
     */
    public <T extends Command> T valueOf(Type zzz) {
        JsonReader reader = new JsonReader(new StringReader(getPayLoad().toString()));
        reader.setLenient(true);
        return CommandWrapperSerializer.instance()
                .getGson()
                .fromJson(reader, zzz);
    }
}
