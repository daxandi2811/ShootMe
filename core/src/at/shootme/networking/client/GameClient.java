package at.shootme.networking.client;

import at.shootme.GameScreen;
import at.shootme.SM;
import at.shootme.entity.general.Entity;
import at.shootme.entity.player.Player;
import at.shootme.networking.data.entity.EntityBodyGeneralState;
import at.shootme.networking.data.entity.EntityCreationMessage;
import at.shootme.networking.data.entity.PlayerStateChangeMessage;
import at.shootme.networking.data.framework.MessageBatch;
import at.shootme.networking.exceptions.NetworkingRuntimeException;
import at.shootme.networking.general.EventProcessor;
import at.shootme.networking.general.NetworkingConstants;
import at.shootme.networking.general.ServerClientConnection;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static at.shootme.networking.general.NetworkingUtils.createEntityCreationMessages;

public class GameClient {

    private ServerClientConnection connection;
    private Client kryonetClient;

    public void connect() {
        kryonetClient = new Client(NetworkingConstants.WRITE_BUFFER_SIZE, NetworkingConstants.OBJECT_BUFFER_SIZE);
        SM.kryoRegistrar.registerClasses(kryonetClient.getKryo());
        kryonetClient.start();
        try {
            kryonetClient.connect(1000, "localhost", NetworkingConstants.TCP_PORT, NetworkingConstants.UDP_PORT);
        } catch (IOException e) {
            throw new NetworkingRuntimeException(e);
        }
        connection = new ServerClientConnection(kryonetClient, new ClientEventProcessor());
    }

    public void processReceived() {
        connection.processReceived();
    }

    private void sendEntityCreationMessagesForNewEntitiesGeneratedAtClient() {
        List<Entity> addedEntitiesThisTick = SM.level.getAddedEntitiesThisTick();
        Set<Entity> entitesCreatedByIncomingMessages = Stream.of(connection.getEventProcessor())
                .map(EventProcessor::getReceivedEntitiesThisTick)
                .flatMap(entities -> entities.stream())
                .collect(Collectors.toSet());
        List<Entity> generatedEntitiesAtClient = addedEntitiesThisTick.stream()
                .filter(entity -> !entitesCreatedByIncomingMessages.contains(entity))
                .collect(Collectors.toList());
        if (!generatedEntitiesAtClient.isEmpty()) {
            List<EntityCreationMessage> entityCreationMessages = createEntityCreationMessages(generatedEntitiesAtClient);
            connection.getKryonetConnection().sendTCP(MessageBatch.create(entityCreationMessages));
        }

        connection.getEventProcessor().getReceivedEntitiesThisTick().clear();
    }

    public void preStep() {
        connection.preStep();
    }

    public void prePhysics() {
        connection.prePhysics();
    }

    public void postStep() {
        connection.postStep();
        sendEntityCreationMessagesForNewEntitiesGeneratedAtClient();
        sendPlayerUpdate();
        connection.sendFlush();
    }

    private void sendPlayerUpdate() {
        Player player = SM.gameScreen.getPlayer();
        PlayerStateChangeMessage playerStateChangeMessage = new PlayerStateChangeMessage();
        playerStateChangeMessage.setEntityId(player.getId());
        playerStateChangeMessage.setEntityBodyGeneralState(new EntityBodyGeneralState(player.getBody()));
        playerStateChangeMessage.setAvailableJumps(player.getAvailableJumps());
        playerStateChangeMessage.setHorizontalMovementState(player.getHorizontalMovementState());
        playerStateChangeMessage.setViewDirection(player.getViewDirection());
        connection.getKryonetConnection().sendUDP(playerStateChangeMessage);
    }

    public ServerClientConnection getConnection() {
        return connection;
    }
}
