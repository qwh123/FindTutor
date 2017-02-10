package com.qwh.findtutor.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * com.qwh.findtutor.utils
 * 开发者 qwh
 * 时间: 14:56
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public class JsonUtils {
    private static Gson mGson = new Gson();

    public static <T> String serialize(T object) {
        return mGson.toJson(object);
    }

    public static <T> T deserialize(String json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    public static <T> T deserialize(JsonObject json, Class<T> clz) throws JsonSyntaxException {
        return mGson.fromJson(json, clz);
    }

    public static <T> T deserialize(String json, Type type) throws JsonSyntaxException {
        return mGson.fromJson(json, type);
    }
}
