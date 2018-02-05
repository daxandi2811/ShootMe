package at.shootme.entity.shot;

import at.shootme.SM;
import at.shootme.entity.general.Entity;
import at.shootme.entity.general.SimpleDrawableEntity;
import at.shootme.entity.player.Player;
import at.shootme.networking.data.entity.EntityCreationMessage;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static at.shootme.ShootMeConstants.METERS_TO_PIXELS;
import static at.shootme.ShootMeConstants.PIXELS_TO_METERS;

/**
 * Created by Nicole on 05.05.2017.
 */
public class StandardShot extends SimpleDrawableEntity implements Shot {

    private static final String TEXTUREPATH = "assets/standard_bullet.png";
    private static final int SCORE_FOR_KILLING_HIT = 100;
    public static final Vector2 SIZE = new Vector2(30, 30).scl(PIXELS_TO_METERS);
    private Entity originator;
    private Sound shotSound = Gdx.audio.newSound(Gdx.files.internal("assets/shot.wav"));

    public StandardShot(Vector2 position, Vector2 initialVelocity, Entity originator, World world) {
        this.originator = originator;

        Texture texture = SM.textureStore.getOrLoadTexture(TEXTUREPATH);
        sprite = new Sprite(texture);
        sprite.setSize(SIZE.cpy().scl(METERS_TO_PIXELS).x, SIZE.cpy().scl(METERS_TO_PIXELS).y);
        sprite.setOriginCenter();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearVelocity.set(initialVelocity);
        bodyDef.position.set(position);
        bodyDef.linearDamping = 0f;

        body = world.createBody(bodyDef);
        body.setUserData(this);
        body.setFixedRotation(true);
        body.setBullet(true);

        CircleShape shape = new CircleShape();
        shape.setRadius((sprite.getWidth() / 2) * PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 2f;
        fixtureDef.density = 1000f;


        fixture = body.createFixture(fixtureDef);
        shotSound.play();

    }

    @Override
    public boolean shouldCollideWith(Entity entity) {
        return this.originator != entity;
    }

    @Override
    public void collidedWith(Entity entity) {
        SM.level.queueForRemoval(this);
        if (SM.isClient()) {
            if (entity instanceof Player) {
                hitPlayer((Player) entity, 10);
            }
        }
    }

    private void hitPlayer(Player playerBeingHit, int damage) {
        if(!playerBeingHit.isDead()) {
            receiveScoreIfOriginatorIsPlayer(damage);
            playerBeingHit.receiveDamage(damage);
            if (playerBeingHit.isDead()) {
                receiveScoreIfOriginatorIsPlayer(SCORE_FOR_KILLING_HIT);
            }
        }
    }

    private void receiveScoreIfOriginatorIsPlayer(int score) {
        if (originator instanceof Player) {
            Player originatorPlayer = (Player) originator;
            originatorPlayer.receiveScore(score);
        }
    }

    @Override
    public Body getBody() {
        return body;
    }

    public Entity getOriginator() {
        return originator;
    }

    public static class StandardShotCreationMessage extends EntityCreationMessage {

        private String originatorEntityId;

        public String getOriginatorEntityId() {
            return originatorEntityId;
        }

        public void setOriginatorEntityId(String originatorEntityId) {
            this.originatorEntityId = originatorEntityId;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        shotSound.dispose();
        super.finalize();
    }
}
