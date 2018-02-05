package at.shootme.networking.server;

import at.shootme.SM;
import at.shootme.entity.general.Entity;
import at.shootme.entity.general.EntityTypeHandler;
import at.shootme.entity.player.Player;
import at.shootme.networking.client.ClientEventProcessor;
import at.shootme.networking.data.entity.EntityCreationMessage;
import at.shootme.networking.data.entity.EntityRemovedMessage;
import at.shootme.networking.data.entity.EntityStateChangeMessage;
import at.shootme.networking.data.entity.PlayerStateChangeMessage;
import at.shootme.networking.general.EventProcessor;
import at.shootme.networking.general.ServerClientConnection;
import at.shootme.state.data.GameState;
import at.shootme.state.data.GameStateType;

import java.util.logging.Logger;

/**
 * Created by Nicole on 17.06.2017.
 */
public class ServerEventProcessor extends EventProcessor {

    @Override
    public void received(ServerClientConnection connection, Object message) {
        if (message instanceof EntityStateChangeMessage) {
            EntityStateChangeMessage entityStateChangeMessage = (EntityStateChangeMessage) message;
            Entity entity = SM.level.getEntityById(entityStateChangeMessage.getEntityId());
            if (entity == null) {
                Logger.getLogger(ClientEventProcessor.class.getName()).info("entity with ID " + entityStateChangeMessage.getEntityId() + " was requested to be updated but has already been removed");
            } else {
                updateEntity(connection, entity, entityStateChangeMessage);
            }
        } else if (message instanceof EntityCreationMessage) {
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

    private void updateEntity(ServerClientConnection connection, Entity entity, EntityStateChangeMessage entityStateChangeMessage) {
        entityStateChangeMessage.getEntityBodyGeneralState().applyTo(entity.getBody());
        if (entityStateChangeMessage instanceof PlayerStateChangeMessage) {
            Player player = (Player) entity;
            PlayerStateChangeMessage playerStateChangeMessage = (PlayerStateChangeMessage) entityStateChangeMessage;
            player.setViewDirection(playerStateChangeMessage.getViewDirection());
            player.setHorizontalMovementState(playerStateChangeMessage.getHorizontalMovementState());
            player.setAvailableJumps(playerStateChangeMessage.getAvailableJumps());
            player.setScore(playerStateChangeMessage.getScore());
            player.setHealth(playerStateChangeMessage.getHealth());
            player.setRemainingLives(playerStateChangeMessage.getRemainingLives());
            SM.server.getKryonetServer().sendToAllExceptUDP(connection.getKryonetConnection().getID(), playerStateChangeMessage);
        }
    }

    @Override
    public void disconnected(ServerClientConnection connection) {
        if (SM.state.getStateType() == GameStateType.IN_GAME) {
            if (connection.getPlayer() != null) {
                SM.level.queueForRemoval(connection.getPlayer());
                EntityRemovedMessage entityRemovedMessage = new EntityRemovedMessage();
                entityRemovedMessage.setEntityId(connection.getPlayer().getId());
                SM.server.getKryonetServer().sendToAllExceptTCP(connection.getKryonetConnection().getID(), entityRemovedMessage);
            }
        }
    }

    @Override
    public void idle(ServerClientConnection connection) {

    }
}
