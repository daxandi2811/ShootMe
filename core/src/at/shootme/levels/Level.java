package at.shootme.levels;

import at.shootme.SM;
import at.shootme.ShootMeConstants;
import at.shootme.entity.general.Drawable;
import at.shootme.entity.general.Entity;
import at.shootme.logic.StepListener;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Alexander Dietrich on 05.05.2017.
 */
public class Level implements ShootMeConstants, StepListener {

    private final List<Entity> entities = new ArrayList<>();
    private final List<Drawable> drawables = new ArrayList<>();
    private final List<Entity> removalQueue = new ArrayList<>();
    protected final World world;

    public Level(World world) {
        this.world = world;
        SM.gameScreen.registerStepListener(0, this);
    }

    public void add(Entity entity) {
        entities.add(entity);
        if (isDrawable(entity)) {
            drawables.add((Drawable) entity);
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
        if (isDrawable(entity)) {
            drawables.remove(entity);
        }
        Body body = entity.getBody();
        world.destroyBody(body);
    }

    @Override
    public void afterWorldStep(float timeStep) {
        removalQueue.forEach(this::remove);
        removalQueue.clear();
    }

    public void render(SpriteBatch batch) {
        for (Drawable drawable : drawables) {
            drawable.draw(batch);
        }
    }
}
