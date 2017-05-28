package at.shootme.physics;

import at.shootme.entity.EntityCategory;
import at.shootme.entity.general.Entity;
import at.shootme.entity.level.Platform;
import at.shootme.entity.player.Player;
import at.shootme.entity.shot.Shot;
import at.shootme.logic.StepListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nicole on 28.05.2017.
 */
public class GameContactFilter implements ContactFilter {

    @Override
    public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
        Entity entityA = (Entity) fixtureA.getBody().getUserData();
        EntityCategory categoryA = entityA.getCategory();
        Entity entityB = (Entity) fixtureB.getBody().getUserData();
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


        if (categoryA.isOneOf(EntityCategory.PLAYER, EntityCategory.PLATFORM) && categoryB == EntityCategory.SHOT) {
            Shot shot = (Shot) entityB;
            if (!shot.shouldCollideWith(entityA)) {
                return false;
            }
        }

        if (categoryA.isOneOf(EntityCategory.SHOT) && categoryB == EntityCategory.SHOT) {
            return false;
        }

        return true;
    }
}
