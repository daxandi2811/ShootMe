package at.shootme.pickupgeneration;

import at.shootme.SM;
import at.shootme.entity.pickups.CoinPickup;
import at.shootme.entity.pickups.SpeedUpPickup;
import at.shootme.entity.player.Player;
import at.shootme.logic.StepListener;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Nicole on 18.06.2017.
 */
public class StatsUpRemover implements StepListener {


    /**
     * generates a new coin every Coin_geneartion_Intervall
     * @param timeStep
     */
    @Override
    public void beforeWorldStep(float timeStep) {
        float gameDurationSeconds = SM.gameScreen.getGameDurationSeconds();
        for (Player player : SM.level.getPlayers()) {
            if(player.getCurrentPickup() != null) {
                if (gameDurationSeconds > player.getLastStatsUpAcquiredInGameSeconds() + player.getCurrentPickup().getDuration()) {
                    switch (player.getCurrentPickup()) {
                        case SPEED_UP:
                            player.setSpeed(1f);
                            break;
                        case TRIPLE_JUMP:
                            player.setMaxJumps(2);
                            break;
                    }
                    player.setCurrentPickup(null);
                }
            }
        }
    }

}
