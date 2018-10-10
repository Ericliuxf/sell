package com.synjones.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/5 14:19
 */
public class JsonUtil {

    public static String toJson(Object object){
        return new GsonBuilder().setPrettyPrinting().create().toJson(object);
    }
}
