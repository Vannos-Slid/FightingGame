package io.github.animation_tree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class EditScreen implements Screen {

    //Screen
    private OrthographicCamera camera;
    private Viewport viewport;

    //Screen parameters
    private final int WORLD_WIDTH = 1920;
    private final int WORLD_HEIGHT = 1080;

    //Graphics
    private SpriteBatch batch;
    private Stage stage;

    private Texture texture;
    private Sprite sprite;


    EditScreen(){
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();

        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        texture = new Texture(Gdx.files.internal("medic.png"));
        sprite = new Sprite(texture);

        sprite.setPosition(WORLD_WIDTH / 2f - sprite.getWidth() / 2, WORLD_HEIGHT / 2f - sprite.getHeight() / 2);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        texture.dispose();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
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

}
