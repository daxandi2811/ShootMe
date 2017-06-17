package at.shootme.networking.server;

import at.shootme.networking.general.EventProcessor;
import com.esotericsoftware.kryonet.Connection;

public class ServerEventProcessor extends EventProcessor {

    @Override
    public void received(Connection connection, Object object) {

    }

    @Override
    public void disconnected(Connection connection) {

    }

    @Override
    public void idle(Connection connection) {

    }
}
