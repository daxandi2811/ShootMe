package at.shootme.networking.server;

import at.shootme.SM;
import at.shootme.entity.general.Entity;
import at.shootme.entity.general.EntityTypeHandler;
import at.shootme.networking.data.entity.EntityCreationMessage;
import at.shootme.networking.data.entity.state.EntityBodyGeneralState;
import at.shootme.networking.data.entity.state.EntityStateChangeMessage;
import at.shootme.networking.data.framework.MessageBatch;
import at.shootme.networking.exceptions.NetworkingRuntimeException;
import at.shootme.networking.general.NetworkingConstants;
import at.shootme.networking.general.ServerClientConnection;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.KryoSerialization;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static at.shootme.networking.general.NetworkingConstants.TCP_PORT;
import static at.shootme.networking.general.NetworkingConstants.UDP_PORT;

public class GameServer {

    private Server kryonetServer;
    private List<ServerClientConnection> connections = new ArrayList<>();

    public void open() {
        kryonetServer = new Server(NetworkingConstants.WRITE_BUFFER_SIZE, NetworkingConstants.OBJECT_BUFFER_SIZE, new KryoSerialization());
        SM.kryoRegistrar.registerClasses(kryonetServer.getKryo());
        try {
            kryonetServer.bind(TCP_PORT, UDP_PORT);
        } catch (IOException e) {
            throw new NetworkingRuntimeException(e);
        }
        kryonetServer.start();
        kryonetServer.addListener(new NewConnectionListener());
    }

    public void preStep() {
        connections.forEach(ServerClientConnection::preStep);
    }

    public void prePhysics() {
        connections.forEach(ServerClientConnection::prePhysics);
    }

    public void postStep() {
        connections.forEach(ServerClientConnection::postStep);
        sendEntityCreationMessagesForNewEntities();
        sendStateUpdateMessages();
    }

    private void sendEntityCreationMessagesForNewEntities() {
        List<Entity> addedEntitiesThisTick = SM.level.getAddedEntitiesThisTick();
        if (!addedEntitiesThisTick.isEmpty()) {
            List<EntityCreationMessage> entityCreationMessages = addedEntitiesThisTick.stream().map(entity -> {
                EntityTypeHandler handler = SM.entityTypeHandlerRegistry.getHandlerFor(entity);
                return handler.createEntityCreationMessage(entity);
            }).collect(Collectors.toList());
            kryonetServer.sendToAllUDP(MessageBatch.create(entityCreationMessages));
        }
    }

    private void sendStateUpdateMessages() {
        List<Entity> entities = SM.level.getEntities();
        List<Entity> addedEntitiesThisTick = SM.level.getAddedEntitiesThisTick();
        if (!addedEntitiesThisTick.isEmpty()) {
            entities = entities.stream()
                    .filter(entity -> !addedEntitiesThisTick.contains(entity))
                    .collect(Collectors.toList());
        }

        List<Entity> toBeSyncedEntities = entities.stream()
                .filter(entity -> entity.getBody().isAwake())
                .collect(Collectors.toList());
        List<EntityStateChangeMessage> entityStateChangeMessages = toBeSyncedEntities.stream()
                .map(this::createEntityStateChangeMessage)
                .collect(Collectors.toList());
        kryonetServer.sendToAllUDP(MessageBatch.create(entityStateChangeMessages));
    }

    private EntityStateChangeMessage createEntityStateChangeMessage(Entity entity) {
        EntityStateChangeMessage message = new EntityStateChangeMessage();
        message.setEntityId(entity.getId());
        message.setEntityBodyGeneralState(new EntityBodyGeneralState(entity.getBody()));
        return message;
    }

    private void handleNewConnection(Connection newConnection) {
        ServerClientConnection serverClientConnection = new ServerClientConnection(newConnection, new ServerEventProcessor());
        connections.add(serverClientConnection);
    }

    private class NewConnectionListener extends Listener {

        @Override
        public void connected(Connection connection) {
            handleNewConnection(connection);
        }
    }

}
