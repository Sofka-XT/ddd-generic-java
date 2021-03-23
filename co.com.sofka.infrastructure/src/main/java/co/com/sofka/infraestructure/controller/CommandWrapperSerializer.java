package co.com.sofka.infraestructure.controller;

import co.com.sofka.infraestructure.AbstractSerializer;
import co.com.sofka.infraestructure.handle.CommandWrapper;

/**
 * The type Command wrapper serializer.
 */
public final class CommandWrapperSerializer extends AbstractSerializer {

    private static CommandWrapperSerializer eventSerializer;

    private CommandWrapperSerializer() {
        super();
    }


    /**
     * Instance command wrapper serializer.
     *
     * @return the command wrapper serializer
     */
    public static synchronized CommandWrapperSerializer instance() {
        if (eventSerializer == null) {
            eventSerializer = new CommandWrapperSerializer();
        }
        return eventSerializer;
    }


    /**
     * Deserialize command wrapper.
     *
     * @param aSerialization the a serialization
     * @return the command wrapper
     */
    public CommandWrapper deserialize(String aSerialization) {
        return gson.fromJson(aSerialization, CommandWrapper.class);
    }


    /**
     * Serialize string.
     *
     * @param object the object
     * @return the string
     */
    public String serialize(CommandWrapper object) {
        return gson.toJson(object);
    }

}