package at.shootme.networking.server;

import at.shootme.SM;
import at.shootme.entity.general.Entity;
import at.shootme.entity.general.EntityTypeHandler;
import at.shootme.entity.player.Player;
import at.shootme.networking.data.entity.EntityCreationMessage;
import at.shootme.networking.general.EventProcessor;
import at.shootme.networking.general.ServerClientConnection;
import at.shootme.state.data.GameState;

public class ServerEventProcessor extends EventProcessor {

    @Override
    public void received(ServerClientConnection connection, Object message) {
        if (message instanceof EntityCreationMessage) {
            EntityCreationMessage entityCreationMessage = (EntityCreationMessage) message;
            EntityTypeHandler handler = SM.entityTypeHandlerRegistry.getHandlerFor(entityCreationMessage.getEntityName());
            Entity entity = handler.createEntity(entityCreationMessage);
            SM.level.add(entity);
            if (entityCreationMessage instanceof Player.PlayerCreationMessage) {
                connection.setPlayer((Player) entity);
            }
            receivedEntitiesThisTick.add(entity);
            SM.server.getKryonetServer().sendToAllExceptUDP(connection.getKryonetConnection().getID(), message);
        } else if (message instanceof GameState) {
            SM.gameStateManager.apply((GameState) message);
        }
    }

    @Override
    public void disconnected(ServerClientConnection connection) {

    }

    @Override
    public void idle(ServerClientConnection connection) {

    }
}
