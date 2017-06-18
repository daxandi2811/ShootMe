package at.shootme.state.manager;

import at.shootme.screens.ConnectingScreen;
import at.shootme.screens.GameScreen;
import at.shootme.SM;
import at.shootme.state.data.GameState;
import at.shootme.state.data.GameStateType;
import at.shootme.screens.MainMenu;

/**
 * Created by Nicole on 17.06.2017.
 */
public class GameStateManager {

    /**
     * requests to switch to level Selection menu
     */
    public void requestSwitchToLevelSelection() {
        if (SM.isClient()) {
            GameState requestedState = new GameState();
            requestedState.setStateType(GameStateType.LEVEL_SELECTION);
            sendStateRequest(requestedState);
        } else {
            switchToLevelSelection();
        }
    }

    /**
     * switch to level Selection menu
     */
    private void switchToLevelSelection() {
        SM.state = new GameState();
        SM.state.setStateType(GameStateType.LEVEL_SELECTION);
        if (SM.isServer()) {
            SM.server.getKryonetServer().sendToAllTCP(SM.state);
        }
        SM.game.setScreen(new MainMenu());
    }

    /**
     * request to start the game
     * @param levelKey
     */
    public void requestStartGame(String levelKey) {
        if (SM.isClient()) {
            GameState requestedState = new GameState();
            requestedState.setLevelKey(levelKey);
            requestedState.setStateType(GameStateType.IN_GAME);
            sendStateRequest(requestedState);
        }
        if (SM.isServer()) {
            startGame(levelKey);
        }
    }

    /**
     * state request
     * @param requestedState
     */
    private void sendStateRequest(GameState requestedState) {
        SM.client.getConnection().sendTCPWithFlush(requestedState);
    }

    /**
     * starts a round of a the game
     * @param levelKey
     */
    private void startGame(String levelKey) {
        if (SM.state != null && SM.state.getStateType() == GameStateType.IN_GAME) {
            return;
        }
        SM.state = new GameState();
        SM.state.setStateType(GameStateType.IN_GAME);
        SM.state.setLevelKey(levelKey);
        if (SM.isServer()) {
            SM.server.getKryonetServer().sendToAllTCP(SM.state);
        }
        SM.game.setScreen(new GameScreen(levelKey));
    }

    public void apply(GameState gameState) {
        switch (gameState.getStateType()) {
            case LEVEL_SELECTION:
                switchToLevelSelection();
                break;
            case IN_GAME:
                startGame(gameState.getLevelKey());
                break;
            case SERVER_SELECTION:
                SM.state = gameState;
                SM.game.setScreen(new ConnectingScreen());
                break;
        }
    }
}
