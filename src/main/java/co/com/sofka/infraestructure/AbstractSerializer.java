package co.com.sofka.infraestructure;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * The type Abstract serializer.
 *
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public abstract class AbstractSerializer {
    /**
     * The Gson.
     */
    protected Gson gson;

    /**
     * Instantiates a new Abstract serializer.
     */
    protected AbstractSerializer() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new AbstractSerializer.DateSerializer())
                .registerTypeAdapter(Date.class, new AbstractSerializer.DateDeserializer())
                .serializeNulls().create();
    }

    private static class DateSerializer implements JsonSerializer<Date> {
        /**
         * Serialize json element.
         *
         * @param source       the source
         * @param typeOfSource the type of source
         * @param context      the context
         * @return the json element
         */
        public JsonElement serialize(Date source, Type typeOfSource, JsonSerializationContext context) {
            return new JsonPrimitive(Long.toString(source.getTime()));
        }
    }

    private static class DateDeserializer implements JsonDeserializer<Date> {
        /**
         * Deserialize date.
         *
         * @param json         the json
         * @param typeOfTarget the type of target
         * @param context      the context
         * @return the date
         */
        public Date deserialize(JsonElement json, Type typeOfTarget, JsonDeserializationContext context) {
            long time = Long.parseLong(json.getAsJsonPrimitive().getAsString());
            return new Date(time);
        }
    }
}
