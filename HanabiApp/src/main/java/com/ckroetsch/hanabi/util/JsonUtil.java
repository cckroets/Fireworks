package com.ckroetsch.hanabi.util;

import android.util.Log;

import com.ckroetsch.hanabi.app.GameFragment;
import com.ckroetsch.hanabi.model.Game;
import com.ckroetsch.hanabi.model.GameResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONWriter;

import java.io.IOException;

/**
 * @author curtiskroetsch
 */
public final class JsonUtil {

    private static final String TAG = "JsonUtil";

    private JsonUtil() {

    }

    public static <T> T getChild(JSONObject json, String key, Class<T> klass) {
        try {
            final JSONObject child = json.getJSONObject(key);
            return  new Gson().fromJson(child.toString(), klass);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    public static String getChild(JSONObject json, String key) {
        try {
            return json.getString(key);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }


    public static JSONObject objectToJson(Object object) {
        final ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(object);
            return new JSONObject(json);
        } catch (JsonProcessingException e) {
            Log.e(TAG, e.getMessage());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    public static String objectToJsonString(Object object) {
        return new Gson().toJson(object);
    }

    public static <T> T jsonToObject(String json, Class<T> klass) {
        return new Gson().fromJson(json, klass);
    }
}
