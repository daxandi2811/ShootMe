package at.shootme.networking;

/**
 * Created by Nicole on 18.06.2017.
 */
public class GameEndedMessage {

    private String winningPlayerEntityId;
    private int winningScore;

    public String getWinningPlayerEntityId() {
        return winningPlayerEntityId;
    }

    public void setWinningPlayerEntityId(String winningPlayerEntityId) {
        this.winningPlayerEntityId = winningPlayerEntityId;
    }

    public int getWinningScore() {
        return winningScore;
    }

    public void setWinningScore(int winningScore) {
        this.winningScore = winningScore;
    }
}
