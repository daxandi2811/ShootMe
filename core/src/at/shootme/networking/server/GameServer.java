package at.shootme.networking.server;

import at.shootme.SM;
import at.shootme.entity.general.Entity;
import at.shootme.networking.data.PlayerSkin;
import at.shootme.networking.data.ServerTick;
import at.shootme.networking.data.entity.EntityBodyGeneralState;
import at.shootme.networking.data.entity.EntityCreationMessage;
import at.shootme.networking.data.entity.EntityStateChangeMessage;
import at.shootme.networking.data.framework.MessageBatch;
import at.shootme.networking.data.framework.StepCommunicationFlush;
import at.shootme.networking.exceptions.NetworkingRuntimeException;
import at.shootme.networking.general.EventProcessor;
import at.shootme.networking.general.NetworkingConstants;
import at.shootme.networking.general.ServerClientConnection;
import at.shootme.state.data.GameStateType;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.KryoSerialization;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static at.shootme.networking.general.NetworkingConstants.TCP_PORT;
import static at.shootme.networking.general.NetworkingConstants.UDP_PORT;
import static at.shootme.networking.general.NetworkingUtils.createEntityCreationMessages;

/**
 * Created by Nicole on 17.06.2017.
 */
public class GameServer {

    private Server kryonetServer;
    private List<ServerClientConnection> connections = new ArrayList<>();
    private List<Connection> newConnections = new ArrayList<>();

    /**
     * starts the kryonetServer and adds the ConnectionListener
     */
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
        handleNewConnections();
        connections.forEach(ServerClientConnection::preStep);
    }

    /**
     * handles the new connected clients
     */
    private void handleNewConnections() {
        newConnections.forEach((newConnection) -> handleNewConnection(newConnection));
        newConnections.clear();
    }

    public void prePhysics() {
        connections.forEach(ServerClientConnection::prePhysics);
    }

    /**
     * clear all disconnected clients and sends updates
     */
    public void postStep() {
        removeDisconnected();
        connections.forEach(ServerClientConnection::postStep);
        sendEntityCreationMessagesForNewEntitiesGeneratedAtServer();
        sendStateUpdateMessages();
        sendGameTick();
    }

    /**
     * removes the disconnected client
     */
    private void removeDisconnected() {
        connections.removeIf(serverClientConnection -> !serverClientConnection.getKryonetConnection().isConnected());
    }

    /**
     * creates a new ServerTick and updates the time
     */
    private void sendGameTick() {
        ServerTick serverTick = new ServerTick();
        serverTick.setCurrentGameDurationSeconds(SM.gameScreen.getGameDurationSeconds());
        getKryonetServer().sendToAllUDP(serverTick);
    }

    public void processReceivedWithoutGameEntities() {
        handleNewConnections();
        connections.forEach(ServerClientConnection::processReceivedWithoutGameEntities);
        kryonetServer.sendToAllTCP(new StepCommunicationFlush());
        removeDisconnected();
    }

    /**
     * sends a message to all clients that new entities were created
     */
    private void sendEntityCreationMessagesForNewEntitiesGeneratedAtServer() {
        List<Entity> addedEntitiesThisTick = SM.level.getAddedEntitiesThisTick();
        Set<Entity> entitesCreatedByIncomingMessages = connections.stream()
                .map(ServerClientConnection::getEventProcessor)
                .map(EventProcessor::getReceivedEntitiesThisTick)
                .flatMap(entities -> entities.stream())
                .collect(Collectors.toSet());
        List<Entity> generatedEntitiesAtServer = addedEntitiesThisTick.stream()
                .filter(entity -> !entitesCreatedByIncomingMessages.contains(entity))
                .collect(Collectors.toList());
        if (!generatedEntitiesAtServer.isEmpty()) {
            List<EntityCreationMessage> entityCreationMessages = createEntityCreationMessages(generatedEntitiesAtServer);
            kryonetServer.sendToAllTCP(MessageBatch.create(entityCreationMessages));
        }

        connections.forEach(serverClientConnection -> serverClientConnection.getEventProcessor().getReceivedEntitiesThisTick().clear());
    }

    /**
     * updates the State of the Game and informs all clients
     */
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

    /**
     * sends the new state of an entity
     * @param entity
     * @return
     */
    private EntityStateChangeMessage createEntityStateChangeMessage(Entity entity) {
        EntityStateChangeMessage message = new EntityStateChangeMessage();
        message.setEntityId(entity.getId());
        message.setEntityBodyGeneralState(new EntityBodyGeneralState(entity.getBody()));
        return message;
    }

    /**
     * gives the client a player skin and handles the new connection
     * @param newConnection
     */
    private void handleNewConnection(Connection newConnection) {
        ServerClientConnection newServerClientConnection = new ServerClientConnection(newConnection, new ServerEventProcessor());
        connections.add(newServerClientConnection);

        newServerClientConnection.sendTCP(SM.nextPlayerSkin);
        setNextPlayerSkin();
        newServerClientConnection.getKryonetConnection().sendTCP(SM.state);
        if (SM.state.getStateType() == GameStateType.IN_GAME) {
            List<EntityCreationMessage> entityCreationMessages = createEntityCreationMessages(SM.level.getEntities());
            newServerClientConnection.sendTCP(MessageBatch.create(entityCreationMessages));
        }
        newServerClientConnection.sendFlush();
    }

    /**
     * defines the next skin for the next client who is connecting
     */
    private void setNextPlayerSkin() {
        int currentIndex = Arrays.asList(PlayerSkin.values()).indexOf(SM.nextPlayerSkin);
        SM.nextPlayerSkin = PlayerSkin.values()[(currentIndex + 1) % (PlayerSkin.values().length)];
    }

    public Server getKryonetServer() {
        return kryonetServer;
    }

    public List<ServerClientConnection> getConnections() {
        return connections;
    }

    private class NewConnectionListener extends Listener {

        @Override
        public void connected(Connection connection) {
            newConnections.add(connection);
        }
    }

}
