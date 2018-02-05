package at.shootme.entity.pickups;

import at.shootme.SM;
import at.shootme.entity.general.SimpleDrawableEntity;
import at.shootme.entity.player.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static at.shootme.SM.world;
import static at.shootme.ShootMeConstants.METERS_TO_PIXELS;
import static at.shootme.ShootMeConstants.PIXELS_TO_METERS;

/**
 * Created by Nicole on 18.06.2017.
 */
public class SpeedUpPickup extends SimpleDrawableEntity implements Pickup {

    public static final Vector2 SIZE = new Vector2(100, 70).scl(PIXELS_TO_METERS);
    public final PickupType type = PickupType.SPEED_UP;
    private final float speedMultiplier = 1.5f;
    private Sound pickupSound = Gdx.audio.newSound(Gdx.files.internal("assets/speedUpPickup.wav"));

    public SpeedUpPickup(Vector2 position) {
        sprite = new Sprite(SM.textureStore.getOrLoadTexture("assets/speedUp.png"));
        sprite.setSize(SIZE.cpy().scl(METERS_TO_PIXELS).x, SIZE.cpy().scl(METERS_TO_PIXELS).y);
        sprite.setOriginCenter();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;

        bodyDef.position.set(position.x, position.y);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(SIZE.x / 2, SIZE.y / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        fixture = body.createFixture(fixtureDef);
        shape.dispose();
    }

    @Override
    public void pickedUpBy(Player player) {
        if (SM.isClient() && SM.gameScreen.getPlayer() == player) {
            player.resetAllStats();
            player.setSpeed(speedMultiplier);
            player.setCurrentPickup(type);
            player.setLastStatsUpAcquiredInGameSeconds(SM.gameScreen.getGameDurationSeconds());
        }
        if (SM.isClient()) pickupSound.play();
        SM.level.queueForRemoval(this);
    }

    @Override
    protected void finalize() throws Throwable {
        pickupSound.dispose();
        super.finalize();
    }
}
