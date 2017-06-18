package at.shootme.pickupgeneration;

import at.shootme.SM;
import at.shootme.ShootMeConstants;
import at.shootme.entity.level.Platform;
import at.shootme.entity.pickups.CoinPickup;
import at.shootme.logic.StepListener;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Nicole on 18.06.2017.
 */
public class PickupGenerator implements StepListener {

    public static final float COIN_GENERATION_INTERVAL = 5f;
    public static final int MAX_ITERATIONS = 50;
    private float lastTimeGeneratedCoinGameSeconds = 0;
    private Random random = new Random();

    @Override
    public void beforeWorldStep(float timeStep) {
        float gameDurationSeconds = SM.gameScreen.getGameDurationSeconds();
        if (gameDurationSeconds > lastTimeGeneratedCoinGameSeconds + COIN_GENERATION_INTERVAL) {
            lastTimeGeneratedCoinGameSeconds = gameDurationSeconds;
            generateCoin();
        }
    }

    private void generateCoin() {
        Vector2 position = null;
        int iterations = 0;
        do {
            iterations++;
            Vector2 potentialPosition = generateRandomPosition();
            AtomicBoolean collision = new AtomicBoolean(false);
            SM.world.QueryAABB(fixture -> {
                collision.set(true);
                return false;
            }, potentialPosition.x - CoinPickup.SIZE.x / 2, potentialPosition.y - CoinPickup.SIZE.y / 2, potentialPosition.x + CoinPickup.SIZE.x / 2, potentialPosition.y + CoinPickup.SIZE.y / 2);
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

        if (position != null) {
            CoinPickup coinPickup = new CoinPickup(position);
            SM.level.add(coinPickup);
        }
    }

    private Vector2 generateRandomPosition() {
        Vector2 bottomLeftCornerPosition = SM.level.getBottomLeftCornerPixelPosition().scl(ShootMeConstants.PIXELS_TO_METERS);
        Vector2 size = SM.level.getPixelSize().scl(ShootMeConstants.PIXELS_TO_METERS);
        return new Vector2(random.nextFloat(), random.nextFloat())
                .scl(size)
                .add(bottomLeftCornerPosition);
    }
}
