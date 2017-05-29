package khoavin.sillylearningenglish.NetworkService.NetworkModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by KhoaVin on 13/05/2017.
 */

public class LessonTracker {

    @SerializedName("current_time")
    @Expose
    private String currentTime;
    @SerializedName("progress")
    @Expose
    private String progress;
    @SerializedName("max_hear")
    @Expose
    private String maxHear;

    public int getMaxHear() {
        return Integer.parseInt(maxHear);
    }

    public void setMaxHear(int maxHear) {
        this.maxHear = String.valueOf(maxHear);
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public int getProgress() {
        return Integer.parseInt(progress);
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

}