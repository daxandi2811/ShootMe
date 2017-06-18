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
    private float lastTimeGeneratedCoinGameSeconds = 0;

    @Override
    public void beforeWorldStep(float timeStep) {
        float gameDurationSeconds = SM.gameScreen.getGameDurationSeconds();
        if (gameDurationSeconds > lastTimeGeneratedCoinGameSeconds + COIN_GENERATION_INTERVAL) {
            lastTimeGeneratedCoinGameSeconds = gameDurationSeconds;
            generateCoin();
        }
    }

    private void generateCoin() {
        Vector2 position = RandomPositionGenerator.getRandomPositionWithMaxGroundGap(CoinPickup.SIZE);
        if (position != null) {
            CoinPickup coinPickup = new CoinPickup(position);
            SM.level.add(coinPickup);
        }
    }
}
