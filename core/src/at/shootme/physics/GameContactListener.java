package at.shootme.physics;

import at.shootme.entity.EntityCategory;
import at.shootme.entity.general.Entity;
import at.shootme.entity.level.Platform;
import at.shootme.entity.player.Player;
import com.badlogic.gdx.physics.box2d.*;
import javafx.collections.transformation.SortedList;

/**
 * Created by Nicole on 28.05.2017.
 */
public class GameContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        EntityCategory categoryA = entityA.getCategory();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
        EntityCategory categoryB = entityB.getCategory();

        if (categoryA.ordinal() > categoryB.ordinal()) {
            // sort according to category
            Entity tmpEntity = entityA;
            entityA = entityB;
            entityB = tmpEntity;
            categoryA = entityA.getCategory();
            categoryB = entityB.getCategory();
        }

        if (categoryA == EntityCategory.PLAYER && categoryB == EntityCategory.PLATFORM) {
            // TODO find out if horizontal or vertical collision
            Player player = (Player) entityA;
            player.hitGround((Platform) entityB);
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
