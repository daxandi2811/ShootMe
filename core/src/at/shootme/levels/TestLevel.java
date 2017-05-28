package at.shootme.levels;

import at.shootme.SM;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alexander Dietrich on 05.05.2017.
 */
public class TestLevel extends Level {

    public TestLevel(World world) {
        super(world);
        loadWorld();
    }

    private void loadWorld() {
        Texture floorTexture = new Texture("assets/irregular_stone_floor_20130930_1665458395.jpg");

        add(LevelUtility.createPlatform(new Vector2(-SM.graphics.getWidth(), 0), new Vector2(SM.graphics.getWidth() * 2, 50), floorTexture,
                world));


    }
}
