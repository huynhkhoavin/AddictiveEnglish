package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KhoaVin on 10/07/2017.
 */

public class ProgressChart {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("unit_count")
    @Expose
    private String unitCount;

    public int getDay() {
        return Integer.parseInt(day);
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getUnitCount() {
        return Integer.parseInt(unitCount);
    }

    public void setUnitCount(String unitCount) {
        this.unitCount = unitCount;
    }

}