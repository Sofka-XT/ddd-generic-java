package co.com.sofka.infraestructure;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;

/**
 * The type Abstract serializer.
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
                .registerTypeAdapter(Instant.class, new AbstractSerializer.DateSerializer())
                .registerTypeAdapter(Instant.class, new AbstractSerializer.DateDeserializer())
                .serializeNulls()
                .create();
    }

    private static class DateSerializer implements JsonSerializer<Instant> {
        @Override
        public JsonElement serialize(Instant source, Type typeOfSource, JsonSerializationContext context) {
            return new JsonPrimitive(Long.toString(source.toEpochMilli()));
        }
    }

    private static class DateDeserializer implements JsonDeserializer<Instant> {
        @Override
        public Instant deserialize(JsonElement json, Type typeOfTarget, JsonDeserializationContext context) {
            long time = Long.parseLong(json.getAsJsonPrimitive().getAsString());
            return Instant.ofEpochMilli(time);
        }
    }
}
