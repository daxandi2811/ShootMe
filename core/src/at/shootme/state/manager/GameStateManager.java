package at.shootme.state.manager;

import at.shootme.GameScreen;
import at.shootme.SM;
import at.shootme.state.data.GameState;
import at.shootme.state.data.GameStateType;
import mainmenu.MainMenu;

/**
 * Created by Nicole on 17.06.2017.
 */
public class GameStateManager {

    public void requestSwitchToLevelSelection() {
        if (SM.isClient()) {
            GameState requestedState = new GameState();
            requestedState.setStateType(GameStateType.LEVEL_SELECTION);
            sendStateRequest(requestedState);
        } else {
            switchToLevelSelection();
        }
    }

    private void switchToLevelSelection() {
        SM.state = new GameState();
        SM.state.setStateType(GameStateType.LEVEL_SELECTION);
        if (SM.isServer()) {
            SM.server.getKryonetServer().sendToAllTCP(SM.state);
        }
        SM.game.setScreen(new MainMenu());
    }

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

    private void sendStateRequest(GameState requestedState) {
        SM.client.getConnection().sendTCPWithFlush(requestedState);
    }

    private void startGame(String levelKey) {
        if (SM.state != null && SM.state.getStateType() == GameStateType.IN_GAME) {
            return;
        }
        SM.state = new GameState();
        SM.state.setStateType(GameStateType.IN_GAME);
        SM.state.setLevelKey(levelKey);
        // TODO server diffferent screen
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
                break;
        }
    }
}
