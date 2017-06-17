package at.shootme.entity.general;


import at.shootme.entity.level.PlatformHandler;
import at.shootme.entity.player.PlayerHandler;
import at.shootme.entity.shot.StandardShotHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EntityTypeHandlerRegistry {

    private HashMap<String, EntityTypeHandler> handlerByEntityName = new HashMap<>();

    public EntityTypeHandlerRegistry() {
        List<EntityTypeHandler> entityTypeHandlers = new LinkedList<>();
        entityTypeHandlers.add(new PlatformHandler());
        entityTypeHandlers.add(new PlayerHandler());
        entityTypeHandlers.add(new StandardShotHandler());
        entityTypeHandlers.forEach((entityTypeHandler) -> register(entityTypeHandler));
    }

    private void register(EntityTypeHandler entityTypeHandler) {
        handlerByEntityName.put(entityTypeHandler.getEntityName(), entityTypeHandler);
    }

    public EntityTypeHandler getHandlerFor(String entityClassName) {
        return handlerByEntityName.get(entityClassName);
    }

    public EntityTypeHandler getHandlerFor(Entity entity) {
        return getHandlerFor(entity.getClass().getSimpleName());
    }
}
