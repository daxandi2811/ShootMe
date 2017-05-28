package at.shootme.entity.general;

import at.shootme.entity.EntityCategory;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Nicole on 28.05.2017.
 */
public abstract class Entity {

    public abstract EntityCategory getCategory();

    /**
     * @return the body of this entity (or <code>null</code> if no body exists for this entity)
     */
    public abstract Body getBody();
}
