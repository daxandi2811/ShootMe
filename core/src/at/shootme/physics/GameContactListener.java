package at.shootme.physics;

import at.shootme.entity.general.Entity;
import at.shootme.entity.player.Player;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Nicole on 28.05.2017.
 */
public class GameContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        Entity entity = (Entity) contact.getFixtureA().getBody().getUserData();
        if(entity instanceof Player){

        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
