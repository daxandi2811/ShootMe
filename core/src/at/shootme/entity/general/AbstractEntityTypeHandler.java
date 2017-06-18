package at.shootme.entity.general;

import at.shootme.networking.data.entity.EntityBodyGeneralState;
import at.shootme.networking.data.entity.EntityCreationMessage;

public abstract class AbstractEntityTypeHandler implements EntityTypeHandler {

    public final EntityCreationMessage createEntityCreationMessage(Entity entity) {
        EntityCreationMessage entityCreationMessageInstance = createEntityCreationMessageInstance();
        entityCreationMessageInstance.setId(entity.getId());
        entityCreationMessageInstance.setEntityName(getEntityName());
        entityCreationMessageInstance.setBodyGeneralState(new EntityBodyGeneralState(entity.getBody()));
        setCustomProperties(entity, entityCreationMessageInstance);
        return entityCreationMessageInstance;
    }

    protected abstract EntityCreationMessage createEntityCreationMessageInstance();

    protected abstract void setCustomProperties(Entity entity, EntityCreationMessage message);

    @Override
    @SuppressWarnings("unchecked")
    public final Entity createEntity(EntityCreationMessage entityCreationMessage) {
        Entity entity = createEntityInternal(entityCreationMessage);
        entity.setId(entityCreationMessage.getId());
        return entity;
    }

    protected abstract Entity createEntityInternal(EntityCreationMessage entityCreationMessage);
}
