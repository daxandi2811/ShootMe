package at.shootme.networking.client;

import at.shootme.SM;
import at.shootme.entity.general.Entity;
import at.shootme.entity.general.EntityTypeHandler;
import at.shootme.entity.player.Player;
import at.shootme.entity.shot.StandardShot;
import at.shootme.networking.GameEndedMessage;
import at.shootme.networking.data.PlayerSkin;
import at.shootme.networking.data.ServerTick;
import at.shootme.networking.data.entity.EntityCreationMessage;
import at.shootme.networking.data.entity.EntityRemovedMessage;
import at.shootme.networking.data.entity.EntityStateChangeMessage;
import at.shootme.networking.data.entity.PlayerStateChangeMessage;
import at.shootme.networking.general.EventProcessor;
import at.shootme.networking.general.ServerClientConnection;
import at.shootme.state.data.GameState;
import at.shootme.screens.ConnectingScreen;

import java.util.logging.Logger;

/**
 * Created by Nicole on 17.06.2017.
 */
public class ClientEventProcessor extends EventProcessor {

    @Override
    public void received(ServerClientConnection connection, Object message) {
        if (message instanceof EntityStateChangeMessage) {
            if(SM.level == null){
                // in the rare case of receiving something before the level has been loaded when joining mid-game -> ignore
                return;
            }
            EntityStateChangeMessage entityStateChangeMessage = (EntityStateChangeMessage) message;
            Entity entity = SM.level.getEntityById(entityStateChangeMessage.getEntityId());
            if (entity == null) {
                Logger.getLogger(ClientEventProcessor.class.getName()).info("entity with ID " + entityStateChangeMessage.getEntityId() + " was requested to be updated but has already been removed");
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
        } else if (message instanceof PlayerSkin) {
            SM.nextPlayerSkin = (PlayerSkin) message;
        } else if (message instanceof EntityRemovedMessage) {
            EntityRemovedMessage entityRemovedMessage = (EntityRemovedMessage) message;
            String entityId = entityRemovedMessage.getEntityId();
            Entity entity = SM.level.getEntityById(entityId);
            if (entity == null) {
                Logger.getLogger(ClientEventProcessor.class.getName()).info("entity with ID " + entityId + " was requested to be removed but has already been removed");
            } else {
                SM.level.queueForRemoval(entity);
            }
        } else if (message instanceof ServerTick) {
            ServerTick serverTick = (ServerTick) message;
            SM.gameScreen.setGameDurationSeconds(serverTick.getCurrentGameDurationSeconds());
        } else if (message instanceof GameEndedMessage) {
            GameEndedMessage gameEndedMessage = (GameEndedMessage) message;
            SM.gameScreen.setGameEndedMessage(gameEndedMessage);
        }
    }

    private void updateEntity(Entity entity, EntityStateChangeMessage entityStateChangeMessage) {
        if (entity == SM.gameScreen.getPlayer()) {
            // ignore owned player to not port around player
            return;
        }
        if (entity instanceof StandardShot) {
            Entity originator = ((StandardShot) entity).getOriginator();
            if (originator == SM.gameScreen.getPlayer()) {
                return;
            }
        }
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
        }
    }

    @Override
    public void disconnected(ServerClientConnection connection) {
        SM.game.setScreen(new ConnectingScreen());
    }

    @Override
    public void idle(ServerClientConnection connection) {

    }
}
