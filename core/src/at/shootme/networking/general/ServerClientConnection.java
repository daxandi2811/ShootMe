package at.shootme.networking.general;

import at.shootme.ShootMeConstants;
import at.shootme.entity.player.Player;
import at.shootme.networking.data.entity.EntityCreationMessage;
import at.shootme.networking.data.entity.EntityStateChangeMessage;
import at.shootme.networking.data.framework.MessageBatch;
import at.shootme.networking.data.framework.StepCommunicationFlush;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.FrameworkMessage;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ServerClientConnection {

    private Connection kryonetConnection;
    private EventProcessor eventProcessor;
    private Player player;
    private ConnectionUpdateEventListener connectionUpdateEventListener;

    public ServerClientConnection(Connection kryonetConnection, EventProcessor eventProcessor) {
        this.kryonetConnection = kryonetConnection;
        this.eventProcessor = eventProcessor;
        registerConnectionListener();
    }

    public void preStep() {
    }

    public void prePhysics() {
        updateReturnTripTimeAndTimeSyncIfNecessary();
        connectionUpdateEventListener.process();
    }

    public void processReceivedWithoutGameEntities() {
        connectionUpdateEventListener.processReceivedWithoutGameEntities();
    }

    public void postStep() {
        sendFlush();
    }

    public void sendFlush() {
        sendTCP(new StepCommunicationFlush());
    }

    public void sendTCP(Object message) {
        this.kryonetConnection.sendTCP(message);
    }

    public void sendTCPWithFlush(Object message) {
        sendTCP(message);
        sendFlush();
    }

    private void registerConnectionListener() {
        connectionUpdateEventListener = new ConnectionUpdateEventListener(eventProcessor);
        if (ShootMeConstants.SIMULATED_LAG_IN_MS == 0) {
            kryonetConnection.addListener(connectionUpdateEventListener);
        } else {
            kryonetConnection.addListener(new Listener.LagListener(ShootMeConstants.SIMULATED_LAG_IN_MS, ShootMeConstants.SIMULATED_LAG_IN_MS + ShootMeConstants.SIMULATED_LAG_IN_MS + 30, connectionUpdateEventListener));
        }
    }

    private void updateReturnTripTimeAndTimeSyncIfNecessary() {
        // TODO
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Connection getKryonetConnection() {
        return kryonetConnection;
    }

    public EventProcessor getEventProcessor() {
        return eventProcessor;
    }

    private class ConnectionUpdateEventListener extends Listener {

        private final EventProcessor eventProcessor;
        private List<Runnable> queuedConnectionEvents = new ArrayList<>();
        private List<Object> queuedReceivedObjects = new ArrayList<>();
        private List<Object> queuedProcessableReceivedObjects = new ArrayList<>();

        private ConnectionUpdateEventListener(EventProcessor eventProcessor) {
            this.eventProcessor = Objects.requireNonNull(eventProcessor);
        }

        @Override
        public void disconnected(Connection connection) {
            queueConnectionEvent(() -> eventProcessor.disconnected(ServerClientConnection.this));
        }

        @Override
        public synchronized void received(Connection connection, final Object object) {
            if (object instanceof StepCommunicationFlush) {
                queuedProcessableReceivedObjects.addAll(queuedReceivedObjects);
                queuedReceivedObjects.clear();
            } else if (object instanceof MessageBatch) {
                queuedReceivedObjects.addAll(((MessageBatch) object).getMessages());
            } else if (object instanceof FrameworkMessage) {
            } else {
                queuedReceivedObjects.add(object);
            }
        }

        @Override
        public void idle(final Connection connection) {
//            queueConnectionEvent(() -> eventProcessor.idle(ServerClientConnection.this));
        }

        private synchronized void queueConnectionEvent(Runnable runnable) {
            queuedConnectionEvents.add(runnable);
        }

        public void process() {
            List<Object> queuedProcessableReceivedObjects = getAndResetQueuedProcessableReceivedObjects();
            if (queuedProcessableReceivedObjects != null) {
                queuedProcessableReceivedObjects.forEach(o -> eventProcessor.received(ServerClientConnection.this, o));
            }
            List<Runnable> queuedConnectionEvents = getAndResetQueuedConnectionEvents();
            if (queuedConnectionEvents != null) {
                queuedConnectionEvents.forEach(Runnable::run);
            }
        }


        public void processReceivedWithoutGameEntities() {
            List<Object> messagesToProcess = null;
            synchronized (this) {
                if (!queuedProcessableReceivedObjects.isEmpty()) {
                    messagesToProcess = this.queuedProcessableReceivedObjects.stream()
                            .filter(o -> !(o instanceof EntityCreationMessage || o instanceof EntityStateChangeMessage))
                            .collect(Collectors.toList());
                    this.queuedProcessableReceivedObjects.removeAll(messagesToProcess);
                }
            }
            if (messagesToProcess != null) {
                messagesToProcess.forEach(o -> eventProcessor.received(ServerClientConnection.this, o));
            }

            List<Runnable> queuedConnectionEvents = getAndResetQueuedConnectionEvents();
            if (queuedConnectionEvents != null) {
                queuedConnectionEvents.forEach(Runnable::run);
            }
        }

        private synchronized List<Runnable> getAndResetQueuedConnectionEvents() {
            List<Runnable> queuedConnectionEvents = null;
            if (!this.queuedConnectionEvents.isEmpty()) {
                queuedConnectionEvents = this.queuedConnectionEvents;
                this.queuedConnectionEvents = new ArrayList<>();
            }
            return queuedConnectionEvents;
        }

        private synchronized List<Object> getAndResetQueuedProcessableReceivedObjects() {
            List<Object> queuedProcessableReceivedObjects = null;
            if (!this.queuedProcessableReceivedObjects.isEmpty()) {
                queuedProcessableReceivedObjects = this.queuedProcessableReceivedObjects;
                this.queuedProcessableReceivedObjects = new ArrayList<>();
            }
            return queuedProcessableReceivedObjects;
        }
    }
}
