package at.shootme;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alexander Dietrich on 07.04.2017.
 */
public class TestScreen implements Screen, InputProcessor {

    private SpriteBatch batch;
    private Texture img;
    private Vector2 imagePos;
    private Vector2 imagePosMovement;

    @Override
    public void show() { //"wie" der Constructor
        SM.input.setInputProcessor(this);
        batch = new SpriteBatch();
        img = new Texture("assets/guenter_right.png");
        imagePos = new Vector2(0, 0);
        imagePosMovement = new Vector2(0,0);
    }

    @Override
    public void render(float delta) { //hier nie Objekte erzeugen

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        imagePos.add(imagePosMovement);
        batch.draw(img, imagePos.x, imagePos.y);
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
        SM.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Input.Keys.LEFT:
            case Input.Keys.A:
                imagePosMovement.set(-5, imagePosMovement.y);
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                imagePosMovement.set(+5, imagePosMovement.y);
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                imagePosMovement.set(imagePosMovement.x, -5);
                break;
            case Input.Keys.UP:
            case Input.Keys.W:
                imagePosMovement.set(imagePosMovement.x, +5);
                break;
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        switch (keycode) {
            case Input.Keys.LEFT:
            case Input.Keys.RIGHT:
            case Input.Keys.A:
            case Input.Keys.D:
                imagePosMovement.set(0, imagePosMovement.y);
                break;
            case Input.Keys.DOWN:
            case Input.Keys.UP:
            case Input.Keys.W:
            case Input.Keys.S:
                imagePosMovement.set(imagePosMovement.x, 0);
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
        imagePos.set(screenX, SM.graphics.getHeight() - screenY);
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
