package at.shootme;

import at.shootme.levels.Level;
import com.badlogic.gdx.physics.box2d.World;

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
}
