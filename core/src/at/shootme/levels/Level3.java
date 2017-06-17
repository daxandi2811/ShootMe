package at.shootme.levels;

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
        addCosmetic(LevelUtility.createLevelBackground(new Vector2(-Gdx.graphics.getWidth(), 50), new Vector2(1280 * 2, 720 * 2), backgroundTexture));


    //Border
        String floorType = "dirt";
        //Floor
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth(), 0), new Vector2(Gdx.graphics.getWidth() * 2, 50), floorType, world));

        //Ceiling
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth(), backgroundTexture.getHeight() / 2 + 50), new Vector2(Gdx.graphics.getWidth() * 2, 50), floorType, world));

        //Wall left
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth() - 50, 0), new Vector2(50, Gdx.graphics.getHeight() * 2 + 100), floorType, world));

        //Wall right
        add(LevelUtility.createPlatform(new Vector2(Gdx.graphics.getWidth(), 0), new Vector2(50, Gdx.graphics.getHeight() * 2 + 100), floorType, world));



    //Plattforms
        //right vertical
        add(LevelUtility.createPlatform(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3), new Vector2(50 , Gdx.graphics.getHeight()), floorType, world));

        //left vertical
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3), new Vector2(50 , Gdx.graphics.getHeight()), floorType, world));

        //top middle horizontally
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()+400), new Vector2(900, 50), floorType, world));

        //bottom middle horizontally
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth()/3+200, Gdx.graphics.getHeight()/3), new Vector2(500, 50), floorType, world));

        //middle left horizontally
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth()/2+50,Gdx.graphics.getHeight()-50  ), new Vector2(300, 50),floorType, world));

        //middle right vertically
        add(LevelUtility.createPlatform(new Vector2(Gdx.graphics.getWidth()/2-300,Gdx.graphics.getHeight()-50  ), new Vector2(300, 50),floorType, world));

        //top left horizontally
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth(),Gdx.graphics.getHeight()+200), new Vector2(300, 50),floorType, world));

        //top right horizontally
        add(LevelUtility.createPlatform(new Vector2(Gdx.graphics.getWidth()-300,Gdx.graphics.getHeight()+200), new Vector2(300, 50),floorType, world));

        //bottom left horizontally
        add(LevelUtility.createPlatform(new Vector2(-Gdx.graphics.getWidth()/2-300,Gdx.graphics.getHeight()-250), new Vector2(300, 50),floorType, world));

        //bottom right horizontally
        add(LevelUtility.createPlatform(new Vector2(Gdx.graphics.getWidth()-600,Gdx.graphics.getHeight()-250), new Vector2(300, 50),floorType, world));

    }
}
