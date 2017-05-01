package at.shootme.beans;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static at.shootme.beans.HorizontalMovementState.STOPPED;

/**
 * Created by Alexander Dietrich on 01.05.2017.
 */
public class Player {

    private Sprite sprite;
    private Body body;
    private Fixture fixture;
    private HorizontalMovementState horizontalMovementState = STOPPED;

    private static final String TEXTUREPATH = "assets/badlogic.jpg";

    public Player() {
    }

    public void init(Vector2 position, World world)
    {
        sprite = new Sprite(new Texture(TEXTUREPATH));

        sprite.setPosition(position.x, position.y);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;

        fixture = body.createFixture(fixtureDef);
        shape.dispose();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Body getBody() {
        return body;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public HorizontalMovementState getHorizontalMovementState() {
        return horizontalMovementState;
    }

    public void setHorizontalMovementState(HorizontalMovementState horizontalMovementState) {
        this.horizontalMovementState = horizontalMovementState;
    }

    /**
     * This method is called during rendering to move the sprite to the new position determined by the physics engine
     */
    public void moveSprite() {
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
    }

    public void moveHorizontally() {
        Vector2 velocity = body.getLinearVelocity();
        System.out.println(horizontalMovementState);
        float desiredVel = 0;
        if(horizontalMovementState == HorizontalMovementState.STOPPING && Math.abs(velocity.x) < 0.01f)
        {
            horizontalMovementState = STOPPED;
        }

        switch(horizontalMovementState)
        {
            case LEFT:
                desiredVel = Math.max( velocity.x - 10f, -100f);
                break;
            case STOPPING:
                desiredVel = velocity.x;
                break;
            case RIGHT:
                desiredVel =Math.min( velocity.x + 10f, 100f);
                break;
            case STOPPED:
                desiredVel = 0;
        }
        float velChange = desiredVel - velocity.x;
        float force = body.getMass() * velChange;
        body.applyLinearImpulse(new Vector2(force, 0), body.getWorldCenter(), true);
    }

    public void jump() {
        body.applyLinearImpulse(new Vector2(body.getLinearVelocity().x, 10000000), body.getWorldCenter(), true);
    }
}
