package at.shootme.levels;

import at.shootme.ShootMeConstants;
import at.shootme.entity.general.Drawable;
import at.shootme.entity.general.Entity;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Alexander Dietrich on 05.05.2017.
 */
public class Level implements ShootMeConstants {

    private List<Entity> entities = new ArrayList<>();
    private List<Drawable> drawables = new ArrayList<>();
    protected final World world;

    public Level(World world) {
        this.world = world;
    }

    public void add(Entity entity) {
        entities.add(entity);
        if (entity instanceof Drawable) {
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

    public void render(SpriteBatch batch) {
        for (Drawable drawable : drawables) {
            drawable.draw(batch);
        }
    }

}
