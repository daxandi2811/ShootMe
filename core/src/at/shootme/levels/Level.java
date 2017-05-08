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
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Alexander Dietrich on 05.05.2017.
 */
public class Level implements ShootMeConstants {

    protected TreeMap<Integer, Sprite> sprites;
    protected World world;
    protected int objectCount = 0;

    public Level(World world) {
        this.world = world;
        this.sprites = new TreeMap<>();
    }

    public void render(SpriteBatch batch)
    {
        for(Sprite sprite : sprites.values())
        {
            sprite.draw(batch);
        }
    }

}
