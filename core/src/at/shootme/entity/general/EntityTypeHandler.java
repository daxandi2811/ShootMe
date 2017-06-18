package at.shootme.entity.general;


import at.shootme.networking.data.entity.EntityCreationMessage;

/**
 * Created by Nicole on 17.06.2017.
 */
public interface EntityTypeHandler {

    String getEntityName();

    /**
     * creates a creation message for the given entity to send it to others
     * @param entity
     * @return
     */
    EntityCreationMessage createEntityCreationMessage(Entity entity);

    /**
     * creates an entity with the given creation message/information
     * @param entityCreationMessage
     * @return
     */
    Entity createEntity(EntityCreationMessage entityCreationMessage);
}
