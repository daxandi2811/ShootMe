package at.shootme.entity.pickups;

import at.shootme.entity.general.AbstractEntityTypeHandler;
import at.shootme.entity.general.Entity;
import at.shootme.networking.data.entity.EntityCreationMessage;

/**
 * Created by Nicole on 18.06.2017.
 */
public class TripleJumpPickupHandler extends AbstractEntityTypeHandler {

    /**
     * returns the name of the entity
     * @return
     */
    public String getEntityName() {
        return TripleJumpPickup.class.getSimpleName();
    }

    @Override
    protected EntityCreationMessage createEntityCreationMessageInstance() {
        return new EntityCreationMessage();
    }

    @Override
    protected void setCustomProperties(Entity entity, EntityCreationMessage message) {
    }

    @Override
    protected TripleJumpPickup createEntityInternal(EntityCreationMessage entityCreationMessage) {
        TripleJumpPickup tripleJumpPickup = new TripleJumpPickup(entityCreationMessage.getBodyGeneralState().getPosition());
        return tripleJumpPickup;
    }
}
