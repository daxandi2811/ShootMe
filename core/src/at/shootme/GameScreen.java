package at.shootme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.utils.Box2DBuild;

/**
 * Created by Alexander Dietrich on 07.04.2017.
 */
public class GameScreen implements Screen, InputProcessor {

    private SpriteBatch batch;
    private Texture img;
    private Sprite sprite;
    private World world;
    private Body body;

    private static final float PIXELS_TO_METRES = 100f;

    @Override
    public void show() { //"wie" der Constructor
        SM.input.setInputProcessor(this);
        batch = new SpriteBatch();
        img = new Texture("assets/guenter_right.png");
        sprite = new Sprite(img);
        sprite.setPosition(SM.graphics.getWidth() / 2 - sprite.getWidth() / 2,
                SM.graphics.getHeight() / 2);
        world  = new World(new Vector2(0, -98f), true);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(sprite.getX() / PIXELS_TO_METRES, sprite.getY() / PIXELS_TO_METRES);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(sprite.getWidth() /2, sprite.getHeight() /2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public void render(float delta) { //hier nie Objekte erzeugen

        world.step(1f/60f, 6,2);
        sprite.setPosition(body.getPosition().x, body.getPosition().y);

        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(sprite, sprite.getX(), sprite.getY());
        batch.end();
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
        img.dispose();
        world.dispose();
        SM.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {



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
