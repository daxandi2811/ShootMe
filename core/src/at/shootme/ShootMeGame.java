package at.shootme;

import com.badlogic.gdx.Game;
import mainmenu.MainMenu;

public class ShootMeGame extends Game {

    public ShootMeGame() {
        SM.game = this;
    }

    @Override
    public void create() {
        //Sets the MainMenu as the current Screen
        setScreen(new MainMenu());
    }

}
