package at.shootme.screens;

import at.shootme.SM;
import at.shootme.state.data.GameMode;
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
 * Created by Nicole on 23.01.2018.
 */
public class GameModeSelectionMenuScreen implements Screen {

    private Skin buttonskin;
    private Stage stage;
    private BitmapFont mediumFont;
    private SpriteBatch batch;

    public GameModeSelectionMenuScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        initComponents();
    }


    private void initComponents() {
        Label.LabelStyle textStyle = new Label.LabelStyle();
        BitmapFont font = new BitmapFont();
        font.getData().setScale(5);
        textStyle.font = font;
        textStyle.fontColor = Color.CORAL;

        Label lbTitle;
        lbTitle = new Label("ShootMe", textStyle);
        lbTitle.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, 500);
        stage.addActor(lbTitle);


        createBasicSkin();

        TextButton btLev1 = new TextButton("Last Man Standing", buttonskin);
        btLev1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SM.gameStateManager.requestGameMode(GameMode.LAST_MAN_STANDING);
            }
        });
        btLev1.setPosition(Gdx.graphics.getWidth() / 4 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
        stage.addActor(btLev1); //so the button appears on the Stage!!


        TextButton btLev2 = new TextButton("Time", buttonskin);
        btLev2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SM.gameStateManager.requestGameMode(GameMode.TIME);
            }
        });
        btLev2.setPosition(Gdx.graphics.getWidth() / 2 - Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2);
        stage.addActor(btLev2);


        mediumFont = new BitmapFont();
        mediumFont.getData().setScale(2);

        batch = new SpriteBatch();
    }


    private void createBasicSkin() {
        BitmapFont font = new BitmapFont();
        buttonskin = new Skin();
        buttonskin.add("default", font);

        Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth() / 4, (int) Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        buttonskin.add("background", new Texture(pixmap));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = buttonskin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = buttonskin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = buttonskin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = buttonskin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = buttonskin.getFont("default");
        buttonskin.add("default", textButtonStyle);
    }


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
