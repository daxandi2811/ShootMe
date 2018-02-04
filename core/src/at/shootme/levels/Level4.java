package at.shootme.levels;

import at.shootme.SM;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Steffi on 07.05.2017.
 */
public class Level4 extends Level {

    public Level4(World world) {
        super(world);
        loadWorld();
    }


    private void loadWorld() {
        //Background
        Texture backgroundTexture = new Texture("assets/level4.png");
        int levelWidth = (int) getPixelSize().x / 2;
        int levelHeight = (int) getPixelSize().y / 2;
        addCosmetic(LevelUtility.createLevelBackground(new Vector2(-levelWidth, 50), new Vector2(levelWidth * 2, levelHeight * 2), backgroundTexture));

        if (SM.isServer()) {
            //Border
            String floorType = "stone";
            //Floor
            add(LevelUtility.createPlatform(new Vector2(-levelWidth, 0), new Vector2(levelWidth * 2, 50), floorType, world));

            //Ceiling
            add(LevelUtility.createPlatform(new Vector2(-levelWidth, backgroundTexture.getHeight() * 2 + 50), new Vector2(levelWidth * 2, 50), floorType, world));

            //Wall left
            add(LevelUtility.createPlatform(new Vector2(-levelWidth - 50, 0), new Vector2(50, levelHeight * 2 + 100), floorType, world));

            //Wall right
            add(LevelUtility.createPlatform(new Vector2(levelWidth, 0), new Vector2(50, levelHeight * 2 + 100), floorType, world));


            //Plattforms

            //house 5 right
            add(LevelUtility.createPlatform(new Vector2(levelWidth -570, levelHeight+350), new Vector2(455, 50), floorType, world));

            //house 3
            add(LevelUtility.createPlatform(new Vector2(-levelWidth+880, levelHeight+150), new Vector2(310, 50), floorType, world));

            //house 2
            add(LevelUtility.createPlatform(new Vector2(-levelWidth+530, levelHeight+530), new Vector2(360, 50), floorType, world));

            //house 4
            add(LevelUtility.createPlatform(new Vector2(levelWidth-1320, levelHeight-250), new Vector2(530, 50), floorType, world));

            //house 1
            add(LevelUtility.createPlatform(new Vector2(-levelWidth+50, levelHeight-250), new Vector2(390, 50), floorType, world));

            //house 5
            add(LevelUtility.createPlatform(new Vector2(levelWidth -570, levelHeight-250), new Vector2(455, 50), floorType, world));


        }
    }
}
