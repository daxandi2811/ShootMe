package at.shootme;

import com.badlogic.gdx.Game;

public class ShootMeGame extends Game {

	@Override
	public void create() {
		SM.game = this; //do not change this variable!
		setScreen(new GameScreen());
	}

}
