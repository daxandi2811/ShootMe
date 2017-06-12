package at.shootme;

import com.badlogic.gdx.Game;
import mainmenu.MainMenu;

public class ShootMeGame extends Game {



    public ShootMeGame() {
        SM.game = this;
    }

    @Override
    public void create() {

        setScreen(new MainMenu());

     //   setScreen(new GameScreen());
    }

}
