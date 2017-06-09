package at.shootme.levels;

import at.shootme.SM;
import com.badlogic.gdx.Gdx;
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
        addCosmetic(LevelUtility.createLevelBackground(new Vector2(-Gdx.graphics.getWidth(), 50), new Vector2(1280 * 2, 720 * 2), backgroundTexture));

        //"Rahmen"
        Texture floorTexture = new Texture("assets/irregular_stone_floor_20130930_1665458395.jpg");
        //Boden
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth(), 0), new Vector2(Gdx.graphics.getWidth() * 2, 50), floorTexture, world));

        //Decke
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth(), backgroundTexture.getHeight() / 2 + 50), new Vector2(Gdx.graphics.getWidth() * 2, 50), floorTexture, world));

        //Wand links
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth() - 50, 0), new Vector2(50, Gdx.graphics.getHeight() * 2 + 100), floorTexture, world));

        //Wand rechts
        add(LevelUtility.createPlatform(new Vector2(Gdx.graphics.getWidth(), 0), new Vector2(50, Gdx.graphics.getHeight() * 2 + 100), floorTexture, world));



        //Plattformen
        //unten rechts waagrecht
        add(LevelUtility.createPlatform(new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/3), new Vector2(300, 50),floorTexture, world));

        //oben rechts waagrecht
        add(LevelUtility.createPlatform(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()+50), new Vector2(Gdx.graphics.getWidth()/2, 50),floorTexture, world));

        //unten rechts senkrecht
        add(LevelUtility.createPlatform(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3), new Vector2(50 , Gdx.graphics.getHeight()-150), floorTexture, world));

        add(LevelUtility.createPlatform(new Vector2(Gdx.graphics.getWidth()/2+300, Gdx.graphics.getHeight()/3), new Vector2(50 , Gdx.graphics.getHeight()/3), floorTexture, world));

        //oben links waagrecht
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), new Vector2(300, 50),floorTexture, world));

        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth()+300, Gdx.graphics.getHeight()+300), new Vector2(400, 50),floorTexture, world));

        //oben links senkrecht
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth()+700, Gdx.graphics.getHeight()+300), new Vector2(50, Gdx.graphics.getHeight()/2+150), floorTexture, world));

        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth()+250, Gdx.graphics.getHeight()-300), new Vector2(50, Gdx.graphics.getHeight()/3+100), floorTexture, world));

        //unten mitte waagrecht
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2), new Vector2(800, 50), floorTexture, world));

        //unten mitte senkrecht
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth()/2+400, Gdx.graphics.getHeight()/2-350), new Vector2(50, Gdx.graphics.getHeight()/2), floorTexture, world));

        //oben mitte senkrecht
        add(LevelUtility.createPlatform(new Vector2(Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()+400), new Vector2(50, Gdx.graphics.getHeight()/2), floorTexture, world));

    }

}
