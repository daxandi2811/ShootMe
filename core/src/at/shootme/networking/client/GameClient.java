package at.shootme.networking.client;

import at.shootme.SM;
import at.shootme.networking.exceptions.NetworkingRuntimeException;
import at.shootme.networking.general.NetworkingConstants;
import at.shootme.networking.general.ServerClientConnection;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

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

    public void preStep() {
        connection.preStep();
    }

    public void prePhysics(){
        connection.prePhysics();
    }

    public void postStep(){
        connection.postStep();
    }

    public ServerClientConnection getConnection() {
        return connection;
    }
}
