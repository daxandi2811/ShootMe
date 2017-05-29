package at.shootme;

import com.badlogic.gdx.Game;

public class ShootMeGame extends Game {

    public ShootMeGame() {
        SM.game = this;
    }

    @Override
    public void create() {
        setScreen(new GameScreen());
    }

}
