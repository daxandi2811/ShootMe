package at.shootme.entity.general;


import at.shootme.networking.data.entity.EntityCreationMessage;

public interface EntityTypeHandler {

    String getEntityName();

    EntityCreationMessage createEntityCreationMessage(Entity entity);

    Entity createEntity(EntityCreationMessage entityCreationMessage);
}
