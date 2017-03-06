package khoavin.sillylearningenglish.SYSTEM.ToolFactory;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Khoavin on 3/5/2017.
 */

public class ArrayConvert<T> {
    public static <T> T[] toArray(ArrayList<T> list) {
        T[] array = (T[]) new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
