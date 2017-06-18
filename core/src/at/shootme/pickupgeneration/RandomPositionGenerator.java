package at.shootme.pickupgeneration;

import at.shootme.SM;
import at.shootme.ShootMeConstants;
import at.shootme.entity.level.Platform;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Nicole on 18.06.2017.
 */
public class RandomPositionGenerator {

    public static final int MAX_ITERATIONS = 50;
    private static Random random = new Random();

    public static Vector2 getRandomPositionWithMaxGroundGap(Vector2 entitySize) {
        Vector2 position = null;
        int iterations = 0;
        do {
            iterations++;
            Vector2 potentialPosition = generateRandomPositionInLevel();
            AtomicBoolean collision = new AtomicBoolean(false);
            SM.world.QueryAABB(fixture -> {
                collision.set(true);
                return false;
            }, potentialPosition.x - entitySize.x / 2, potentialPosition.y - entitySize.y / 2, potentialPosition.x + entitySize.x / 2, potentialPosition.y + entitySize.y / 2);
            if (!collision.get()) {
                AtomicBoolean groundSomewhereUnder = new AtomicBoolean(false);
                SM.world.rayCast((fixture, point, normal, fraction) -> {
                    boolean isPlatform = fixture.getBody().getUserData() instanceof Platform;
                    if (isPlatform) {
                        groundSomewhereUnder.set(true);
                    }
                    return isPlatform ? 0 : -1;
                }, potentialPosition, potentialPosition.cpy().add(0, -400 * ShootMeConstants.PIXELS_TO_METERS));

                if (groundSomewhereUnder.get()) {
                    position = potentialPosition;
                }
            }
        } while (position == null && iterations < MAX_ITERATIONS);
        return position;
    }

    public static Vector2 generateRandomPositionInLevel() {
        Vector2 bottomLeftCornerPosition = SM.level.getBottomLeftCornerPixelPosition().scl(ShootMeConstants.PIXELS_TO_METERS);
        Vector2 size = SM.level.getPixelSize().scl(ShootMeConstants.PIXELS_TO_METERS);
        return new Vector2(random.nextFloat(), random.nextFloat())
                .scl(size)
                .add(bottomLeftCornerPosition);
    }
}
