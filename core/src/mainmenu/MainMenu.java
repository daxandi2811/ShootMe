package mainmenu;

import at.shootme.GameScreen;
import at.shootme.SM;
import at.shootme.levels.Level;
import at.shootme.levels.Level1;
import at.shootme.levels.Level2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import javafx.scene.control.RadioButton;

import javax.swing.*;

import static at.shootme.SM.game;
import static at.shootme.SM.level;
import static at.shootme.SM.world;

/**
 * Created by Steffi on 11.06.2017.
 */
public class MainMenu implements Screen {

    private Skin skin;
    private Stage stage;
    private ButtonGroup btGroup;

    public MainMenu() {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);// Make the stage consume events

        createBasicSkin();
        TextButton btLev1 = new TextButton("Level1", skin); // Use the initialized skin
        btLev1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("HIII");
                game.setScreen( new GameScreen(1) );
            };
        });
        btLev1.setPosition(Gdx.graphics.getWidth()/4 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2);
        stage.addActor(btLev1);


        TextButton btLev2 = new TextButton("Level2", skin);
        btLev2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("HIII");
                game.setScreen( new GameScreen(2) );
            };
        });
        btLev2.setPosition(Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2);
        stage.addActor(btLev2);


        TextButton btLev3 = new TextButton("Level3", skin);
        btLev3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("HIII");
                game.setScreen( new GameScreen(3) );
            };
        });
        btLev3.setPosition(Gdx.graphics.getWidth()*3/4 - Gdx.graphics.getWidth()/8 , Gdx.graphics.getHeight()/2);
        stage.addActor(btLev3);
    }

    @Override
    public void show() {

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void createBasicSkin(){
        //Create a font
        BitmapFont font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

    }
}
