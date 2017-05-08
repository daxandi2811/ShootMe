package at.shootme.levels;

import at.shootme.SM;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Steffi on 07.05.2017.
 */
public class Level3 extends Level {

    public Level3(World world) {
        super(world);
        loadWorld();
    }


    private void loadWorld()
    {
        //Hintergrund
        Texture backgroundTexture = new Texture("assets/level3.png");
        sprites.put(objectCount++, LevelUtility.createLevelSprite(backgroundTexture, new Vector2(1280*2, 720*2),
                new Vector2(-SM.graphics.getWidth(), 50)));

        //"Rahmen"
        Texture floorTexture = new Texture("assets/irregular_stone_floor_20130930_1665458395.jpg");
        //Boden
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(SM.graphics.getWidth() *2, 50),
                new Vector2(-SM.graphics.getWidth(), 0),world, BodyDef.BodyType.StaticBody, 1f));


        //Decke  WIRD NICHT GEZEICHNET
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(SM.graphics.getWidth() *2, 50),
                new Vector2(-SM.graphics.getWidth(), backgroundTexture.getHeight()/2+50), world, BodyDef.BodyType.StaticBody, 1f));


        //Wand links
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(50, SM.graphics.getHeight()*2+100),
                new Vector2(-SM.graphics.getWidth()-50, 0), world, BodyDef.BodyType.StaticBody, 1f));


        //Wand rechts
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(50, SM.graphics.getHeight()*2+100),
                new Vector2(SM.graphics.getWidth(), 0), world, BodyDef.BodyType.StaticBody, 1f));



    }
}
