package at.shootme.pickupgeneration;

import at.shootme.SM;
import at.shootme.entity.pickups.CoinPickup;
import at.shootme.entity.player.Player;
import at.shootme.logic.StepListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nicole on 18.06.2017.
 */
public class DeadPlayerRespawner implements StepListener {

    public static final float PLAYER_RESPAWN_TIME = 2f;

    @Override
    public void beforeWorldStep(float timeStep) {
        float gameDurationSeconds = SM.gameScreen.getGameDurationSeconds();
        SM.level.getPlayers().stream()
                .filter(Player::isDead)
                .filter(player -> player.getLastDeathTimeInGameSeconds() + PLAYER_RESPAWN_TIME > gameDurationSeconds)
                .forEach(this::rez);
    }

    private void rez(Player player) {
        player.setHealth(100);
        Vector2 position = RandomPositionGenerator.getRandomPositionWithMaxGroundGap(player.getSize());
        if (position != null) {
            player.getBody().setTransform(position, 0);
        }
    }
}
