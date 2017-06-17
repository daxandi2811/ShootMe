package at.shootme.networking.client;

import at.shootme.SM;
import at.shootme.entity.general.Entity;
import at.shootme.entity.general.EntityTypeHandler;
import at.shootme.entity.shot.StandardShot;
import at.shootme.networking.data.entity.EntityCreationMessage;
import at.shootme.networking.data.entity.state.EntityStateChangeMessage;
import at.shootme.networking.general.EventProcessor;
import at.shootme.networking.general.ServerClientConnection;
import at.shootme.state.data.GameState;
import com.sun.istack.internal.logging.Logger;

public class ClientEventProcessor extends EventProcessor {

    @Override
    public void received(ServerClientConnection connection, Object message) {
        if (message instanceof EntityStateChangeMessage) {
            EntityStateChangeMessage entityStateChangeMessage = (EntityStateChangeMessage) message;
            Entity entity = SM.level.getEntityById(entityStateChangeMessage.getEntityId());
            if (entity == null) {
                Logger.getLogger(ClientEventProcessor.class).info("entity with ID " + entityStateChangeMessage.getEntityId() + " was requested to be updated but has already been removed");
            } else {
                updateEntity(entity, entityStateChangeMessage);
            }
        } else if (message instanceof EntityCreationMessage) {
            EntityCreationMessage entityCreationMessage = (EntityCreationMessage) message;
            EntityTypeHandler handler = SM.entityTypeHandlerRegistry.getHandlerFor(entityCreationMessage.getEntityName());
            Entity entity = handler.createEntity(entityCreationMessage);
            SM.level.add(entity);
            receivedEntitiesThisTick.add(entity);
        } else if (message instanceof GameState) {
            SM.gameStateManager.apply((GameState) message);
        }
    }

    private void updateEntity(Entity entity, EntityStateChangeMessage entityStateChangeMessage) {
        if (entity == SM.gameScreen.getPlayer()) {
            // TODO check if way too far from server
            return;
        }
        if (entity instanceof StandardShot) {
            Entity originator = ((StandardShot) entity).getOriginator();
            if (originator == SM.gameScreen.getPlayer()) {
                return;
            }
        }
        entityStateChangeMessage.getEntityBodyGeneralState().applyTo(entity.getBody());
    }

    @Override
    public void disconnected(ServerClientConnection connection) {

    }

    @Override
    public void idle(ServerClientConnection connection) {

    }
}
