package at.shootme.entity.shot;

import at.shootme.SM;
import at.shootme.entity.general.Entity;
import at.shootme.entity.general.SimpleDrawableEntity;
import at.shootme.networking.data.entity.EntityCreationMessage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static at.shootme.ShootMeConstants.PIXELS_TO_METERS;

/**
 * Created by Nicole on 05.05.2017.
 */
public class StandardShot extends SimpleDrawableEntity implements Shot {

    private static final int TEXTURE_SCALE = 3;
    private static final String TEXTUREPATH = "assets/standard_bullet.png";
    private Entity originator;

    public StandardShot(Vector2 position, Vector2 initialVelocity, Entity originator, World world) {
        this.originator = originator;

        Texture texture = new Texture(TEXTUREPATH);
        sprite = new Sprite(texture);
        sprite.setScale(TEXTURE_SCALE);

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
    }

    @Override
    public boolean shouldCollideWith(Entity entity) {
        return this.originator != entity;
    }

    @Override
    public void collidedWith(Entity entity) {
        SM.level.queueForRemoval(entity);
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
}
