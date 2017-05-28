package at.shootme.entity.level;

import at.shootme.entity.general.Drawable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Nicole on 28.05.2017.
 */
public class Background implements Drawable {

    private final Sprite sprite;

    public Background(Vector2 position, Vector2 size, Texture texture) {
        sprite = new Sprite(texture);
        sprite.setSize(size.x, size.y);
        sprite.setOriginCenter();
        sprite.setPosition(position.x, position.y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
