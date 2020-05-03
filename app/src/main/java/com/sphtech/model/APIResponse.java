package com.sphtech.model;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import com.sphtech.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class APIResponse<T> {

    @SerializedName("success")
    private boolean success = false;

    @SerializedName("result")
    private Object result = null;

    @SerializedName("help")
    private String help = "";


    public T getData(Class<T> myClass) {
        if (result != null) {
            JsonElement jsonElement =  StringUtils.convertStringToJsonElement(result) ;
            return (new Gson()).fromJson(jsonElement, myClass);
        }
        return null;
    }

    public List<T> getDataList(Class<T> myClass) {
        JsonElement jsonElement =  StringUtils.convertStringToJsonElement(result) ;

        JsonArray arrJson = jsonElement.getAsJsonArray();
        List<T> listData = new ArrayList<>();
        for (JsonElement jsonE : arrJson) {
            T objectTemp = (new Gson()).fromJson(jsonE, myClass);
            listData.add(objectTemp);
        }
        return listData;
    }

    public ArrayList<T> getDataArrayList(Class<T> myClass) {
        JsonElement jsonElement =  StringUtils.convertStringToJsonElement(result) ;

        JsonArray arrJson = jsonElement.getAsJsonArray();
        ArrayList<T> listData = new ArrayList<>();
        for (JsonElement jsonE : arrJson) {
            T objectTemp = (new Gson()).fromJson(jsonE, myClass);
            listData.add(objectTemp);
        }
        return listData;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }
}