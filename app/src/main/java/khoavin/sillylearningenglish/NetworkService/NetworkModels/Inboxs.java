package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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