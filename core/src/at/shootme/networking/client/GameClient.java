package at.shootme.networking.client;

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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static at.shootme.networking.general.NetworkingUtils.createEntityCreationMessages;

/**
 * Created by Nicole on 17.06.2017.
 */
public class GameClient {

    private ServerClientConnection connection;
    private Client kryonetClient;

    public void connect(String host) {
        kryonetClient = new Client(NetworkingConstants.WRITE_BUFFER_SIZE, NetworkingConstants.OBJECT_BUFFER_SIZE);
        SM.kryoRegistrar.registerClasses(kryonetClient.getKryo());
        kryonetClient.start();
        connection = new ServerClientConnection(kryonetClient, new ClientEventProcessor());
        try {
            kryonetClient.connect(1000, host, NetworkingConstants.TCP_PORT, NetworkingConstants.UDP_PORT);
            kryonetClient.setKeepAliveUDP(5000);
            kryonetClient.setKeepAliveTCP(5000);
            kryonetClient.setTimeout(10000);
        } catch (IOException e) {
            throw new NetworkingRuntimeException(e);
        }
    }

    public void processReceivedWithoutGameEntities() {
        connection.processReceivedWithoutGameEntities();
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
        playerStateChangeMessage.setScore(player.getScore());
        playerStateChangeMessage.setHorizontalMovementState(player.getHorizontalMovementState());
        playerStateChangeMessage.setViewDirection(player.getViewDirection());
        connection.getKryonetConnection().sendUDP(playerStateChangeMessage);
    }

    public ServerClientConnection getConnection() {
        return connection;
    }
}
