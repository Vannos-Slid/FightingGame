package io.github.animation_tree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TestScreen implements Screen {

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
    private Texture texture2;

    private Sprite sprite;
    private Sprite sprite2;

    //Logic
    private DragAndDrop dragAndDrop;

    TestScreen(){

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();

        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        texture2 = new Texture(Gdx.files.internal("scout_is_scared.png"));
        sprite2 = new Sprite(texture2);
        Image targetZone = new Image(sprite2);
        targetZone.setPosition(WORLD_WIDTH - targetZone.getWidth(), 0);

        texture = new Texture(Gdx.files.internal("medic.png"));
        sprite = new Sprite(texture);
        Image draggableItem = new Image(sprite);

//        draggableItem.setPosition(WORLD_WIDTH / 2f - sprite.getWidth() / 2, WORLD_HEIGHT / 2f -
//            sprite.getHeight() / 2);

        dragAndDrop = new DragAndDrop();

        dragAndDrop.addSource(new DragAndDrop.Source(draggableItem) {
            private float offsetX, offsetY;

            @Override
            public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
                payload.setObject(getActor());
                payload.setDragActor(getActor());

                offsetX = event.getStageX() - draggableItem.getX();
                offsetY = event.getStageY() - draggableItem.getY();

                dragAndDrop.setDragActorPosition(event.getStageX() - offsetX,
                    event.getStageY() - offsetY);
//                dragAndDrop.setDragActorPosition(getActor().getWidth() - x,
//                    -getActor().getHeight() + y);

//               dragAndDrop.setDragActorPosition(getActor().getWidth() / 2,
//                   -getActor().getHeight() / 2);

//                dragAndDrop.setDragActorPosition(-x, -y + getActor().getHeight());

                return payload;
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
//                dragAndDrop.setDragActorPosition(event.getStageX() - offsetX,
//                    event.getStageY() - offsetY);
//                draggableItem.moveBy(event.getStageX() - offsetX, event.getStageY() - offsetY);

            }
        });

        dragAndDrop.addTarget(new DragAndDrop.Target(targetZone) {
            @Override
            public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                targetZone.setColor(Color.GREEN);
                return true;
            }

            @Override
            public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
                Gdx.app.log("DragAndDrop", "Scout is caught");
            }

            @Override
            public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
                targetZone.setColor(Color.WHITE);
            }
        });

        Gdx.input.setInputProcessor(stage);

        stage.addActor(targetZone);
        stage.addActor(draggableItem);
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();

        texture2.dispose();
        texture.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        stage.act(delta);
        stage.draw();

        batch.begin();
        batch.end();
    }

    @Override
    public void hide() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void show() {

    }
}
