package at.shootme.networking.general;

import at.shootme.entity.general.Entity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Nicole on 17.06.2017.
 */
public abstract class EventProcessor {

    /**
     * list of all recieved new entities this tick
     */
    protected List<Entity> receivedEntitiesThisTick = new LinkedList<>();

    /**
     * the recieved object for a connection
     * @param connection
     * @param object
     */
    public abstract void received(ServerClientConnection connection, Object object);

    /**
     * this client disconnected from the server, close connection
     * @param connection
     */
    public abstract void disconnected(ServerClientConnection connection);

    /**
     * this connection is idle
     * @param connection
     */
    public abstract void idle(ServerClientConnection connection);

    /**
     * gives a list of all new recieved entities this tick
     * @return
     */
    public List<Entity> getReceivedEntitiesThisTick() {
        return receivedEntitiesThisTick;
    }
}
