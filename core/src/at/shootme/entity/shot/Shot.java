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

    boolean shouldCollideWith(Entity entity);

    void collidedWith(Entity entity);
}
