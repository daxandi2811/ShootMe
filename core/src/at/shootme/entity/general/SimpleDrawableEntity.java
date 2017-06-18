package at.shootme.entity.general;

import at.shootme.ShootMeConstants;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public abstract class SimpleDrawableEntity implements Entity, Drawable {

    protected String id;
    protected Sprite sprite;
    protected Body body;
    protected Fixture fixture;

    @Override
    public Body getBody() {
        return body;
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setPosition(body.getPosition().x * ShootMeConstants.METERS_TO_PIXELS - sprite.getWidth() / 2, body.getPosition().y * ShootMeConstants.METERS_TO_PIXELS - sprite.getHeight() / 2);
        sprite.draw(batch);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        if (this.id != null) throw new IllegalArgumentException();
        this.id = id;
    }
}
