package at.shootme.networking.general;

import at.shootme.SM;
import at.shootme.entity.general.Entity;
import at.shootme.entity.general.EntityTypeHandler;
import at.shootme.networking.data.entity.EntityCreationMessage;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Nicole on 17.06.2017.
 */
public class NetworkingUtils {


    public static List<EntityCreationMessage> createEntityCreationMessages(List<Entity> entities) {
        return entities.stream().map(entity -> {
            EntityTypeHandler handler = SM.entityTypeHandlerRegistry.getHandlerFor(entity);
            return handler.createEntityCreationMessage(entity);
        }).collect(Collectors.toList());
    }
}
