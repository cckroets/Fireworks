package com.ckroetsch.hanabi.util;

import android.util.Log;

import com.ckroetsch.hanabi.app.GameFragment;
import com.ckroetsch.hanabi.model.Game;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author curtiskroetsch
 */
public final class JsonUtil {

    private static final String TAG = "JsonUtil";

    private JsonUtil() {

    }

    public static String objectToJson(Object object) {
        final ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            Log.e(TAG, e.getMessage());
        }
        return json;
    }

    public static <T> T jsonToObject(String json, Class<T> klass) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(json, klass);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
