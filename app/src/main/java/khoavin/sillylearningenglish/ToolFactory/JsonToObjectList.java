package khoavin.sillylearningenglish.ToolFactory;

import java.util.Arrays;
import java.util.List;

import khoavin.sillylearningenglish.EntityDatabase.Silly_english.User;

import static khoavin.sillylearningenglish.ToolFactory.JsonConvert.gson;

/**
 * Created by KhoaVin on 1/22/2017.
 */

public class JsonToObjectList<T> {
    public static <T> List<T> getArray(String jsonString, Class<T[]> tClass){
        T[] ts = gson.fromJson(jsonString,tClass);
        return Arrays.asList(ts);
    }
}
