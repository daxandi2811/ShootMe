package at.shootme;

import at.shootme.entity.general.EntityTypeHandlerRegistry;
import at.shootme.levels.Level;
import at.shootme.networking.client.GameClient;
import at.shootme.networking.data.PlayerSkin;
import at.shootme.networking.general.KryoRegistrar;
import at.shootme.networking.server.GameServer;
import at.shootme.state.data.GameState;
import at.shootme.state.manager.GameStateManager;
import at.shootme.texture.TextureStore;
import at.shootme.util.entity.EntityIdGenerator;
import com.badlogic.gdx.physics.box2d.World;
import screens.GameScreen;

/**
 * Created by Alexander Dietrich on 07.04.2017.
 * <p>
 * Globally accessible current bean instances.
 */
public class SM {
    public static ShootMeGame game;
    public static GameScreen gameScreen;
    public static World world;
    public static Level level;
    public static EntityIdGenerator entityIdGenerator;
    public static KryoRegistrar kryoRegistrar;
    public static EntityTypeHandlerRegistry entityTypeHandlerRegistry;
    public static GameClient client;
    public static GameServer server;
    public static GameStateManager gameStateManager;
    public static GameState state;
    public static PlayerSkin nextPlayerSkin = PlayerSkin.PLAYER_1;
    public static TextureStore textureStore = new TextureStore();
    public static String playerName = "";

    public static boolean isServer() {
        return server != null;
    }

    public static boolean isClient() {
        return client != null;
    }
}
