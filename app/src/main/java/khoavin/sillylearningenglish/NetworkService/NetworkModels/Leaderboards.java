package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Leaderboards {

    @SerializedName("data")
    @Expose
    private List<Leaderboard> data = null;

    public List<Leaderboard> getData() {
        return data;
    }

    public void setData(List<Leaderboard> data) {
        this.data = data;
    }

}