package at.shootme;

import at.shootme.beans.HorizontalMovementState;
import at.shootme.entity.player.Player;
import at.shootme.entity.shot.StandardShot;
import at.shootme.levels.Level;
import at.shootme.levels.Level1;
import at.shootme.levels.Level2;
import at.shootme.levels.Level3;
import at.shootme.logic.StepListener;
import at.shootme.physics.GameContactFilter;
import at.shootme.physics.GameContactListener;
import at.shootme.util.vectors.Vector2Util;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import mainmenu.MainMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Alexander Dietrich on 07.04.2017.
 */
public class GameScreen implements Screen, InputProcessor, ShootMeConstants {

    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private MainMenu menu;

    private Player player;

    private float partStep;

    private Level level;

    private TreeMap<Integer, List<StepListener>> stepListenerListsByPriority = new TreeMap<>();
    private Label.LabelStyle textStyle;
    private BitmapFont font;

    private GameScreen() {
        SM.gameScreen = this;
        world = new World(new Vector2(0, -98), true);
        SM.world = world;
    }

    public GameScreen(String levelKey) {
        this();
        Level level;
        switch (levelKey) {
            case "VOLCANO":
                level = new Level1(SM.world);
                break;
            case "COAST":
                level = new Level2(SM.world);
                break;
            case "FOREST":
                level = new Level3(SM.world);
                break;
            default:
                throw new IllegalArgumentException();
        }
        this.level = level;
        registerStepListener(0, level);
    }

    public GameScreen(int levelNr) {
        this();
        switch (levelNr) {
            case 1:
                level = new Level1(world);
                break;
            case 2:
                level = new Level2(world);
                break;
            case 3:
                level = new Level3(world);
                break;
        }

    }

    @Override
    public void show() {
        if (!ShootMeConstants.HIT_BOX_MODE) {
            camera = new OrthographicCamera(12.8f * 205f, 7.2f * 205f);
            camera.translate(0, 775);
        } else {
            camera = new OrthographicCamera(12.8f * 3f, 7.2f * 3f);
        }
        camera.update();

        debugRenderer = new Box2DDebugRenderer();

        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();

        if (SM.isClient()) {
            player = new Player();
            player.setTexturepath("assets/playersprite1.png");
            player.init(new Vector2(0, 100).scl(PIXELS_TO_METERS), world);
            level.add(player);
        }

        SM.level = level;

        GameContactListener listener = new GameContactListener();
        registerStepListener(1, listener);
        world.setContactListener(listener);
        world.setContactFilter(new GameContactFilter());

        textStyle = new Label.LabelStyle();
        font = new BitmapFont();
        font.getData().setScale(3);
    }


    /***
     *
     * Custom step method for movement etc
     *
     * */
    private void step(float timeStep) {
        if (SM.isServer()) {
            SM.server.preStep();
        } else {
            SM.client.preStep();
        }
        stepListenerListsByPriority.values().forEach(stepListeners -> stepListeners.forEach(stepListener -> {
            stepListener.beforeStep(timeStep);
        }));
        stepListenerListsByPriority.values().forEach(stepListeners -> stepListeners.forEach(stepListener -> {
            stepListener.beforeWorldStep(timeStep);
        }));

        if (SM.isServer()) {
            SM.server.prePhysics();
        } else {
            SM.client.prePhysics();
        }

        SM.level.getPlayers().forEach(Player::move);
        world.step(timeStep, 6, 2);

        stepListenerListsByPriority.values().forEach(stepListeners -> stepListeners.forEach(stepListener -> {
            stepListener.afterWorldStep(timeStep);
        }));

        if (SM.isServer()) {
            SM.server.postStep();
        } else {
            SM.client.postStep();
        }

        stepListenerListsByPriority.values().forEach(stepListeners -> stepListeners.forEach(stepListener -> {
            stepListener.cleanup();
        }));
    }

    /**
     * @param priorityIndex lower => earlier
     * @param stepListener
     */
    public void registerStepListener(int priorityIndex, StepListener stepListener) {
        List<StepListener> stepListeners = stepListenerListsByPriority.computeIfAbsent(priorityIndex, integer -> new ArrayList<>());
        stepListeners.add(stepListener);
    }

    @Override
    public void render(float delta) { //hier nie Objekte erzeugen

        float accumulator = delta;
        if (partStep >= 1f / 60f) {
            step(Math.min(1f / 60f, delta));
            partStep -= 1f / 60f;
        }
        do {
            step(Math.min(1f / 60f, delta));
            accumulator -= 1f / 60f;
        }
        while (accumulator > 1f / 60f);
        partStep += accumulator;


        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        level.render(batch);


        displayScore(500);
        displayTime(123);

        batch.end();

        debugRenderer.render(world, camera.combined);
    }

    //Displays the current score at the top right center
    public void displayScore(int score) {

        font.draw(batch, "Score: " + score, Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() * 2 - 50);
    }

    //Displays the time left at the top right corner
    public void displayTime(int seconds) {
        font.draw(batch, "Time: " + String.format("%d:%02d", seconds / 60, seconds % 60), Gdx.graphics.getWidth() - 300, Gdx.graphics.getHeight() * 2);
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
        batch.dispose();
        world.dispose();
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        if (SM.isClient()) {
            switch (keycode) {
                case Input.Keys.SPACE:
                    player.jumpIfPossible();
                    break;
                case Input.Keys.A:
                    player.setHorizontalMovementState(HorizontalMovementState.LEFT);
                    break;
                case Input.Keys.D:
                    player.setHorizontalMovementState(HorizontalMovementState.RIGHT);
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (SM.isClient()) {
            switch (keycode) {
                case Input.Keys.A:
                case Input.Keys.D:
                    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                        player.setHorizontalMovementState(HorizontalMovementState.LEFT);
                    } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                        player.setHorizontalMovementState(HorizontalMovementState.RIGHT);
                    } else
                        player.setHorizontalMovementState(HorizontalMovementState.STOPPING);
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (SM.isClient()) {
            Vector2 worldClickPoint = Vector2Util.convertVector3To2(camera.unproject(new Vector3(screenX, screenY, 0)));
            StandardShot shot = player.shootAt(worldClickPoint.scl(PIXELS_TO_METERS));
            if (shot != null) {
                level.add(shot);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public Player getPlayer() {
        return player;
    }
}

