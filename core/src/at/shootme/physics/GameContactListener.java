package at.shootme.physics;

import at.shootme.entity.EntityCategory;
import at.shootme.entity.general.Entity;
import at.shootme.entity.level.Platform;
import at.shootme.entity.pickups.Pickup;
import at.shootme.entity.player.Player;
import at.shootme.entity.shot.Shot;
import at.shootme.logic.StepListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nicole on 28.05.2017.
 */
public class GameContactListener implements ContactListener, StepListener {

    private final Set<Contact> disabledContacts = new HashSet<>();

    @Override
    public void beginContact(Contact contact) {
        if (contact.isTouching()) {
            Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
            EntityCategory categoryA = entityA.getCategory();
            Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
            EntityCategory categoryB = entityB.getCategory();

            boolean invertedAB = categoryA.ordinal() > categoryB.ordinal();
            if (invertedAB) {
                // sort according to category
                Entity tmpEntity = entityA;
                entityA = entityB;
                entityB = tmpEntity;
                categoryA = entityA.getCategory();
                categoryB = entityB.getCategory();
            }

            Vector2 worldManifoldNormal = contact.getWorldManifold().getNormal();
            if (invertedAB) {
                worldManifoldNormal.scl(-1);
            }

            if (categoryA == EntityCategory.PLAYER && categoryB == EntityCategory.PLATFORM) {
//                System.out.println(ReflectionToStringBuilder.toString(contact.getWorldManifold()));
                Player player = (Player) entityA;
                if (worldManifoldNormal.y < 0) {
                    player.hitGround((Platform) entityB);
                } else if (worldManifoldNormal.x != 0) {
                    contact.setFriction(0);
                }
            }


            if (categoryA.isOneOf(EntityCategory.PLAYER, EntityCategory.PLATFORM) && categoryB == EntityCategory.SHOT) {
                Shot shot = (Shot) entityB;
                shot.collidedWith(entityA);
            }

            if (categoryA.isOneOf(EntityCategory.PLAYER) && categoryB.isOneOf(EntityCategory.PICKUP)) {
                Pickup pickup = (Pickup) entityB;
                pickup.pickedUpBy((Player) entityA);
                contact.setEnabled(false);
            }
        }
    }

    private void disableInPreSolve(Contact contact) {
        disabledContacts.add(contact);
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        if (disabledContacts.contains(contact)) {
            disable(contact);
        }
    }

    private void disable(Contact contact) {
        contact.setEnabled(false);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    @Override
    public void afterWorldStep(float timeStep) {
        disabledContacts.clear();
    }
}
