package at.shootme.networking.general;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public abstract class EventProcessor extends Listener {

    @Override
    public abstract void received(Connection connection, Object object);

    @Override
    public abstract void disconnected(Connection connection);

    @Override
    public abstract void idle(Connection connection);

}
