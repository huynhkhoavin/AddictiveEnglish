package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Questions {

    @SerializedName("data")
    @Expose
    private List<Question> data = null;

    public List<Question> getData() {
        return data;
    }

    public void setData(List<Question> data) {
        this.data = data;
    }

}