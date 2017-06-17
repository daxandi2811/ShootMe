package at.shootme.levels;

import at.shootme.SM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Steffi on 07.05.2017.
 */
public class Level3 extends Level {

    public Level3(World world) {
        super(world);
        loadWorld();
    }


    private void loadWorld() {
        //Background
        Texture backgroundTexture = new Texture("assets/level3.png");
        int levelWidth = 1280;
        int levelHeight = 720;
        addCosmetic(LevelUtility.createLevelBackground(new Vector2(-levelWidth, 50), new Vector2(levelWidth * 2, levelHeight * 2), backgroundTexture));

        if (SM.isServer()) {
            //Border
            String floorType = "dirt";
            //Floor
            add(LevelUtility.createPlatform(new Vector2(-levelWidth, 0), new Vector2(levelWidth * 2, 50), floorType, world));

            //Ceiling
            add(LevelUtility.createPlatform(new Vector2(-levelWidth, backgroundTexture.getHeight() / 2 + 50), new Vector2(levelWidth * 2, 50), floorType, world));

            //Wall left
            add(LevelUtility.createPlatform(new Vector2(-levelWidth - 50, 0), new Vector2(50, levelHeight * 2 + 100), floorType, world));

            //Wall right
            add(LevelUtility.createPlatform(new Vector2(levelWidth, 0), new Vector2(50, levelHeight * 2 + 100), floorType, world));


            //Plattforms
            //right vertical
            add(LevelUtility.createPlatform(new Vector2(levelWidth / 2, levelHeight / 3), new Vector2(50, levelHeight), floorType, world));

            //left vertical
            add(LevelUtility.createPlatform(new Vector2(-levelWidth / 2, levelHeight / 3), new Vector2(50, levelHeight), floorType, world));

            //top middle horizontally
            add(LevelUtility.createPlatform(new Vector2(-levelWidth / 3, levelHeight + 400), new Vector2(900, 50), floorType, world));

            //bottom middle horizontally
            add(LevelUtility.createPlatform(new Vector2(-levelWidth / 3 + 200, levelHeight / 3), new Vector2(500, 50), floorType, world));

            //middle left horizontally
            add(LevelUtility.createPlatform(new Vector2(-levelWidth / 2 + 50, levelHeight - 50), new Vector2(300, 50), floorType, world));

            //middle right vertically
            add(LevelUtility.createPlatform(new Vector2(levelWidth / 2 - 300, levelHeight - 50), new Vector2(300, 50), floorType, world));

            //top left horizontally
            add(LevelUtility.createPlatform(new Vector2(-levelWidth, levelHeight + 200), new Vector2(300, 50), floorType, world));

            //top right horizontally
            add(LevelUtility.createPlatform(new Vector2(levelWidth - 300, levelHeight + 200), new Vector2(300, 50), floorType, world));

            //bottom left horizontally
            add(LevelUtility.createPlatform(new Vector2(-levelWidth / 2 - 300, levelHeight - 250), new Vector2(300, 50), floorType, world));

            //bottom right horizontally
            add(LevelUtility.createPlatform(new Vector2(levelWidth - 600, levelHeight - 250), new Vector2(300, 50), floorType, world));
        }
    }
}
