package khoavin.sillylearningenglish.SYSTEM.EventBusObject;

/**
 * Created by Khoavin on 4/12/2017.
 */

public class ProgressObject {
    int currentPosition;
    int Duration;

    public ProgressObject(int currentPosition, int duration) {
        this.currentPosition = currentPosition;
        Duration = duration;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }
}
