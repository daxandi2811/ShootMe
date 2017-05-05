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
    protected World world;

    public Level(World world) {
        this.world = world;
        this.sprites = new LinkedList<>();
    }

    public void render(SpriteBatch batch)
    {
        for(Sprite sprite : sprites)
        {
            sprite.draw(batch);
        }
    }

}
