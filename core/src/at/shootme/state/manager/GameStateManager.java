package at.shootme.state.manager;

import at.shootme.screens.ConnectingScreen;
import at.shootme.screens.GameModeSelectionMenuScreen;
import at.shootme.screens.GameScreen;
import at.shootme.SM;
import at.shootme.state.data.GameMode;
import at.shootme.state.data.GameState;
import at.shootme.state.data.GameStateType;
import at.shootme.screens.LevelSelectionMenuScreen;

/**
 * Created by Nicole on 17.06.2017.
 */
public class GameStateManager {

    /**
     * switch to level Selection menu
     */
    public void switchToGameModeSelection() {
        SM.state = new GameState();
        SM.state.setStateType(GameStateType.GAME_MODE_SELECTION);
        if (SM.isServer()) {
            SM.server.getKryonetServer().sendToAllTCP(SM.state);
        }
        SM.game.setScreen(new GameModeSelectionMenuScreen());
    }

    /**
     * switch to level Selection menu
     * @param gameMode
     */
    private void switchToLevelSelection(GameMode gameMode) {
        SM.state = new GameState();
        SM.state.setStateType(GameStateType.LEVEL_SELECTION);
        SM.state.setGameMode(gameMode);
        if (SM.isServer()) {
            SM.server.getKryonetServer().sendToAllTCP(SM.state);
        }
        SM.game.setScreen(new LevelSelectionMenuScreen());
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
            startGame(SM.state.getGameMode(), levelKey);
        }
    }

    public void requestGameMode(GameMode gameMode) {
        if (SM.isClient()) {
            GameState requestedState = new GameState();
            requestedState.setGameMode(gameMode);
            requestedState.setStateType(GameStateType.LEVEL_SELECTION);
            sendStateRequest(requestedState);
        }
        if (SM.isServer()) {
            selectGameMode(gameMode);
        }
    }

    /**
     * state request
     * @param requestedState
     */
    private void sendStateRequest(GameState requestedState) {
        SM.client.getConnection().sendTCPWithFlush(requestedState);
    }

    private void selectGameMode(GameMode gameMode) {
        if (SM.state != null && SM.state.getStateType() == GameStateType.LEVEL_SELECTION) {
            // ignore simultaneous requests
            return;
        }
        switchToLevelSelection(gameMode);
    }

    /**
     * starts a round of a the game
     * @param levelKey
     */
    private void startGame(GameMode gameMode, String levelKey) {
        if (SM.state != null && SM.state.getStateType() == GameStateType.IN_GAME) {
            // ignore simultaneous requests
            return;
        }
        SM.state = new GameState();
        SM.state.setStateType(GameStateType.IN_GAME);
        SM.state.setGameMode(gameMode);
        SM.state.setLevelKey(levelKey);
        if (SM.isServer()) {
            SM.server.getKryonetServer().sendToAllTCP(SM.state);
        }
        SM.game.setScreen(new GameScreen(levelKey));
    }

    public void apply(GameState gameState) {
        switch (gameState.getStateType()) {
            case GAME_MODE_SELECTION:
                switchToGameModeSelection();
                break;
            case LEVEL_SELECTION:
                switchToLevelSelection(gameState.getGameMode());
                break;
            case IN_GAME:
                startGame(gameState.getGameMode(), gameState.getLevelKey());
                break;
            case SERVER_SELECTION:
                SM.state = gameState;
                SM.game.setScreen(new ConnectingScreen());
                break;
        }
    }
}
