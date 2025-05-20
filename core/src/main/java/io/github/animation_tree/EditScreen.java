package io.github.animation_tree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class EditScreen implements Screen, InputProcessor {

    //Screen
    private OrthographicCamera camera;
    private Viewport viewport;

    //Screen parameters
    private final int WORLD_WIDTH = 1920;
    private final int WORLD_HEIGHT = 1080;

    //Graphics
    private SpriteBatch batch;
    private Stage stage;

//    InputMultiplexer multiplexer;

    private Texture texture;
    private Texture texture2;
    private Sprite sprite;
    private Sprite sprite2;



    EditScreen(){
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();

        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        texture2 = new Texture(Gdx.files.internal("scout_is_scared.png"));
        Image targetZone = new Image(texture2);
        sprite2 = new Sprite(texture2);

        texture = new Texture(Gdx.files.internal("medic.png"));
        sprite = new Sprite(texture);
        Image draggableItem = new Image(texture);

        draggableItem.setPosition(WORLD_WIDTH / 2f - sprite.getWidth() / 2, WORLD_HEIGHT / 2f -
            sprite.getHeight() / 2);

//        multiplexer = new InputMultiplexer();
//
//        multiplexer.addProcessor(stage);
//        multiplexer.addProcessor(this);

        Gdx.input.setInputProcessor(stage);

        stage.addActor(targetZone);
        stage.addActor(draggableItem);

        draggableItem.addListener(new DragListener(){
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                draggableItem.moveBy(x - draggableItem.getWidth() / 2,
                    y - draggableItem.getHeight() / 2);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                if (draggableItem.getX() > targetZone.getX() &&
                    draggableItem.getX() < targetZone.getX() + targetZone.getWidth() &&
                    draggableItem.getY() > targetZone.getY() &&
                    draggableItem.getY() < targetZone.getY() + targetZone.getHeight())
                {
                    Gdx.app.log("DragAndDrop", "Item dropped in target zone");
                } else {
                    draggableItem.setPosition(0, 0);
                    Gdx.app.log("DragAndDrop", "Sosav?");
                }
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        texture.dispose();
        texture2.dispose();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
//        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        stage.act(delta);
        stage.draw();
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
        System.out.println("Sosav?");
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
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
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
