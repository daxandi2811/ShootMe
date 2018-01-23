package at.shootme.state.data;

/**
 * Created by Nicole on 17.06.2017.
 */
public class GameState {
    private GameStateType stateType;
    private GameMode gameMode;
    private String levelKey;

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public GameStateType getStateType() {
        return stateType;
    }

    public void setStateType(GameStateType stateType) {
        this.stateType = stateType;
    }

    public String getLevelKey() {
        return levelKey;
    }

    public void setLevelKey(String levelKey) {
        this.levelKey = levelKey;
    }
}
