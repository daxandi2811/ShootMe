package at.shootme.entity.shot;

import at.shootme.entity.EntityCategory;
import at.shootme.entity.general.Drawable;
import at.shootme.entity.general.Entity;
import at.shootme.util.vectors.Vector2Util;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static at.shootme.ShootMeConstants.METERS_TO_PIXELS;
import static at.shootme.ShootMeConstants.PIXELS_TO_METERS;

/**
 * Created by Nicole on 05.05.2017.
 */
public class StandardShot extends Entity implements Drawable {

    public static final int TEXTURE_SCALE = 3;
    private Sprite sprite;
    private Body body;
    private Fixture fixture;
    private static final String TEXTUREPATH = "assets/standard_bullet.png";

    public StandardShot(Vector2 position, Vector2 initialVelocity, World world){
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

        CircleShape shape = new CircleShape();
        shape.setRadius((sprite.getWidth() / 2) * PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 2f;
        fixtureDef.density = 1000f;


        fixture = body.createFixture(fixtureDef);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setPosition(body.getPosition().x * METERS_TO_PIXELS - sprite.getWidth() / 2, body.getPosition().y * METERS_TO_PIXELS - sprite.getHeight() / 2);
        sprite.draw(batch);
    }

    @Override
    public EntityCategory getCategory() {
        return EntityCategory.SHOT;
    }
}
