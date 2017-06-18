package at.shootme.entity.shot;

import at.shootme.entity.EntityCategory;
import at.shootme.entity.general.Drawable;
import at.shootme.entity.general.Entity;

/**
 * Created by Nicole on 28.05.2017.
 */
public interface Shot extends Entity, Drawable {

    @Override
    default EntityCategory getCategory() {
        return EntityCategory.SHOT;
    }

    /**
     * determines if the given entity should collide with this shot
     * @param entity
     * @return
     */
    boolean shouldCollideWith(Entity entity);

    /**
     * shot collided with given entity
     * @param entity
     */
    void collidedWith(Entity entity);
}
