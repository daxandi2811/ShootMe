package screens;

import at.shootme.SM;
import at.shootme.networking.client.GameClient;
import at.shootme.networking.exceptions.NetworkingRuntimeException;
import at.shootme.state.data.GameState;
import at.shootme.state.data.GameStateType;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Steffi on 18.06.2017.
 */
public class ConnectingScreen implements Screen {


    private Skin buttonskin;
    private Stage stage;
    private BitmapFont mediumFont;
    private SpriteBatch batch;
    private Label lbError;

    //Constructor
    public ConnectingScreen() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);// Make the stage consume events
        initComponents();
    }

    private void initComponents() {
        createBasicSkin();

        //Playername-Label
        //Creates a new Label, which requires a LabelSytle
        //LabelStyle
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        BitmapFont font = new BitmapFont(); //BitmapFont is a pixel font
        font.getData().setScale(2); //scaling the font with the factor 5
        labelStyle.font = font;
        labelStyle.fontColor = Color.CORAL;

        //Label
        Label lbPlayername;
        lbPlayername = new Label("Playername: ", labelStyle);
        lbPlayername.setPosition(Gdx.graphics.getWidth() / 4, 500);
        stage.addActor(lbPlayername); //so the label appears on the Stage!!

        //Text Field
        TextField.TextFieldStyle textStyle = new TextField.TextFieldStyle();
        textStyle.font = font;
        textStyle.fontColor = Color.CORAL;

        TextField tfPlayername = new TextField(SM.playerName, textStyle);
        tfPlayername.setWidth(300);
        tfPlayername.setMessageText("*enter name here*");
        tfPlayername.setPosition(Gdx.graphics.getWidth() / 2, 500);
        stage.addActor(tfPlayername);

//Ip-add
        //Label
        Label lbIP;
        lbIP = new Label("Server-Host IP: ", labelStyle);
        lbIP.setPosition(Gdx.graphics.getWidth() / 4, 400);
        stage.addActor(lbIP); //so the label appears on the Stage!!

        //Text Field
        TextField tfIP = new TextField("", textStyle);
        tfIP.setWidth(300);
        tfIP.setMessageText("*enter ip here*");
        tfIP.setPosition(Gdx.graphics.getWidth() / 2, 400);
        stage.addActor(tfIP);

        lbError = new Label("", labelStyle);
        lbError.setWrap(true);
        lbError.setWidth(Gdx.graphics.getWidth() - (Gdx.graphics.getWidth() / 4) - 100);
        lbError.setPosition(Gdx.graphics.getWidth() / 4, 300);
        stage.addActor(lbError);

//Submit Button
        //Skin for Buttons
        createBasicSkin();

        TextButton btSubmit = new TextButton("Submit", buttonskin);
        btSubmit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String playerName = tfPlayername.getText();
                SM.playerName = playerName;
                String host = tfIP.getText();

                if (playerName.trim().isEmpty()) {
                    lbError.setText("Please enter a player name!");
                    return;
                }
                if (playerName.length() > 10) {
                    lbError.setText("Please only enter 10 or less characters for the name!");
                    return;
                }

                try {
                    GameClient gameClient = new GameClient();
                    SM.client = gameClient;
                    gameClient.connect(host);
                } catch (NetworkingRuntimeException ex) {
                    lbError.setText("Could not connect! (" + ex.getMessage() + ")");
                    return;
                }

                GameState gameState = new GameState();
                gameState.setStateType(GameStateType.LEVEL_SELECTION);
                SM.gameStateManager.apply(gameState);
            }

            ;
        });
        btSubmit.setPosition(Gdx.graphics.getWidth() / 3, 150);
        stage.addActor(btSubmit); //so the button appears on the Stage!!
    }


    @Override
    public void show() {

    }

    @Override
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
}
