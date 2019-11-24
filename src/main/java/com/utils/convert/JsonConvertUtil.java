package com.utils.convert;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

/**
 * JsonStr - Json(Array) - 对象(Array) 转换
 * 注意方法的 ‘ 重载 ’ - ‘ 调用 ’
 */
public class JsonConvertUtil {

    public static <T> T json2Obj(JSONObject jsonObject){

        return (T) new Object();
    }

    public static <T> JSONObject obj2Json(T t){

        return  new JSONObject();
    }

    public static <T> T jsonStr2Obj(String jsonStr){

        return (T) new Object();
    }

    public static <T> String obj2JsonStr(T t){

        return "";
    }

    public static <T> Collection<T> jsonArray2Objs(JSONArray jsonArray){

        return new ArrayList<T>();
    }

    public static <T> JSONArray objs2JsonArray(Collection<T> objs){

        return new JSONArray();
    }

}
