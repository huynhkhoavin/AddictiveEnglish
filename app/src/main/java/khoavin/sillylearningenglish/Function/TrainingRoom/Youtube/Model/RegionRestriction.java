package khoavin.sillylearningenglish.Function.TrainingRoom.Youtube.Model;

/**
 * Created by KhoaVin on 09/07/2017.
 */
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegionRestriction {

    @SerializedName("blocked")
    @Expose
    private List<String> blocked = null;

    public List<String> getBlocked() {
        return blocked;
    }

    public void setBlocked(List<String> blocked) {
        this.blocked = blocked;
    }

}