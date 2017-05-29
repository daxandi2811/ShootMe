package at.shootme.levels;

import at.shootme.SM;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Steffi on 07.05.2017.
 */
public class Level1 extends Level {

    public Level1(World world) {
        super(world);
        loadWorld();
    }


    private void loadWorld()
    {
        //Hintergrund
        Texture backgroundTexture = new Texture("assets/level1.png");
        sprites.put(objectCount++, LevelUtility.createLevelSprite(backgroundTexture, new Vector2(1280*2, 720*2),
                new Vector2(-SM.graphics.getWidth(), 50)));

        //"Rahmen"
        Texture floorTexture = new Texture("assets/irregular_stone_floor_20130930_1665458395.jpg");
        //Boden
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(SM.graphics.getWidth() *2, 50),
                new Vector2(-SM.graphics.getWidth(), 0),world, BodyDef.BodyType.StaticBody, 1f));

        //Decke
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(SM.graphics.getWidth() *2, 50),
                new Vector2(-SM.graphics.getWidth(), backgroundTexture.getHeight()/2+50), world, BodyDef.BodyType.StaticBody, 1f));

        //Wand links
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(50, SM.graphics.getHeight()*2+100),
                new Vector2(-SM.graphics.getWidth()-50, 0), world, BodyDef.BodyType.StaticBody, 1f));

        //Wand rechts
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(50, SM.graphics.getHeight()*2+100),
                new Vector2(SM.graphics.getWidth(), 0), world, BodyDef.BodyType.StaticBody, 1f));




        //Plattformen
        // unten rechts waagrecht
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(300, 50),
                new Vector2(SM.graphics.getWidth()/2, SM.graphics.getHeight()/3), world, BodyDef.BodyType.StaticBody, 1f));

        //oben rechts waagrecht
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(SM.graphics.getWidth()/2, 50),
                new Vector2(SM.graphics.getWidth()/2, SM.graphics.getHeight()+200), world, BodyDef.BodyType.StaticBody, 1f));

        //unten rechts senkrecht
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(50 , SM.graphics.getHeight()),
                new Vector2(SM.graphics.getWidth()/2, SM.graphics.getHeight()/3), world, BodyDef.BodyType.StaticBody, 1f));

        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(50 , SM.graphics.getHeight()/3),
                new Vector2(SM.graphics.getWidth()/2+300, SM.graphics.getHeight()/3), world, BodyDef.BodyType.StaticBody, 1f));

        //oben links waagrecht
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(300, 50),
                new Vector2(-SM.graphics.getWidth(), SM.graphics.getHeight()), world, BodyDef.BodyType.StaticBody, 1f));

        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(400, 50),
                new Vector2(-SM.graphics.getWidth()+300, SM.graphics.getHeight()+300), world, BodyDef.BodyType.StaticBody, 1f));

        //oben links senkrecht
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(50, SM.graphics.getHeight()/2+150),
                new Vector2(-SM.graphics.getWidth()+300, SM.graphics.getHeight()+300), world, BodyDef.BodyType.StaticBody, 1f));

      //  sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(50, -SM.graphics.getHeight()/3),
        //        new Vector2(-SM.graphics.getWidth()+250, SM.graphics.getHeight()), world, BodyDef.BodyType.StaticBody, 1f));

        //unten mitte waagrecht
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(800, 50),
                new Vector2(-SM.graphics.getWidth()/2, SM.graphics.getHeight()/2), world, BodyDef.BodyType.StaticBody, 1f));

        //unten mitte senkrecht
      //  sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(50, -SM.graphics.getHeight()/2),
        //        new Vector2(-SM.graphics.getWidth()/2, SM.graphics.getHeight()/2), world, BodyDef.BodyType.StaticBody, 1f));

        //oben mitte senkrecht
        sprites.put(objectCount++, LevelUtility.createLevelObject(floorTexture, new Vector2(50, SM.graphics.getHeight()/2),
                new Vector2(SM.graphics.getWidth()/3, SM.graphics.getHeight()+400), world, BodyDef.BodyType.StaticBody, 1f));
    }

}
