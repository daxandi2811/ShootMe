package at.shootme.levels;

import at.shootme.SM;
import at.shootme.ShootMeConstants;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import javax.swing.text.StyledEditorKit;
import java.util.LinkedList;

/**
 * Created by Alexander Dietrich on 05.05.2017.
 */
public class Level implements ShootMeConstants {

    protected LinkedList<Sprite> sprites;
    private World world;

    public Level(World world) {
        this.world = world;
        this.sprites = new LinkedList<>();
    }

    /**
     *
     * @param texture
     * @param size
     * @param position
     * @param bodyType
     * @param density
     * @return
     *
     * This method creates the physics objects for the world
     */
    protected Sprite createLevelObject(Texture texture, Vector2 size, Vector2 position, BodyDef.BodyType bodyType, float density)
    {
        Sprite sprite = new Sprite(texture);
        sprite.setSize(size.x, size.y);
        sprite.setOriginCenter();
        sprite.setPosition(position.x, position.y);

        BodyDef floorBodyDef = new BodyDef();
        floorBodyDef.type = bodyType;

        floorBodyDef.position.set((sprite.getX() + sprite.getWidth() / 2) * PIXELS_TO_METERS,
                (sprite.getY()) + sprite.getHeight() / 2 * PIXELS_TO_METERS);

        Body floorBody = world.createBody(floorBodyDef);

        PolygonShape floorShape = new PolygonShape();

        floorShape.setAsBox(sprite.getWidth() / 2 * PIXELS_TO_METERS, sprite.getHeight() / 2 * PIXELS_TO_METERS);


        FixtureDef floorFixDef = new FixtureDef();
        floorFixDef.shape = floorShape;
        floorFixDef.density = density;

        Fixture floorFixture = floorBody.createFixture(floorFixDef);
        floorShape.dispose();


        return sprite;
    }

    public void render(SpriteBatch batch)
    {
        for(Sprite sprite : sprites)
        {
            sprite.draw(batch);
        }
    }

}
