package at.shootme.entity.pickups;

import at.shootme.entity.general.AbstractEntityTypeHandler;
import at.shootme.entity.general.Entity;
import at.shootme.networking.data.entity.EntityCreationMessage;

/**
 * Created by Steffi on 04.02.2018.
 */
public class CoinBagPickupHandler extends AbstractEntityTypeHandler {

    public String getEntityName() {
        return CoinBagPickup.class.getSimpleName();
    }

    @Override
    protected EntityCreationMessage createEntityCreationMessageInstance() {
        return new EntityCreationMessage();
    }

    @Override
    protected void setCustomProperties(Entity entity, EntityCreationMessage message) {
    }

    @Override
    protected CoinBagPickup createEntityInternal(EntityCreationMessage entityCreationMessage) {
        CoinBagPickup coinBagPickup = new CoinBagPickup(entityCreationMessage.getBodyGeneralState().getPosition());
        return coinBagPickup;
    }
}
