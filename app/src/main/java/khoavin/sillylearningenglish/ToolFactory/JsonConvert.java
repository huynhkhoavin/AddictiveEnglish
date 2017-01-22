package khoavin.sillylearningenglish.ToolFactory;

import com.google.gson.Gson;

/**
 * Created by KhoaVin on 1/19/2017.
 */

public class JsonConvert {
    static Gson gson = new Gson();
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
    public static String HashMapToJson(Object obj){
        return gson.toJson(obj);
    }
}
