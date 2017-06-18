package at.shootme.util.entity;

import at.shootme.entity.general.Entity;
import at.shootme.entity.level.Platform;

import java.util.UUID;

/**
 * Created by Nicole on 17.06.2017.
 */
public class EntityIdGenerator {

    /**
     * creates a random, unique id for the entity
     * @param entity
     * @return
     */
    public String createId(Entity entity) {
        if (entity instanceof Platform) {

        }
        return entity.getCategory().name() + "#" + UUID.randomUUID().toString();
    }
}
