package io.github.animation_tree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.text.Utilities;

public class EditScreen implements Screen, InputProcessor {

    //Screen
    private OrthographicCamera camera;
    private Viewport viewport;

    //Screen parameters
    private final int WORLD_WIDTH = 1920;
    private final int WORLD_HEIGHT = 1080;

    private final float AXIS_ORIGIN_X = WORLD_WIDTH / 2f;
    private final float AXIS_ORIGIN_Y = WORLD_HEIGHT / 2f;

    //Graphics
    private SpriteBatch batch;
    private Stage stage;

    private ShapeRenderer shapeRenderer;

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

        shapeRenderer = new ShapeRenderer();

        texture2 = new Texture(Gdx.files.internal("scout_is_scared.png"));
        sprite2 = new Sprite(texture2);
        Image targetZone = new Image(sprite2);
        targetZone.setName("targetZone");

        texture = new Texture(Gdx.files.internal("medic.png"));
        sprite = new Sprite(texture);
        Image draggableItem = new Image(sprite);
        draggableItem.setName("draggableItem");

//        Gdx.input.setInputProcessor(stage);

        stage.addActor(targetZone);
        stage.addActor(draggableItem);

        loadOrInitPositions();

        draggableItem.addListener(new DragListener(){
            private float offsetX, offsetY;

            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                offsetX = event.getStageX() - draggableItem.getX();
                offsetY = event.getStageY() - draggableItem.getY();
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                draggableItem.setPosition(event.getStageX() - offsetX, event.getStageY() - offsetY);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                System.out.println("The element has been dragged");
            }
        });

        //Prepare input screen
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void loadOrInitPositions(){
        FileHandle fileHandle = Gdx.files.local("Json/positions.json");
        if (!fileHandle.exists() || fileHandle.length() == 0){
            savePosition(fileHandle);
        } else {
            try{
               loadPosition(fileHandle);
            } catch (Exception e){
                e.printStackTrace();
                savePosition(fileHandle);
            }
        }
    }

    private void loadPosition(FileHandle fileHandle){
        if (fileHandle.exists()){
            JsonReader jsonReader = new JsonReader();
            JsonValue root = jsonReader.parse(fileHandle);
            if (root.isArray()){
                for (JsonValue posValue : root){
                    String name = posValue.getString("name");
                    float x = posValue.getFloat("x");
                    float y = posValue.getFloat("y");
                    Actor actor = stage.getRoot().findActor(name);
                    if (actor != null){
                        actor.setPosition(x,y);
                    }
                }
            } else {
                savePosition(fileHandle);
            }
        }
    }

    private void savePosition(FileHandle fileHandle){
        try {
            JsonWriter jsonWriter = new JsonWriter(fileHandle.writer(false));
            jsonWriter.setOutputType(JsonWriter.OutputType.json);
            jsonWriter.array();

            for (Actor actor : stage.getActors()){
                if (actor.getName() != null){
                    jsonWriter.object();
                    jsonWriter.name("name").value(actor.getName());
                    jsonWriter.name("x").value(actor.getX());
                    jsonWriter.name("y").value(actor.getY());
                    jsonWriter.pop();
                }
            }
            jsonWriter.pop();
            jsonWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        texture.dispose();
        texture2.dispose();
        shapeRenderer.dispose();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.x = 0;
        camera.position.y = 0;

        camera.update();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(-WORLD_WIDTH, 0, WORLD_WIDTH, 0);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(0, -WORLD_HEIGHT, 0, WORLD_HEIGHT);
        shapeRenderer.end();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.end();

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
        if (keycode == Input.Keys.S && (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT) ||
            Gdx.input.isKeyPressed(Input.Keys.CONTROL_RIGHT))){
            savePosition(Gdx.files.local("Json/positions.json"));
            return true;
        }
        return true;
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
