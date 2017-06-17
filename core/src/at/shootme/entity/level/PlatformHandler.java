package at.shootme.entity.level;


import at.shootme.SM;
import at.shootme.entity.general.AbstractEntityTypeHandler;
import at.shootme.entity.general.Entity;
import at.shootme.entity.level.Platform.PlatformCreationMessage;
import at.shootme.networking.data.entity.EntityCreationMessage;

public class PlatformHandler extends AbstractEntityTypeHandler {

    @Override
    public String getEntityName() {
        return Platform.class.getSimpleName();
    }

    @Override
    protected PlatformCreationMessage createEntityCreationMessageInstance() {
        return new PlatformCreationMessage();
    }

    @Override
    protected void setCustomProperties(Entity entity, EntityCreationMessage message) {
        PlatformCreationMessage platformCreationMessage = (PlatformCreationMessage) message;
        Platform platformEntity = (Platform) entity;
        platformCreationMessage.setSize(platformEntity.getSize());
        platformCreationMessage.setType(((Platform) entity).getType());
    }

    @Override
    protected Entity createEntityInternal(EntityCreationMessage entityCreationMessage) {
        PlatformCreationMessage platformCreationMessage = (PlatformCreationMessage) entityCreationMessage;
        return new Platform(entityCreationMessage.getBodyGeneralState().getPosition(), platformCreationMessage.getSize(), platformCreationMessage.getType(), SM.world);
    }
}
