package at.shootme.entity.shot;

import at.shootme.SM;
import at.shootme.entity.general.AbstractEntityTypeHandler;
import at.shootme.entity.general.Entity;
import at.shootme.entity.shot.StandardShot.StandardShotCreationMessage;
import at.shootme.networking.data.entity.EntityCreationMessage;

public class StandardShotHandler extends AbstractEntityTypeHandler {

    @Override
    public String getEntityName() {
        return StandardShot.class.getSimpleName();
    }

    @Override
    protected StandardShotCreationMessage createEntityCreationMessageInstance() {
        return new StandardShotCreationMessage();
    }

    @Override
    protected void setCustomProperties(Entity entity, EntityCreationMessage message) {
        StandardShotCreationMessage standardShotCreationMessage = (StandardShotCreationMessage) message;
        standardShotCreationMessage.setOriginatorEntityId(((StandardShot) entity).getOriginator().getId());
    }

    @Override
    protected StandardShot createEntityInternal(EntityCreationMessage entityCreationMessage) {
        StandardShotCreationMessage standardShotCreationMessage = (StandardShotCreationMessage) entityCreationMessage;
        Entity originator = SM.level.getEntityById(standardShotCreationMessage.getOriginatorEntityId());
        return new StandardShot(entityCreationMessage.getBodyGeneralState().getPosition(), entityCreationMessage.getBodyGeneralState().getLinearVelocity(), originator, SM.world);
    }
}
