package com.sphtech.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StringUtils {

    /**
     * check object is json object
     *
     * @param str : is input object
     * @return false if str is not Json and else
     */
    public static boolean isJSONValid(Object str) {
        try {
            new JSONObject(new Gson().toJson(str));
        } catch (JSONException ex) {
            try {
                new JSONArray(new Gson().toJson(str));
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }


    public static JsonElement convertStringToJsonElement(Object str) {
        JsonParser jsonParser = new JsonParser();
        if (isJSONValid(str)) {
            return jsonParser.parse(new Gson().toJson(str));
        } else {
            return jsonParser.parse(str.toString());
        }
    }



}
