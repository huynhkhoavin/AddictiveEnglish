package khoavin.sillylearningenglish.SYSTEM.ToolFactory;

import java.util.ArrayList;
import java.util.List;

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
    public static <T> ArrayList<T> toArrayList(ArrayList<Object> list){
        ArrayList<T> arrayList = new ArrayList<>();
        for (Object obj:list){
            T t = (T)obj;
            arrayList.add(t);
        }
        return arrayList;
    }
    public static<T> ArrayList<Object> toObjectArray(ArrayList<T> list){
        ArrayList<Object> arrayList = new ArrayList<>();
        for (T t:list){
            Object obj = (Object)t;
            arrayList.add(t);
        }
        return arrayList;
    }
    public static<T> ArrayList<Object> toObjectArray(List<T> list){
        ArrayList<Object> arrayList = new ArrayList<>();
        for (T t:list){
            Object obj = (Object)t;
            arrayList.add(t);
        }
        return arrayList;
    }
    public static<T> ArrayList<Object> toObjectArray(T[] list){
        ArrayList<Object> arrayList = new ArrayList<>();
        for (T t:list){
            Object obj = (Object)t;
            arrayList.add(t);
        }
        return arrayList;
    }
    public static<T> ArrayList<T> toArrayList(List<T> list){
        ArrayList<T> arrayList = new ArrayList<>();
        for (T t:list){
            Object obj = (Object)t;
            arrayList.add(t);
        }
        return arrayList;
    }
    public static<T> ArrayList<T> toArrayList(T[] list){
        ArrayList<T> arrayList = new ArrayList<>();
        for (T t:list){
            Object obj = (Object)t;
            arrayList.add(t);
        }
        return arrayList;
    }
}
