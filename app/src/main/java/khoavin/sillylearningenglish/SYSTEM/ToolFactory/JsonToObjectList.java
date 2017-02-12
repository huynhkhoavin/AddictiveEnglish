package khoavin.sillylearningenglish.SYSTEM.ToolFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by KhoaVin on 1/22/2017.
 */

public class JsonToObjectList<T> {
    public static <T> List<T> getArray(String jsonString, Class<T[]> tClass){
        T[] ts = JsonConvert.gson.fromJson(jsonString,tClass);
        return Arrays.asList(ts);
    }
}
