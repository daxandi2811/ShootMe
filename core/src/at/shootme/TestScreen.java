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

    @Override
    public void show() { //"wie" der Constructor
        SM.input.setInputProcessor(this);
        batch = new SpriteBatch();
        img = new Texture("assets/badlogic.jpg");
        imagePos = new Vector2(0, 0);
    }

    @Override
    public void render(float delta) { //hier nie Objekte erzeugen

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
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
            case Input.Keys.A:
                imagePos.set(imagePos.x - 5, imagePos.y);
                break;
            case Input.Keys.D:
                imagePos.set(imagePos.x + 5, imagePos.y);
                break;
            case Input.Keys.S:
                imagePos.set(imagePos.x, imagePos.y - 5);
                break;
            case Input.Keys.W:
                imagePos.set(imagePos.x, imagePos.y + 5);
                break;

        }
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
