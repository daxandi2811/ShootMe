package at.shootme.networking.client;

import at.shootme.SM;
import at.shootme.entity.general.Entity;
import at.shootme.entity.general.EntityTypeHandler;
import at.shootme.networking.data.entity.EntityCreationMessage;
import at.shootme.networking.data.entity.state.EntityStateChangeMessage;
import at.shootme.networking.general.EventProcessor;
import com.esotericsoftware.kryonet.Connection;
import com.sun.istack.internal.logging.Logger;

public class ClientEventProcessor extends EventProcessor {

    @Override
    public void received(Connection connection, Object message) {
        if (message instanceof EntityStateChangeMessage) {
            EntityStateChangeMessage entityStateChangeMessage = (EntityStateChangeMessage) message;
            Entity entity = SM.level.getEntityById(entityStateChangeMessage.getEntityId());
            if (entity == null) {
                Logger.getLogger(ClientEventProcessor.class).info("entity with ID " + entityStateChangeMessage.getEntityId() + " was requested to be updated but has already been removed");
            } else {
                entityStateChangeMessage.getEntityBodyGeneralState().applyTo(entity.getBody());
            }
        } else if (message instanceof EntityCreationMessage) {
            EntityCreationMessage entityCreationMessage = (EntityCreationMessage) message;
            EntityTypeHandler handler = SM.entityTypeHandlerRegistry.getHandlerFor(entityCreationMessage.getEntityName());
            Entity entity = handler.createEntity(entityCreationMessage);
            SM.level.add(entity);
        }
    }

    @Override
    public void disconnected(Connection connection) {

    }

    @Override
    public void idle(Connection connection) {

    }
}
