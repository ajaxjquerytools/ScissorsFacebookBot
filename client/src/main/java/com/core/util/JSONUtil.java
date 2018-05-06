package com.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.io.IOException;
import java.util.*;

public final class JSONUtil {

    private static final Logger log = LoggerFactory.getLogger(JSONUtil.class);

    /**
     * {@link com.fasterxml.jackson.databind.ObjectMapper} instance
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

      public static <T> Optional<T> parse(String source, Class<T> clazz) {
        try {
            return Optional.of(MAPPER.readValue(source, clazz));
        } catch (IOException e) {
            log.error("parse(): ", e);
            return Optional.empty();
        }
    }

    public static <T> Optional<T> parse(JsonNode jsonNode, Class<T> clazz) {
        try {
            return Optional.of(MAPPER.treeToValue(jsonNode, clazz));
        } catch (IOException e) {
            log.error("parse(): ", e);
            return Optional.empty();
        }
    }

}
