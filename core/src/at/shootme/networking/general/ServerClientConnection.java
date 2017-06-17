package at.shootme.networking.general;

import at.shootme.ShootMeConstants;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import at.shootme.entity.player.Player;
import at.shootme.networking.data.framework.MessageBatch;
import at.shootme.networking.data.framework.StepCommunicationFlush;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServerClientConnection {

    private Connection connection;
    private EventProcessor eventProcessor;
    private Player player;
    private ConnectionUpdateEventListener connectionUpdateEventListener;

    public ServerClientConnection(Connection connection, EventProcessor eventProcessor) {
        this.connection = connection;
        this.eventProcessor = eventProcessor;
        registerConnectionListener();
    }

    public void preStep() {
    }

    public void prePhysics() {
        updateReturnTripTimeAndTimeSyncIfNecessary();
        connectionUpdateEventListener.process();
    }

    public void postStep() {
        send(new StepCommunicationFlush());
    }

    public void send(StepCommunicationFlush message) {
        this.connection.sendTCP(message);
    }

    private void registerConnectionListener() {
        connectionUpdateEventListener = new ConnectionUpdateEventListener(eventProcessor);
        if (ShootMeConstants.SIMULATED_LAG_IN_MS == 0) {
            connection.addListener(connectionUpdateEventListener);
        } else {
            connection.addListener(new Listener.LagListener(ShootMeConstants.SIMULATED_LAG_IN_MS, ShootMeConstants.SIMULATED_LAG_IN_MS + ShootMeConstants.SIMULATED_LAG_IN_MS + 30, connectionUpdateEventListener));
        }
    }

    public void updateReturnTripTimeAndTimeSyncIfNecessary() {
        // TODO
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Connection getConnection() {
        return connection;
    }

    private class ConnectionUpdateEventListener extends Listener {

        private List<Runnable> queuedConnectionEvents = new ArrayList<>();
        private List<Object> queuedReceivedObjects = new ArrayList<>();
        private List<Object> queuedProcessableReceivedObjects = new ArrayList<>();
        private final Listener delegateListener;

        public ConnectionUpdateEventListener(Listener delegateListener) {
            this.delegateListener = Objects.requireNonNull(delegateListener);
        }

        @Override
        public void disconnected(Connection connection) {
            queueConnectionEvent(() -> delegateListener.disconnected(connection));
        }

        @Override
        public synchronized void received(Connection connection, final Object object) {
            if (object instanceof MessageBatch) {
                queuedReceivedObjects.addAll(((MessageBatch) object).getMessages());
            } else {
                queuedReceivedObjects.add(object);
            }
            if (object instanceof StepCommunicationFlush) {
                queuedProcessableReceivedObjects.addAll(queuedReceivedObjects);
                queuedReceivedObjects.clear();
            }
        }

        @Override
        public void idle(final Connection connection) {
            queueConnectionEvent(() -> delegateListener.idle(connection));
        }

        private synchronized void queueConnectionEvent(Runnable runnable) {
            queuedConnectionEvents.add(runnable);
        }

        public synchronized void process() {
            if (!queuedProcessableReceivedObjects.isEmpty()) {
                queuedProcessableReceivedObjects.forEach(o -> delegateListener.received(connection, o));
                queuedProcessableReceivedObjects.clear();
            }
            if (!queuedConnectionEvents.isEmpty()) {
                queuedConnectionEvents.forEach(Runnable::run);
                queuedConnectionEvents.clear();
            }
        }
    }
}
