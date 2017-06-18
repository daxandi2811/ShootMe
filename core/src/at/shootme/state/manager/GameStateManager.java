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
        GameState requestedState = new GameState();
        requestedState.setStateType(GameStateType.LEVEL_SELECTION);
        sendStateRequest(requestedState);
    }

    private void switchToLevelSelection() {
        if (SM.isClient()) {
            SM.game.setScreen(new MainMenu());
        }
        if (SM.isServer()) {
            SM.server.getKryonetServer().sendToAllTCP(SM.state);
        }
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
        // TODO server diffferent screen
        if (SM.isServer()) {
            SM.server.getKryonetServer().sendToAllTCP(SM.state);
        }
        SM.game.setScreen(new GameScreen(levelKey));
    }

    public void apply(GameState gameState) {
        SM.state = gameState;
        switch (gameState.getStateType()) {
            case LEVEL_SELECTION:
                switchToLevelSelection();
                break;
            case IN_GAME:
                startGame(gameState.getLevelKey());
                break;
            case SERVER_SELECTION:
                break;
        }
    }
}
