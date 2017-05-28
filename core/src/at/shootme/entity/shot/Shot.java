package at.shootme.entity.shot;

import at.shootme.entity.EntityCategory;
import at.shootme.entity.general.Entity;

/**
 * Created by Nicole on 28.05.2017.
 */
public abstract class Shot extends Entity {

    @Override
    public EntityCategory getCategory() {
        return EntityCategory.SHOT;
    }

    public abstract boolean shouldCollideWith(Entity entity);

    public abstract void collidedWith(Entity entity);
}
