package at.shootme.entity.level;

import at.shootme.entity.EntityCategory;
import at.shootme.entity.general.SimpleDrawableEntity;
import at.shootme.networking.data.entity.EntityCreationMessage;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static at.shootme.ShootMeConstants.METERS_TO_PIXELS;

/**
 * Created by Nicole on 28.05.2017.
 */
public class Platform extends SimpleDrawableEntity {

    private final Vector2 size;
    private String type;

    public Platform(Vector2 position, Vector2 size, String type, World world) {
        this.size = size;
        this.type = type;
        sprite = new Sprite(new Texture(type.equals("stone") ? "assets/greytexture.jpg" : (type.equals("sand") ? "assets/sandtexture.jpg" : "assets/browntexture.jpg")));
        sprite.setSize(size.cpy().scl(METERS_TO_PIXELS).x, this.size.cpy().scl(METERS_TO_PIXELS).y);
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
    public EntityCategory getCategory() {
        return EntityCategory.PLATFORM;
    }

    public Vector2 getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class PlatformCreationMessage extends EntityCreationMessage {

        private Vector2 size;
        private String type;

        public Vector2 getSize() {
            return size;
        }

        public void setSize(Vector2 size) {
            this.size = size;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
