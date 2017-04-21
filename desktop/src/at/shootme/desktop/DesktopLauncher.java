package at.shootme.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import at.shootme.ShootMeGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "ShootMe";
		config.width = 1280;
		config.height = 720;
		config.addIcon("assets/guenter_icon32px.png", Files.FileType.Internal);
        config.addIcon("assets/guenter_icon16px.png", Files.FileType.Internal);
        config.resizable = false;
		new LwjglApplication(new ShootMeGame(), config);
	}
}
