package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen, InputProcessor {

    //Screen
    private OrthographicCamera camera;
    private Viewport viewport;

    //World parameters
    private final int WORLD_WIDTH = 1280;
    private final int WORLD_HEIGHT = 720;

    //Graphics
    private SpriteBatch batch;
    private Stage stage;
    private InputMultiplexer multiplexer;

    //Fields
    private String strSelectedLevel;
    private final float zoomFactor = 0.375f;
    private final float scale = 1f;

    //Graphics
    private AnimatedCharacter leftCharacter;
    private AnimatedCharacter rightCharacter;
    private Platform platform;
    private ControlButtons controlButtons;
    private HealthBar healthBarLeft;
    private HealthBar healthBarRight;

    GameScreen(String strLevelName) {

        int numButtonsSize = 100;

        //Load platform
        strSelectedLevel = strLevelName;
        String strDataPath = "data/maps.json";
        boolean isValid = loadPlatform(strDataPath);
        if(isValid)
            System.out.println("Map has been loaded successfully");
        int newPlatformX = (WORLD_WIDTH - platform.getWidth())/2;
        platform.setX(newPlatformX);

        //Set viewport
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH * zoomFactor,
            WORLD_HEIGHT * zoomFactor, camera);
        viewport.apply();

        //Load left Player
        leftCharacter = new AnimatedCharacter("Scorpion");
        leftCharacter.setPosition(platform.getX() + 500, platform.getY() + 80);
//        leftCharacter.showColliders(true);

        //Load right Player
        rightCharacter = new AnimatedCharacter("Scorpion");
        rightCharacter.setPosition(platform.getX() + 700, platform.getY() + 80);
        rightCharacter.switchRight();

        //Load animation
        controlButtons = new ControlButtons(numButtonsSize, numButtonsSize, numButtonsSize,
            numButtonsSize, leftCharacter);

        healthBarLeft = new HealthBar(100, 0, 0, "LifeBar/lifeBar1.png",
            false);

        healthBarRight = new HealthBar(100, 0, 0, "LifeBar/lifeBar1.png",
            true);

        //Create batch
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);

//        Gdx.input.setInputProcessor(this);

        Gdx.input.setInputProcessor(multiplexer);

//        System.out.println("Fuck");
    }

    GameScreen() {
        this("The Balcony");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Clear background
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Update Camera position
        float newCameraPosX = (float) (leftCharacter.getCenteredX() + rightCharacter.getCenteredX()) /2;

        if(newCameraPosX - camera.viewportWidth / 1.4 >= platform.getX() &&
            newCameraPosX + camera.viewportWidth / 1.4 <= platform.getRightBorder())
            camera.position.x = newCameraPosX;


        camera.viewportWidth = WORLD_WIDTH * zoomFactor + 100;
        camera.update();

        healthBarLeft.setPosition(camera.position.x - 150, camera.position.y + 105);
        healthBarRight.setPosition(camera.position.x + 150, camera.position.y + 105);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        platform.render(batch);

        rightCharacter.movement(false,false,false,
            false, platform, leftCharacter);
        rightCharacter.render(batch, delta);

        controlButtons.render(batch, delta, camera, platform, rightCharacter);

        leftCharacter.collideWithEnemy(rightCharacter);

        healthBarLeft.setHealth(leftCharacter.getHealth());
        healthBarRight.setHealth(rightCharacter.getHealth());

        healthBarLeft.render(batch, camera);
        healthBarRight.render(batch, camera);

        if (controlButtons.directionButtons.isUpPressed){
            leftCharacter.setPosition(platform.getX() + 500, platform.getY() + 80);
            rightCharacter.setPosition(platform.getX() + 700, platform.getY() + 80);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
    public void dispose() {
        batch.dispose();

        platform.dispose();

        leftCharacter.dispose();
        rightCharacter.dispose();

        controlButtons.dispose();

        healthBarRight.dispose();
        healthBarLeft.dispose();
    }

    private boolean loadPlatform(String strDataPath) {
        FileHandle fileHandle = Gdx.files.internal(strDataPath);
        if(fileHandle.exists()){
            JsonReader jsonReader = new JsonReader();
            JsonValue root = jsonReader.parse(fileHandle);

            JsonValue platformStats = root.get(strSelectedLevel);

            if(platformStats == null){
                System.out.println("Can't find level with this name");
                platform = new Platform("Null");
                return false;
            }

            String platformTexturePath = createTexturePath(platformStats.getString("texture"));
            float x = platformStats.getFloat("x");
            float y = platformStats.getFloat("y");
            boolean flip_h = platformStats.getBoolean("flip_h");

            JsonValue levelObjects = platformStats.get("child_objects");

            if(levelObjects == null){
                platform = new Platform(x, y, platformTexturePath, flip_h);
                return false;
            }

            TextureObjectP[] textureObjectPS = new TextureObjectP[levelObjects.size];

            for(int i = 0; i < levelObjects.size; i++) {
                JsonValue objData = levelObjects.get(i);
                String texturePath = "Levels/" + strSelectedLevel + "/" +
                    objData.getString("texture") + ".png";
                float childX = objData.getFloat("x");
                float childY = objData.getFloat("y");
                boolean child_flip_h1 = objData.getBoolean("flip_h");

                textureObjectPS[i] = new TextureObjectP(x + childX, y + childY,
                    texturePath, child_flip_h1);
            }

            platform = new Platform(x, y, platformTexturePath, flip_h, textureObjectPS);
            return true;
        }
        System.out.print("Invalid path: " + strDataPath + " to json file");
        platform = new Platform("Null");
        return false;
    }

    private String createTexturePath(String textureName){
        if(textureName == null)
            return "Null";
        return  "Levels/" + strSelectedLevel + "/" + textureName + ".png";
    }

    private String[] createAnimationPath(String characterName, String[] frameNames){
        String[] frames = new String[frameNames.length];
        for(int i = 0; i < frameNames.length; i++){
            frames[i] = "Characters/" + characterName + "/Default/" +
                frameNames[i] + ".png";
        }
        return frames;
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
        if(leftCharacter == null)
            return false;

        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        camera.unproject(touchPos);

        controlButtons.touchDownDir(screenX, screenY, camera);
        controlButtons.touchDownPunch(screenX, screenY, camera);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(leftCharacter == null)
            return false;

        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        camera.unproject(touchPos);

        controlButtons.touchUpDir(screenX, screenY, camera);
        controlButtons.touchUpPunch(screenX, screenY, camera);
        return true;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(leftCharacter == null)
            return false;

        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        camera.unproject(touchPos);

        controlButtons.touchDraggedDir(screenX, screenY, camera);
        controlButtons.touchDraggedPunch(screenX, screenY, camera);
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
