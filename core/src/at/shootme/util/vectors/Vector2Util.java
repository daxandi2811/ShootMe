package at.shootme.util.vectors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static at.shootme.ShootMeConstants.PIXELS_TO_METERS;

/**
 * Created by Nicole on 07.05.2017.
 */
public class Vector2Util {

    public static Vector2 convertVector3To2(Vector3 vector3) {
        return new Vector2(vector3.x, vector3.y);
    }

    public static Vector2 radianToVector2(float radian) {
        return new Vector2((float) Math.cos(radian), (float) Math.sin(radian));
    }

    public static Vector2 degreeToVector2(float degree) {
        return radianToVector2((float) Math.toRadians(degree));
    }

    public static Vector2 getCornerPositionOf(Sprite sprite) {
        return new Vector2((sprite.getX() + sprite.getWidth() / 2) * PIXELS_TO_METERS,
                (sprite.getY() + sprite.getHeight() / 2) * PIXELS_TO_METERS);
    }

    public static float getAngleFromAToB(Vector2 a, Vector2 b) {
        return b.cpy().sub(a).angle();
    }
}
