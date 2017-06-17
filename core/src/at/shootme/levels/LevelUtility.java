package at.shootme.levels;

import at.shootme.ShootMeConstants;
import at.shootme.entity.level.Background;
import at.shootme.entity.level.Platform;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alexander Dietrich on 05.05.2017.
 */
public class LevelUtility implements ShootMeConstants {


    /**
     * Creates a platform
     *
     * @param pixelPositionBottomLeftCorner
     * @param pixelSize
     * @param world
     * @return
     */
    public static Platform createPlatform(Vector2 pixelPositionBottomLeftCorner, Vector2 pixelSize, String type, World world) {
        Vector2 worldSize = pixelSize.scl(PIXELS_TO_METERS);
        Vector2 worldPosition = pixelPositionBottomLeftCorner.cpy().scl(PIXELS_TO_METERS)
                .add(worldSize.cpy().scl(0.5f));
        return new Platform(worldPosition, worldSize, type, world);
    }

    /**
     * Creates a level background.
     *
     * @param position
     * @param size
     * @param texture
     * @return
     */
    public static Background createLevelBackground(Vector2 position, Vector2 size, Texture texture) {
        return new Background(position, size, texture);
    }

}
