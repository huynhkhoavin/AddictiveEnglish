package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Khoavin on 4/8/2017.
 */

public class LessonUnits {

    @SerializedName("data")
    @Expose
    private List<LessonUnit> data = null;

    public List<LessonUnit> getData() {
        return data;
    }

    public void setData(List<LessonUnit> data) {
        this.data = data;
    }

}