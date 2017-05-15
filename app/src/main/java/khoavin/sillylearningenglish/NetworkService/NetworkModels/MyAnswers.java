package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyAnswers {

    @SerializedName("data")
    @Expose
    private List<MyAnswer> data = null;

    public List<MyAnswer> getData() {
        return data;
    }

    public void setData(List<MyAnswer> data) {
        this.data = data;
    }

}