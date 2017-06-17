package at.shootme.util.entity;

import at.shootme.entity.general.Entity;

import java.util.UUID;

public class EntityIdGenerator {

    public String createId(Entity entity) {
        return entity.getCategory().name() + "#" + UUID.randomUUID().toString();
    }
}
