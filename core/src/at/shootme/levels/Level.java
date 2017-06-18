package at.shootme.levels;

import at.shootme.SM;
import at.shootme.ShootMeConstants;
import at.shootme.entity.general.Drawable;
import at.shootme.entity.general.Entity;
import at.shootme.entity.player.Player;
import at.shootme.logic.StepListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander Dietrich on 05.05.2017.
 */
public class Level implements ShootMeConstants, StepListener {

    protected final World world;
    private final List<Entity> entities = new ArrayList<>();
    private final Map<String, Entity> entitiesById = new HashMap<>();
    private final List<Drawable> drawables = new ArrayList<>();
    private final List<Entity> removalQueue = new ArrayList<>();
    private final List<Entity> addedEntitiesThisTick = new ArrayList<>();
    private final List<Player> players = new ArrayList<>();

    public Level(World world) {
        this.world = world;
    }

    public void add(Entity entity) {
        initializeIdIfNotSet(entity);
        entities.add(entity);
        entitiesById.put(entity.getId(), entity);
        if (isDrawable(entity)) {
            drawables.add((Drawable) entity);
        }
        addedEntitiesThisTick.add(entity);
        if (entity instanceof Player) {
            players.add((Player) entity);
        }
    }

    private void initializeIdIfNotSet(Entity entity) {
        if (entity.getId() == null) {
            entity.setId(SM.entityIdGenerator.createId(entity));
        }
    }

    public void addCosmetic(Drawable drawable) {
        if (drawable instanceof Entity) {
            throw new IllegalArgumentException("cosmetic is not allowed to be an " + Entity.class.getSimpleName());
        } else {
            drawables.add(drawable);
        }
    }

    private boolean isDrawable(Entity entity) {
        return entity instanceof Drawable;
    }

    /**
     * Queues the entity to be removed after the tick. If called twice with the same entity (during the same tick), it is only removed once.
     * The entities will be removed in the order they have been called here.
     * (During the world step the game state is not allowed to be changed in certain ways, e.g. destroying a body)
     *
     * @param entity
     */
    public void queueForRemoval(Entity entity) {
        if (!removalQueue.contains(entity)) {
            removalQueue.add(entity);
        }
    }

    private void remove(Entity entity) {
        entities.remove(entity);
        entitiesById.remove(entity.getId());
        if (isDrawable(entity)) {
            drawables.remove(entity);
        }
        if (entity instanceof Player) {
            players.remove(entity);
        }
        Body body = entity.getBody();
        world.destroyBody(body);
    }

    public Entity getEntityById(String id) {
        return entitiesById.get(id);
    }

    @Override
    public void cleanup() {
        addedEntitiesThisTick.clear();
        removalQueue.clear();
    }

    @Override
    public void afterWorldStep(float timeStep) {
        removalQueue.forEach(this::remove);
    }

    public void render(SpriteBatch batch) {
        for (Drawable drawable : drawables) {
            drawable.draw(batch);
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Entity> getAddedEntitiesThisTick() {
        return addedEntitiesThisTick;
    }

    public List<Entity> getRemovedEntitiesThisTick() {
        return removalQueue;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
