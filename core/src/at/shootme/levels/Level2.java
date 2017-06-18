package at.shootme.levels;

import at.shootme.SM;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Steffi on 07.05.2017.
 */
public class Level2 extends Level {

    public Level2(World world) {
        super(world);
        loadWorld();
    }

    //adding Level Items (Background, Plattforms)
    private void loadWorld() {

        //Background
        Texture backgroundTexture = new Texture("assets/level2.png");
        int levelWidth = 1280;
        int levelHeight = 720;
        addCosmetic(LevelUtility.createLevelBackground(new Vector2(-levelWidth, 50), new Vector2(levelWidth * 2, levelHeight * 2), backgroundTexture));

        if (SM.isServer()) {
            //Border
            String floorType = "sand";
            //Floor
            add(LevelUtility.createPlatform(new Vector2(-levelWidth, 0), new Vector2(levelWidth * 2, 50), floorType, world));

            //Ceiling
            add(LevelUtility.createPlatform(new Vector2(-levelWidth, backgroundTexture.getHeight() / 2 + 50), new Vector2(levelWidth * 2, 50), floorType, world));


            //Wand links
            add(LevelUtility.createPlatform(new Vector2(-levelWidth - 50, 0), new Vector2(50, levelHeight * 2 + 100), floorType, world));


            //Wand rechts
            add(LevelUtility.createPlatform(new Vector2(levelWidth, 0), new Vector2(50, levelHeight * 2 + 100), floorType, world));


            //Plattforms
            //bottom right horizontally
            add(LevelUtility.createPlatform(new Vector2(levelWidth / 2, levelHeight / 3), new Vector2(450, 50), floorType, world));

            //middle middle horizontally
            add(LevelUtility.createPlatform(new Vector2(-levelWidth + 400, levelHeight - 150), new Vector2(800, 50), floorType, world));

            //top right horizontally
            add(LevelUtility.createPlatform(new Vector2(levelWidth / 2, levelHeight + 50), new Vector2(levelWidth / 2, 50), floorType, world));

            //bottom left horizontally
            add(LevelUtility.createPlatform(new Vector2(-levelWidth, levelHeight / 3), new Vector2(300, 50), floorType, world));

            //top left horizontally
            add(LevelUtility.createPlatform(new Vector2(-levelWidth, levelHeight + 200), new Vector2(300, 50), floorType, world));

            //top middle horizontally
            add(LevelUtility.createPlatform(new Vector2(-levelWidth / 2, levelHeight + 400), new Vector2(600, 50), floorType, world));
        }

    }
}
