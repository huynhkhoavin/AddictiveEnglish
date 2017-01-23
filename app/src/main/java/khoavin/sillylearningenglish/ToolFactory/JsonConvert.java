package khoavin.sillylearningenglish.ToolFactory;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

/**
 * Created by KhoaVin on 1/19/2017.
 */

public class JsonConvert {
    public static Gson gson = new Gson();
    public static String convertToJson(Object obj)
    {

        String json = gson.toJson(obj);
        return json;
    }
    public static Object convertFromJson(String jsonString)
    {
        Object obj = gson.fromJson(jsonString, Object.class);
        return obj;
    }
    public static <T> List<T> getArray(String jsonString, Class<T[]> tClass){
        T[] ts = gson.fromJson(jsonString,tClass);
        return Arrays.asList(ts);
    }
    public static String HashMapToJson(Object obj){
        return gson.toJson(obj);
    }
}
