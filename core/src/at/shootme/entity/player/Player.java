package at.shootme.entity.player;

import at.shootme.ShootMeConstants;
import at.shootme.beans.HorizontalMovementState;
import at.shootme.entity.shot.StandardShot;
import at.shootme.beans.VerticalMovementState;
import at.shootme.entity.general.Drawable;
import at.shootme.entity.general.Entity;
import at.shootme.util.vectors.Vector2Util;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static at.shootme.beans.HorizontalMovementState.*;
import static at.shootme.beans.VerticalMovementState.*;

/**
 * Created by Alexander Dietrich on 01.05.2017.
 */
public class Player extends Entity implements ShootMeConstants, Drawable {

    private Sprite sprite;
    private Body body;
    private Fixture fixture;
    private HorizontalMovementState horizontalMovementState = STOPPED;
    private VerticalMovementState verticalMovementState = STANDING;

    private String texturepath;

    public Player() {
    }

    public void init(Vector2 position, World world) {

        Texture texture = new Texture(texturepath);
        sprite = new Sprite(texture);

        sprite.setSize(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setOriginCenter();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = 3f;
        bodyDef.position.set(position);

        body = world.createBody(bodyDef);
        body.setUserData(this);
        body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();

        //The minus 3 makes the polygon slightly smaller than the sprite so there are no visible gaps between the world and the player
        shape.setAsBox((sprite.getWidth() - 3) / 2 * PIXELS_TO_METERS, (sprite.getHeight() - 3) / 2 * PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.6f;
        fixtureDef.density = 1000f;

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

    public void setTexturepath(String texturepath) {
        this.texturepath = texturepath;
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

    public void move() {
        Vector2 velocity = body.getLinearVelocity();

        //Calculating horizontal movement
        if (horizontalMovementState == HorizontalMovementState.STOPPING && Math.abs(velocity.x) < 0.001f) {
            horizontalMovementState = STOPPED;
        }

        float desiredHorizontalVelocity = 0;
        switch (horizontalMovementState) {
            case LEFT:
                desiredHorizontalVelocity = Math.max(velocity.x - 5f, -14f);
                break;
            case STOPPING:
                desiredHorizontalVelocity = velocity.x;
                break;
            case RIGHT:
                desiredHorizontalVelocity = Math.min(velocity.x + 5f, 14f);
                break;
            case STOPPED:
                desiredHorizontalVelocity = 0;
        }

        float horizontalVelocityChange = desiredHorizontalVelocity - velocity.x;
        float horizontalForce = body.getMass() * horizontalVelocityChange;

        //Calculating vertical movement
        float verticalForce = 0;
        switch (verticalMovementState) {
            case AIRBORN:
            case STANDING:
                verticalForce = 0 * body.getMass();
                break;
            case JUMPING:
                verticalForce = 50 * body.getMass();
                verticalMovementState = STANDING;
                break;
        }


        body.applyLinearImpulse(new Vector2(horizontalForce, verticalForce), body.getWorldCenter(), true);
//        System.out.println(body.getLinearVelocity().x + "  " + body.getLinearVelocity().y);
    }

    public StandardShot shootAt(Vector2 clickPosition) {
        Vector2 playerPosition = body.getPosition();
        float angle = Vector2Util.getAngleFromAToB(playerPosition, clickPosition);
        Vector2 directionVector = Vector2Util.degreeToVector2(angle);
        int initialShotSpeed = 40;
        Vector2 initialShotVelocity = directionVector.scl(initialShotSpeed);
        StandardShot shot = new StandardShot(playerPosition, initialShotVelocity, getWorld());
//        System.out.println("playerPosition: "+ playerPosition + " --- " + "clickPosition: "+ clickPosition + " --- " + "initialShotVelocity: "+ initialShotVelocity);
//        System.out.println("directionVector: "+ directionVector + " --- " + "angle: "+ angle + " --- ");
        return shot;
    }

    private World getWorld() {
        return body.getWorld();
    }

    public void jump() {
        if (verticalMovementState != AIRBORN) verticalMovementState = JUMPING;
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setPosition(body.getPosition().x * METERS_TO_PIXELS - sprite.getWidth() / 2, body.getPosition().y * METERS_TO_PIXELS - sprite.getHeight() / 2);
        sprite.draw(batch);
    }
}
