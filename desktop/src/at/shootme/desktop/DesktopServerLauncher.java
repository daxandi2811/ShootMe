package at.shootme.desktop;


import at.shootme.SM;
import at.shootme.ShootMeGame;
import at.shootme.entity.general.EntityTypeHandlerRegistry;
import at.shootme.networking.general.KryoRegistrar;
import at.shootme.networking.server.GameServer;
import at.shootme.state.data.GameState;
import at.shootme.state.data.GameStateType;
import at.shootme.state.manager.GameStateManager;
import at.shootme.util.entity.EntityIdGenerator;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopServerLauncher {

    public static void main(String[] arg) {
        SM.gameStateManager = new GameStateManager();
        SM.entityIdGenerator = new EntityIdGenerator();
        SM.kryoRegistrar = new KryoRegistrar();
        SM.entityTypeHandlerRegistry = new EntityTypeHandlerRegistry();

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "ShootMe Server";
        config.width = 700;
        config.height = 200;
        config.addIcon("assets/guenter_icon32px.png", Files.FileType.Internal);
        config.addIcon("assets/guenter_icon16px.png", Files.FileType.Internal);
        config.resizable = false;
        new LwjglApplication(new ShootMeGame(), config);

        GameServer gameServer = new GameServer();
        SM.server = gameServer;
        gameServer.open();
    }
}
