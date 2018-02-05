package at.shootme.pickupgeneration;

import at.shootme.SM;
import at.shootme.ShootMeConstants;
import at.shootme.entity.level.Platform;
import at.shootme.entity.pickups.CoinBagPickup;
import at.shootme.entity.pickups.CoinPickup;
import at.shootme.entity.pickups.SpeedUpPickup;
import at.shootme.entity.pickups.TripleJumpPickup;
import at.shootme.entity.player.Player;
import at.shootme.logic.StepListener;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Nicole on 18.06.2017.
 */
public class PickupGenerator implements StepListener {

    public static final float COIN_GENERATION_INTERVAL = 5f;
    public static final float COINBAG_GENERATION_INTERVAL = 21f;
    private float lastTimeGeneratedCoinGameSeconds = 0;
    public static final float STATS_UP_GENERATION_INTERVAL = 7f;
    private float lastTimeGeneratedStatsUpSeconds = 0;
    private float lastTimeGeneratedCoinBagGameSeconds = 0;

    /**
     * generates a new coin every Coin_geneartion_Intervall
     * @param timeStep
     */
    @Override
    public void beforeWorldStep(float timeStep) {
        float gameDurationSeconds = SM.gameScreen.getGameDurationSeconds();
        if (gameDurationSeconds > lastTimeGeneratedCoinGameSeconds + COIN_GENERATION_INTERVAL) {
            lastTimeGeneratedCoinGameSeconds = gameDurationSeconds;
            generateCoin();
        }

        if (gameDurationSeconds > lastTimeGeneratedCoinBagGameSeconds + COINBAG_GENERATION_INTERVAL) {
            lastTimeGeneratedCoinBagGameSeconds = gameDurationSeconds;
            generateCoinBag();
        }
        if (gameDurationSeconds > lastTimeGeneratedStatsUpSeconds + STATS_UP_GENERATION_INTERVAL) {
            lastTimeGeneratedStatsUpSeconds = gameDurationSeconds;
            generateStatsUp();
        }
    }

    /**
     * generates a new pickup coin at a random position on the map
     * the position is reachable for the player
     */
    private void generateCoin() {
        Vector2 position = RandomPositionGenerator.getRandomPositionWithMaxGroundGap(CoinPickup.SIZE);
        if (position != null) {
            CoinPickup coinPickup = new CoinPickup(position);
            SM.level.add(coinPickup);
        }
    }

    private void generateCoinBag() {
        Vector2 position = RandomPositionGenerator.getRandomPositionWithMaxGroundGap(CoinBagPickup.SIZE);
        if (position != null) {
            CoinBagPickup coinBagPickup = new CoinBagPickup(position);
            SM.level.add(coinBagPickup);
        }
    }

    private void generateStatsUp() {
        Vector2 position = RandomPositionGenerator.getRandomPositionWithMaxGroundGap(SpeedUpPickup.SIZE);
        Random rand = new Random();
        if (position != null) {
            int pickupNumber = rand.nextInt(2);
            switch(pickupNumber){
                case 0:
                    SpeedUpPickup speedUpPickup = new SpeedUpPickup(position);
                    SM.level.add(speedUpPickup);
                    break;
                case 1:
                    TripleJumpPickup tripleJumpPickup = new TripleJumpPickup(position);
                    SM.level.add(tripleJumpPickup);
                    break;
            }
        }
    }
}
