package at.shootme.entity.pickups;

import at.shootme.entity.general.AbstractEntityTypeHandler;
import at.shootme.entity.general.Entity;
import at.shootme.networking.data.entity.EntityCreationMessage;

public class CoinPickupHandler extends AbstractEntityTypeHandler {

    public String getEntityName() {
        return CoinPickup.class.getSimpleName();
    }

    @Override
    protected EntityCreationMessage createEntityCreationMessageInstance() {
        return new EntityCreationMessage();
    }

    @Override
    protected void setCustomProperties(Entity entity, EntityCreationMessage message) {
    }

    @Override
    protected CoinPickup createEntityInternal(EntityCreationMessage entityCreationMessage) {
        CoinPickup coinPickup = new CoinPickup(entityCreationMessage.getBodyGeneralState().getPosition());
        return coinPickup;
    }
}
