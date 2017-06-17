package at.shootme.util.entity;

import at.shootme.entity.general.Entity;
import at.shootme.entity.level.Platform;

import java.util.UUID;

public class EntityIdGenerator {

    public String createId(Entity entity) {
        if(entity instanceof Platform){

        }
        return entity.getCategory().name() + "#" + UUID.randomUUID().toString();
    }
}
