package io.github.animation_tree;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javax.lang.model.util.SimpleAnnotationValueVisitor6;

public class EditScreen implements Screen, InputProcessor {

    //Screen
    private OrthographicCamera camera;
    private Viewport viewport;

    //Screen parameters
    private final int WORLD_WIDTH = 1920;
    private final int WORLD_HEIGHT = 1080;

    private boolean testFlag = false;

    private float zoomFactor;

    private float touchedDownX;
    private float touchedDownY;

    private Vector3 originalCameraPos;

    private boolean isMouseTouchedDown;

    //Graphics
    private SpriteBatch batch;
    private Stage stage;

    private ShapeRenderer shapeRenderer;

    private Sprite textureSprite;
    private Sprite hitColliderSprite;
    private Sprite bodyColliderSprite;

    private Image draggableSprite;
    private Image draggableHitCollider;
    private Image draggableBodyCollider;

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();

        textureSprite.getTexture().dispose();
        hitColliderSprite.getTexture().dispose();
        bodyColliderSprite.getTexture().dispose();

        shapeRenderer.dispose();

    }

    EditScreen(){
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply();

        zoomFactor = 1;

        touchedDownX = 0;
        touchedDownY = 0;
        isMouseTouchedDown = false;

        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        shapeRenderer = new ShapeRenderer();

        Texture bodyColliderTexture = MySimplerMethods.createDefaultColliderTexture();
        bodyColliderSprite = new Sprite(bodyColliderTexture);
        draggableBodyCollider = MySimplerMethods.createImageObject("draggableBodyCollider",
            bodyColliderSprite, 0.5f);
        draggableBodyCollider.setSize(40f, 130f);

        Texture hitColliderTexture = MySimplerMethods.createDefaultColliderTexture();
        hitColliderSprite = new Sprite(hitColliderTexture);
        draggableHitCollider = MySimplerMethods.createImageObject("draggableHitCollider",
            hitColliderSprite, 0.5f);
        draggableHitCollider.setSize(58f, 60f);

        Texture spriteTexture = new Texture(Gdx.files.internal("light-feet-6.png"));
        textureSprite = new Sprite(spriteTexture);
        draggableSprite = MySimplerMethods.createImageObject("draggableSprite",
            textureSprite);


//        Gdx.input.setInputProcessor(stage);

        stage.addActor(draggableSprite);
        stage.addActor(draggableBodyCollider);
        stage.addActor(draggableHitCollider);


        loadOrInitPositions();

        // make images able to be dragged

        draggableSprite.addListener(MySimplerMethods.createDefaultDragListener(draggableSprite));

        draggableHitCollider.addListener(MySimplerMethods.createDefaultDragListener(draggableHitCollider));

        draggableBodyCollider.addListener(MySimplerMethods.createDefaultDragListener(draggableBodyCollider));

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

    private void loadPosition( FileHandle fileHandle){
        if (fileHandle.exists()){
            try{
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
                    System.out.println("The elements positions have been loaded");
                } else {
                    savePosition(fileHandle);
                }
            } catch (Exception e){
                e.printStackTrace();
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
            System.out.println("New positions have been saved.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (!testFlag){
            camera.position.x = 0;
            camera.position.y = 0;
            testFlag = true;
        }


        camera.update();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(-WORLD_WIDTH-WORLD_WIDTH, 0, WORLD_WIDTH + WORLD_WIDTH, 0);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.line(0, -WORLD_HEIGHT-WORLD_HEIGHT, 0, WORLD_HEIGHT+WORLD_HEIGHT);
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
        System.out.println("resize: width: " + width + ", height: " + height);
        System.out.println("windowWidth: " + WORLD_WIDTH + ", windowHeight: " + WORLD_HEIGHT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
    }

    private void zoomInCamera(){
        zoomFactor -= 0.1f;
        zoomFactor = Math.max(zoomFactor, 0.3f);
        camera.viewportWidth = WORLD_WIDTH * zoomFactor;
        camera.viewportHeight = WORLD_HEIGHT * zoomFactor;
        camera.update();
    }

    private void zoomOutCamera(){
        zoomFactor += 0.1f;
        camera.viewportWidth = WORLD_WIDTH * zoomFactor;
        camera.viewportHeight = WORLD_HEIGHT * zoomFactor;
        camera.update();
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
        System.out.println(
            "touchDown: screenX: " + screenX + ", screenY: " + screenY + ", pointer: " + pointer +
                ", button: " + button
        );

        touchedDownX = screenX;
        touchedDownY = screenY;
        isMouseTouchedDown = true;
        originalCameraPos = camera.position.cpy();

        System.out.println("origin camera position: " + camera.position);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchedDownX: " + touchedDownX + ", touchedDownY: " + touchedDownY);

        System.out.println(
            "touchDown: screenX: " + screenX + ", screenY: " + screenY + ", pointer: " + pointer +
                ", button: " + button
        );

        float deltaX = screenX - touchedDownX;
        float deltaY = screenY - touchedDownY;

        System.out.println("deltaX: " + deltaX + ", deltaY: " + deltaY);
        isMouseTouchedDown = false;
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println(
            "touchDown: screenX: " + screenX + ", screenY: " + screenY + ", pointer: " + pointer
        );
        if (isMouseTouchedDown){
            float deltaX = screenX - touchedDownX;
            float deltaY = screenY - touchedDownY;
            Vector3 newCameraPos = originalCameraPos.cpy();
            newCameraPos.add(-deltaX, deltaY, 0f);
            camera.position.set(newCameraPos);
            camera.update();
        }
        return true;
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
