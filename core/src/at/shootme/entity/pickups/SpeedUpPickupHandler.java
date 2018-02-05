package at.shootme.entity.pickups;

import at.shootme.entity.general.AbstractEntityTypeHandler;
import at.shootme.entity.general.Entity;
import at.shootme.networking.data.entity.EntityCreationMessage;

/**
 * Created by Nicole on 18.06.2017.
 */
public class SpeedUpPickupHandler extends AbstractEntityTypeHandler {

    /**
     * returns the name of the entity
     * @return
     */
    public String getEntityName() {
        return SpeedUpPickup.class.getSimpleName();
    }

    @Override
    protected EntityCreationMessage createEntityCreationMessageInstance() {
        return new EntityCreationMessage();
    }

    @Override
    protected void setCustomProperties(Entity entity, EntityCreationMessage message) {
    }

    @Override
    protected SpeedUpPickup createEntityInternal(EntityCreationMessage entityCreationMessage) {
        SpeedUpPickup speedPickup = new SpeedUpPickup(entityCreationMessage.getBodyGeneralState().getPosition());
        return speedPickup;
    }
}
