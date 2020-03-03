package co.com.sofka.infraestructure;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Date;

public abstract class AbstractSerializer {
    protected Gson gson;

    protected AbstractSerializer(){
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new AbstractSerializer.DateSerializer())
                .registerTypeAdapter(Date.class, new AbstractSerializer.DateDeserializer())
                .serializeNulls().create();
    }

    private static class DateSerializer implements JsonSerializer<Date> {
        public JsonElement serialize(Date source, Type typeOfSource, JsonSerializationContext context) {
            return new JsonPrimitive(Long.toString(source.getTime()));
        }
    }

    private static class DateDeserializer implements JsonDeserializer<Date> {
        public Date deserialize(JsonElement json, Type typeOfTarget, JsonDeserializationContext context)  {
            long time = Long.parseLong(json.getAsJsonPrimitive().getAsString());
            return new Date(time);
        }
    }
}
