package at.shootme;

import at.shootme.beans.HorizontalMovementState;
import at.shootme.beans.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Alexander Dietrich on 07.04.2017.
 */
public class GameScreen implements Screen, InputProcessor {

    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;

    private Player player;

    private static final float PIXELS_TO_METRES = 100f;

    private float partStep;

    @Override
    public void show() { //"wie" der Constructor

        camera = new OrthographicCamera(1280, 720);

        debugRenderer = new Box2DDebugRenderer();

        SM.input.setInputProcessor(this);
        batch = new SpriteBatch();

        world = new World(new Vector2(0, -98), true);

        player = new Player();
        player.init(new Vector2(0, 50), world);


        BodyDef floorBodyDef = new BodyDef();
        floorBodyDef.type = BodyDef.BodyType.StaticBody;

        floorBodyDef.position.set(0, 0);

        Body floorBody = world.createBody(floorBodyDef);

        PolygonShape floorShape = new PolygonShape();

        floorShape.setAsBox(SM.graphics.getWidth() / 2 - 1, 5);


        FixtureDef floorFixDef = new FixtureDef();
        floorFixDef.shape = floorShape;
        floorFixDef.density = 1f;

        Fixture floorFixture = floorBody.createFixture(floorFixDef);


        floorShape.dispose();
    }


    /***
     *
     * Custom step method for movement etc
     *
     * */
    private void step(float timeStep) {
        world.step(timeStep, 6, 2);
        player.moveHorizontally();
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


        player.moveSprite();

        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(player.getSprite(), player.getSprite().getX(), player.getSprite().getY());
        batch.end();

        debugRenderer.render(world, camera.combined);
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
        SM.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Input.Keys.SPACE:

                player.jump();
                break;
            case Input.Keys.A:
                player.setHorizontalMovementState(HorizontalMovementState.LEFT);
                break;
            case Input.Keys.D:
                player.setHorizontalMovementState(HorizontalMovementState.RIGHT);
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.D:
                if (SM.input.isKeyPressed(Input.Keys.A)) {
                    player.setHorizontalMovementState(HorizontalMovementState.LEFT);
                } else if (SM.input.isKeyPressed(Input.Keys.D)) {
                    player.setHorizontalMovementState(HorizontalMovementState.RIGHT);
                } else
                    player.setHorizontalMovementState(HorizontalMovementState.STOPPING);
                break;

        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
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


}

