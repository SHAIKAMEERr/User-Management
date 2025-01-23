package com.example.user_management.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {

    private static final Gson gson = new Gson();

    public static String toJson(Object object) {
        try {
            log.info("Converting object to JSON");
            return gson.toJson(object);
        } catch (Exception e) {
            log.error("Error converting object to JSON: {}", e.getMessage());
            throw new RuntimeException("Error converting object to JSON", e);
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            log.info("Converting JSON to object");
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            log.error("Invalid JSON format: {}", e.getMessage());
            throw new RuntimeException("Invalid JSON format", e);
        }
    }
}
