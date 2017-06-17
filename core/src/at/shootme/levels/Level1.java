package at.shootme.levels;

import at.shootme.SM;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Steffi on 07.05.2017.
 */
public class Level1 extends Level {

    public Level1(World world) {
        super(world);
        loadWorld();
    }

    //placing Level Items (Plattforms and Background)
    private void loadWorld() {
        //Pictures as Background
        Texture backgroundTexture = new Texture("assets/level1.png");
        int levelWidth = 1280;
        int levelHeight = 720;
        addCosmetic(LevelUtility.createLevelBackground(new Vector2(-levelWidth, 50), new Vector2(levelWidth * 2, levelHeight * 2), backgroundTexture));

        if (SM.isServer()) {
            //Border
            String floorType = "stone";
            //Floor
            add(LevelUtility.createPlatform(new Vector2(-levelWidth, 0), new Vector2(levelWidth * 2, 50), floorType, world));

            //Ceiling
            add(LevelUtility.createPlatform(new Vector2(-levelWidth, backgroundTexture.getHeight() / 2 + 50), new Vector2(levelWidth * 2, 50), floorType, world));

            //Wall left
            add(LevelUtility.createPlatform(new Vector2(-levelWidth - 50, 0), new Vector2(50, levelHeight * 2 + 100), floorType, world));

            //Wall right
            add(LevelUtility.createPlatform(new Vector2(levelWidth, 0), new Vector2(50, levelHeight * 2 + 100), floorType, world));


            //Plattforms
            //bottom right horizontally
            add(LevelUtility.createPlatform(new Vector2(levelWidth / 2, levelHeight / 3), new Vector2(300, 50), floorType, world));

            //top right horizontally
            add(LevelUtility.createPlatform(new Vector2(levelWidth / 2, levelHeight + 50), new Vector2(levelWidth / 2, 50), floorType, world));

            //bottom right vertical
            add(LevelUtility.createPlatform(new Vector2(levelWidth / 2, levelHeight / 3), new Vector2(50, levelHeight - 150), floorType, world));

            add(LevelUtility.createPlatform(new Vector2(levelWidth / 2 + 300, levelHeight / 3), new Vector2(50, levelHeight / 3), floorType, world));

            //top left horizontally
            add(LevelUtility.createPlatform(new Vector2(-levelWidth, levelHeight), new Vector2(300, 50), floorType, world));

            add(LevelUtility.createPlatform(new Vector2(-levelWidth + 300, levelHeight + 300), new Vector2(400, 50), floorType, world));

            //top left vertical
            add(LevelUtility.createPlatform(new Vector2(-levelWidth + 700, levelHeight + 300), new Vector2(50, levelHeight / 2 + 150), floorType, world));

            add(LevelUtility.createPlatform(new Vector2(-levelWidth + 250, levelHeight - 300), new Vector2(50, levelHeight / 3 + 100), floorType, world));

            //bottom middle waagrecht
            add(LevelUtility.createPlatform(new Vector2(-levelWidth / 2, levelHeight / 2), new Vector2(800, 50), floorType, world));

            //bottom middle vertical
            add(LevelUtility.createPlatform(new Vector2(-levelWidth / 2 + 400, levelHeight / 2 - 350), new Vector2(50, levelHeight / 2), floorType, world));

            //top middle vertical
            add(LevelUtility.createPlatform(new Vector2(levelWidth / 3, levelHeight + 410), new Vector2(50, levelHeight / 2), floorType, world));
        }
    }

}

