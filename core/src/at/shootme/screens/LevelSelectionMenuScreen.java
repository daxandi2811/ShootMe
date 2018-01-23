package at.shootme.screens;

import at.shootme.SM;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Steffi on 01.06.2017.
 */
public class LevelSelectionMenuScreen implements Screen {

    private Skin buttonskin;
    private Stage stage;
    private BitmapFont mediumFont;
    private SpriteBatch batch;

    //Constructor
    public LevelSelectionMenuScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);// Make the stage consume events
        initComponents();
    }


    //Generates the ShootMe-Label and the 3 Buttons + their skins
    private void initComponents() {
//Shoot-Me Titel-Label
        //Creates a new Label, which requires a LabelSytle
        //LabelStyle
        Label.LabelStyle textStyle = new Label.LabelStyle();
        BitmapFont font = new BitmapFont(); //BitmapFont is a pixel font
        font.getData().setScale(5); //scaling the font with the factor 5
        textStyle.font = font;
        textStyle.fontColor = Color.CORAL;

        //Label
        Label lbTitle;
        lbTitle = new Label("ShootMe", textStyle);
        lbTitle.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, 500);
        stage.addActor(lbTitle); //so the label appears on the Stage!!


//Buttons, to select the level
        //Skin for Buttons
        createBasicSkin();

        TextButton btLev1 = new TextButton("Level 1 - Volcano", buttonskin);
        btLev1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //sets GameScreen as the new Screen, hands over 1, so Level1 is created there
                SM.gameStateManager.requestStartGame("VOLCANO");
            }

            ;
        });
        btLev1.setPosition(Gdx.graphics.getWidth() / 4 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
        stage.addActor(btLev1); //so the button appears on the Stage!!


        TextButton btLev2 = new TextButton("Level 2 - Coast", buttonskin);
        btLev2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //sets GameScreen as the new Screen, hands over 2, so Level2 is created there
                SM.gameStateManager.requestStartGame("COAST");
            }

            ;
        });
        btLev2.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
        stage.addActor(btLev2); //so the button appears on the Stage!!


        TextButton btLev3 = new TextButton("Level 3 - Forest", buttonskin);
        btLev3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //sets GameScreen as the new Screen, hands over 3, so Level3 is created there
                SM.gameStateManager.requestStartGame("FOREST");
            }

            ;
        });
        btLev3.setPosition(Gdx.graphics.getWidth() * 3 / 4 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
        stage.addActor(btLev3); //so the button appears on the Stage!!


        mediumFont = new BitmapFont();
        mediumFont.getData().setScale(2);

        batch = new SpriteBatch();
    }


    //Creates a ButtonStyle for the buttons
    private void createBasicSkin() {
        //Create a font
        BitmapFont font = new BitmapFont();
        buttonskin = new Skin();
        buttonskin.add("default", font);

        //Create a texture
        Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth() / 4, (int) Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        buttonskin.add("background", new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonskin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = buttonskin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = buttonskin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = buttonskin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = buttonskin.getFont("default");
        buttonskin.add("default", textButtonStyle);
    }


    //for drawing the MainMenu
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (SM.isServer()) {
            SM.server.processReceivedWithoutGameEntities();
        } else {
            SM.client.processReceivedWithoutGameEntities();
        }

        if (SM.isClient()) {
        stage.act();
        stage.draw();
        } else {
            mediumFont.setColor(Color.BLACK);
            batch.begin();
            mediumFont.draw(batch, "Server is running. " + SM.server.getConnections().size() + " players connected.", 50, Gdx.graphics.getHeight()-50);
            batch.end();
        }
    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
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


}
