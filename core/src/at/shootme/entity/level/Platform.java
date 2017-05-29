package at.shootme.entity.level;

import at.shootme.entity.EntityCategory;
import at.shootme.entity.general.Drawable;
import at.shootme.entity.general.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import static at.shootme.ShootMeConstants.METERS_TO_PIXELS;

/**
 * Created by Nicole on 28.05.2017.
 */
public class Platform extends Entity implements Drawable {

    private Sprite sprite;
    private Body body;
    private Fixture fixture;

    public Platform(Vector2 position, Vector2 size, Texture texture, World world) {
        sprite = new Sprite(texture);
        sprite.setSize(size.cpy().scl(METERS_TO_PIXELS).x, size.cpy().scl(METERS_TO_PIXELS).y);
        sprite.setOriginCenter();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;

        bodyDef.position.set(position.x + size.x / 2,
                position.y + size.y / 2);

        body = world.createBody(bodyDef);
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x / 2, size.y / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        fixture = body.createFixture(fixtureDef);
        shape.dispose();
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setPosition(body.getPosition().x * METERS_TO_PIXELS - sprite.getWidth() / 2, body.getPosition().y * METERS_TO_PIXELS - sprite.getHeight() / 2);
        sprite.draw(batch);
    }

    @Override
    public EntityCategory getCategory() {
        return EntityCategory.PLATFORM;
    }

    @Override
    public Body getBody() {
        return body;
    }
}
