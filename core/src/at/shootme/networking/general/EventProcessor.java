package at.shootme.networking.general;

import at.shootme.entity.general.Entity;

import java.util.LinkedList;
import java.util.List;

public abstract class EventProcessor {

    protected List<Entity> receivedEntitiesThisTick = new LinkedList<>();

    public abstract void received(ServerClientConnection connection, Object object);

    public abstract void disconnected(ServerClientConnection connection);

    public abstract void idle(ServerClientConnection connection);

    public List<Entity> getReceivedEntitiesThisTick() {
        return receivedEntitiesThisTick;
    }
}
