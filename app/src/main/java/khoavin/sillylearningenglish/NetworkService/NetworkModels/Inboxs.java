package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Inboxs {

    @SerializedName("data")
    @Expose
    private List<Inbox> data = null;

    public List<Inbox> getData() {
        return data;
    }

    public void setData(List<Inbox> data) {
        this.data = data;
    }

}