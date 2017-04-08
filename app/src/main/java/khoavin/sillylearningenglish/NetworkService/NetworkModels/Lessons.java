package khoavin.sillylearningenglish.NetworkService.NetworkModels;

/**
 * Created by Khoavin on 4/2/2017.
 */

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lessons {

    @SerializedName("data")
    @Expose
    private List<Lesson> data = null;

    public List<Lesson> getData() {
        return data;
    }

    public void setData(List<Lesson> data) {
        this.data = data;
    }
}
