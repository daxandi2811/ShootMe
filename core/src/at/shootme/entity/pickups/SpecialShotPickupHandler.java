package at.shootme.entity.pickups;

import at.shootme.entity.general.AbstractEntityTypeHandler;
import at.shootme.entity.general.Entity;
import at.shootme.networking.data.entity.EntityCreationMessage;

/**
 * Created by Nicole on 18.06.2017.
 */
public class SpecialShotPickupHandler extends AbstractEntityTypeHandler {

    /**
     * returns the name of the entity
     * @return
     */
    public String getEntityName() {
        return SpecialShotPickup.class.getSimpleName();
    }

    @Override
    protected EntityCreationMessage createEntityCreationMessageInstance() {
        return new EntityCreationMessage();
    }

    @Override
    protected void setCustomProperties(Entity entity, EntityCreationMessage message) {
    }

    @Override
    protected SpecialShotPickup createEntityInternal(EntityCreationMessage entityCreationMessage) {
        SpecialShotPickup shotPickup = new SpecialShotPickup(entityCreationMessage.getBodyGeneralState().getPosition());
        return shotPickup;
    }
}
