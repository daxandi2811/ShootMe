package at.shootme.entity.general;

import at.shootme.networking.data.entity.EntityBodyGeneralState;
import at.shootme.networking.data.entity.EntityCreationMessage;

/**
 * Created by Nicole on 17.06.2017.
 */
public abstract class AbstractEntityTypeHandler implements EntityTypeHandler {

    public final EntityCreationMessage createEntityCreationMessage(Entity entity) {
        EntityCreationMessage entityCreationMessageInstance = createEntityCreationMessageInstance();
        entityCreationMessageInstance.setId(entity.getId());
        entityCreationMessageInstance.setEntityName(getEntityName());
        entityCreationMessageInstance.setBodyGeneralState(new EntityBodyGeneralState(entity.getBody()));
        setCustomProperties(entity, entityCreationMessageInstance);
        return entityCreationMessageInstance;
    }

    /**
     * creates an instance of the entity creation message
     * @return
     */
    protected abstract EntityCreationMessage createEntityCreationMessageInstance();

    /**
     * setter for costum properties
     * @param entity
     * @param message
     */
    protected abstract void setCustomProperties(Entity entity, EntityCreationMessage message);

    @Override
    public final Entity createEntity(EntityCreationMessage entityCreationMessage) {
        Entity entity = createEntityInternal(entityCreationMessage);
        entity.setId(entityCreationMessage.getId());
        return entity;
    }

    protected abstract Entity createEntityInternal(EntityCreationMessage entityCreationMessage);
}
