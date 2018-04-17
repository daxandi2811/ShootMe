package at.shootme.entity.shot;

import at.shootme.SM;
import at.shootme.entity.general.AbstractEntityTypeHandler;
import at.shootme.entity.general.Entity;
import at.shootme.entity.shot.SpecialShot.SpecialShotCreationMessage;
import at.shootme.networking.data.entity.EntityCreationMessage;

/**
 * Created by Nicole on 17.06.2017.
 */
public class SpecialShotHandler extends AbstractEntityTypeHandler {

    @Override
    public String getEntityName() {
        return SpecialShot.class.getSimpleName();
    }

    @Override
    protected SpecialShotCreationMessage createEntityCreationMessageInstance() {
        return new SpecialShotCreationMessage();
    }

    @Override
    protected void setCustomProperties(Entity entity, EntityCreationMessage message) {
        SpecialShotCreationMessage specialShotCreationMessage = (SpecialShotCreationMessage) message;
        specialShotCreationMessage.setOriginatorEntityId(((SpecialShot) entity).getOriginator().getId());
    }

    @Override
    protected SpecialShot createEntityInternal(EntityCreationMessage entityCreationMessage) {
        SpecialShotCreationMessage specialShotCreationMessage = (SpecialShotCreationMessage) entityCreationMessage;
        Entity originator = SM.level.getEntityById(specialShotCreationMessage.getOriginatorEntityId());
        return new SpecialShot(entityCreationMessage.getBodyGeneralState().getPosition(), entityCreationMessage.getBodyGeneralState().getLinearVelocity(), originator, SM.world);
    }
}
