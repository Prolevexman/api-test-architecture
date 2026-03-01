package prolevexman.api.support;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.util.Objects;

public final class Json {
    private final ObjectMapper mapper;

    public static Json createDefault() {
        ObjectMapper m = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();
        return new Json(m);
    }

    public Json(ObjectMapper mapper) {
        this.mapper = Objects.requireNonNull(mapper, "mapper");
    }

    public ObjectMapper mapper() {
        return mapper;
    }

    public String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new JsonException("Failed to serialize to JSON. Type=" + typeOf(value), e);
        }
    }

    public <T> T fromJson(String json, Class<T> type) {
        requireJson(json);
        try {
            return mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new JsonException("Failed to parse JSON. TargetType=" + type.getName() + ", json=" + preview(json), e);
        }
    }

    private static void requireJson(String json) {
        if (json == null || json.isBlank()) {
            throw new JsonException("JSON is blank/empty");
        }
    }

    private static String typeOf(Object v) {
        return v == null ? "null" : v.getClass().getName();
    }

    private static String preview(String json) {
        String s = json == null ? "null" : json.trim();
        int max = 800;
        if (s.length() <= max) return s;
        return s.substring(0, max) + " ...(" + s.length() + " chars)";
    }

    public static final class JsonException extends RuntimeException {
        public JsonException(String message) { super(message); }
        public JsonException(String message, Throwable cause) { super(message, cause); }
    }
}
