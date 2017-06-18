package at.shootme.networking.data;

/**
 * Created by Nicole on 18.06.2017.
 */
public class ServerTick {
    private float currentGameDurationSeconds;

    public float getCurrentGameDurationSeconds() {
        return currentGameDurationSeconds;
    }

    public void setCurrentGameDurationSeconds(float currentGameDurationSeconds) {
        this.currentGameDurationSeconds = currentGameDurationSeconds;
    }
}
