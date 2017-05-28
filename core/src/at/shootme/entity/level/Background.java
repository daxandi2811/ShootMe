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

    public Background(Vector2 pixePosition, Vector2 pixelSize, Texture texture) {
        sprite = new Sprite(texture);
        sprite.setSize(pixelSize.x, pixelSize.y);
        sprite.setOriginCenter();
        sprite.setPosition(pixePosition.x, pixePosition.y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}
