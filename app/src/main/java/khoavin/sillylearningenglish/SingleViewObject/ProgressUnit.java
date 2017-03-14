package khoavin.sillylearningenglish.SingleViewObject;

/**
 * Created by KhoaVin on 2/18/2017.
 */

public class ProgressUnit {
    int PlayStatus;
    String Title;
    String Duration;
    boolean IsDone;

    public ProgressUnit(int playStatus, String title, String duration, boolean isDone) {
        PlayStatus = playStatus;
        Title = title;
        Duration = duration;
        IsDone = isDone;
    }

    public boolean isDone() {
        return IsDone;
    }

    public void setDone(boolean done) {
        IsDone = done;
    }

    public int getPlayStatus() {
        return PlayStatus;
    }

    public void setPlayStatus(int playStatus) {
        PlayStatus = playStatus;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }
}
