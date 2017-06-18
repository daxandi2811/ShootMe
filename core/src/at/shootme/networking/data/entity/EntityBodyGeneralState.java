package at.shootme.networking.data.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class EntityBodyGeneralState {
    private Vector2 position;
    private Vector2 linearVelocity;
    private float angle;

    public EntityBodyGeneralState() {
    }

    public EntityBodyGeneralState(Body body) {
        this.position = body.getPosition().cpy();
        this.linearVelocity = body.getLinearVelocity().cpy();
        this.angle = body.getAngle();
    }

    public void applyTo(Body body) {
        body.setTransform(position, angle);
        body.setLinearVelocity(linearVelocity);
        body.setAwake(true);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    public void setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity = linearVelocity;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
