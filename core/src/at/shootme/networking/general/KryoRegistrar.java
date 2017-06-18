package at.shootme.networking.general;

import at.shootme.beans.HorizontalMovementState;
import at.shootme.beans.ViewDirection;
import at.shootme.entity.level.Platform.PlatformCreationMessage;
import at.shootme.entity.player.Player.PlayerCreationMessage;
import at.shootme.entity.shot.StandardShot.StandardShotCreationMessage;
import at.shootme.networking.data.entity.EntityBodyGeneralState;
import at.shootme.networking.data.entity.EntityCreationMessage;
import at.shootme.networking.data.entity.EntityStateChangeMessage;
import at.shootme.networking.data.entity.PlayerStateChangeMessage;
import at.shootme.networking.data.framework.MessageBatch;
import at.shootme.networking.data.framework.StepCommunicationFlush;
import at.shootme.state.data.GameState;
import at.shootme.state.data.GameStateType;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;

import java.util.*;

public class KryoRegistrar {

    public void registerClasses(Kryo kryo) {
        registerUtilityClasses(kryo);
        registerGameMessages(kryo);
    }


    private void registerGameMessages(Kryo kryo) {
        List<Class> gameMessageClasses = getGameMessages();
        gameMessageClasses.forEach((type) -> kryo.register(type));
    }

    private List<Class> getGameMessages() {
        List<Class> gameMessageClasses = new LinkedList<>();
        gameMessageClasses.add(EntityCreationMessage.class);
        gameMessageClasses.add(PlayerCreationMessage.class);
        gameMessageClasses.add(PlatformCreationMessage.class);
        gameMessageClasses.add(StandardShotCreationMessage.class);
        gameMessageClasses.add(EntityBodyGeneralState.class);
        gameMessageClasses.add(EntityStateChangeMessage.class);
        gameMessageClasses.add(MessageBatch.class);
        gameMessageClasses.add(StepCommunicationFlush.class);
        gameMessageClasses.add(GameState.class);
        gameMessageClasses.add(GameStateType.class);
        gameMessageClasses.add(PlayerStateChangeMessage.class);
        gameMessageClasses.add(ViewDirection.class);
        gameMessageClasses.add(HorizontalMovementState.class);
        return gameMessageClasses;
    }

    private void registerUtilityClasses(Kryo kryo) {
        kryo.register(ArrayList.class);
        kryo.register(HashSet.class);
        kryo.register(HashMap.class);
        kryo.register(LinkedList.class);
        kryo.register(Vector2.class);
    }
}
