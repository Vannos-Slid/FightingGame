package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.tommyettinger.textra.Font;
import com.github.tommyettinger.textra.TypingLabel;


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
    private final float zoomFactor = 0.375f;
//    private final float scale = 1f;

    //Graphics
    private AnimatedCharacter leftCharacter;
    private AnimatedCharacter rightCharacter;

    private MyCharacter lCharacter;
    private MyCharacter rCharacter;

    private Platform platform;

    private ControlButtons controlButtons;

    private HealthBar healthBarLeft;
    private HealthBar healthBarRight;

    //Controller
    private MyController myController;

    //Labels
    private MyLabel lCharLabel;

    //Fonts
    private Font myFont;

    //Skins
    private Skin skin;

    @Override
    public void dispose() {
        stage.dispose();

        batch.dispose();

        platform.dispose();

        leftCharacter.dispose();
        rightCharacter.dispose();

        lCharacter.dispose();
        rCharacter.dispose();

        controlButtons.dispose();

        healthBarRight.dispose();
        healthBarLeft.dispose();

        myController.dispose();

        myFont.dispose();
        skin.dispose();
    }

    //Initiation

    private void charactersInit(){
        //Load left Player
        leftCharacter = new AnimatedCharacter("Scorpion");
        leftCharacter.setPosition(platform.getX() + 500, platform.getY() + 80);
        leftCharacter.showColliders(true);

        try{
            lCharacter = new MyCharacter("Scorpion");
            lCharacter.setPosition(platform.getX() + 500, platform.getY() + 80);
            lCharacter.showColliders(true);
        }
        catch (Exception e){
            System.out.println("LoxL");
        }

        //Load right Player
        rightCharacter = new AnimatedCharacter("Scorpion");
        rightCharacter.setPosition(platform.getX() + 700, platform.getY() + 80);
        rightCharacter.switchRight();
//        rightCharacter.showColliders(true);

        try{
            rCharacter = new MyCharacter("Scorpion");
            rCharacter.setPosition(platform.getX() + 700, platform.getY() + 80);
            rCharacter.showColliders(true);
            rCharacter.switchRight();

        }
        catch (Exception e){
            System.out.println("LoxR");
        }
    }

    private void initUIComponents(){
        int numButtonsSize = 100;

        //Load buttons
        controlButtons = new ControlButtons(numButtonsSize, numButtonsSize, numButtonsSize,
            numButtonsSize, leftCharacter);

        //Load health bars
        healthBarLeft = new HealthBar(100, 0, 0, "LifeBar/lifeBar1.png",
            false);

        healthBarRight = new HealthBar(100, 0, 0, "LifeBar/lifeBar1.png",
            true);

        //Load controller

        try{
            myController = new MyController();
        }
        catch (Exception e){
            System.out.println("There was an error appeared when controller init");
        }

        try{
            lCharLabel = new MyLabel("Scorpion");
        }
        catch (Exception e){
            System.out.println("There was an error appeared when label init");
        }

    }

    private void init(String strLevelName){
        //Load platform

        platform = MySimplerMethods.loadPlatform(strLevelName);

        int newPlatformX = (WORLD_WIDTH - platform.getWidth())/2;
        platform.setX(newPlatformX);

        //Set viewport
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH * zoomFactor,
            WORLD_HEIGHT * zoomFactor, camera);
        viewport.apply();

        charactersInit();

        initUIComponents();

        //Create batch
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        myFont = MySimplerMethods.generateDefaultFont();

        skin = new Skin(Gdx.files.internal("data/UI-skins/Fonts/mk3FontSkin.json"));

        BitmapFont bitmapFont = skin.get("default", BitmapFont.class);

        myFont = new Font(bitmapFont);

        com.github.tommyettinger.textra.Styles.LabelStyle textraStyle = null;

        textraStyle = new com.github.tommyettinger.textra.Styles.LabelStyle(myFont, bitmapFont.getColor());

//        TypingLabel label = new TypingLabel("Lent's roll", skin, "default");

        TypingLabel label1 = new TypingLabel("Gay",(textraStyle));


        label1.setPosition(healthBarLeft.getX() + 100, healthBarRight.getY());

//        stage.addActor(directionsTouchpad);
//        stage.addActor(punchesTouchpad);

        stage.addActor(myController.getDirectionsTouchpad());
        stage.addActor(myController.getPunchesTouchpad());
//        stage.addActor(lCharLabel);
        stage.addActor(label1);

        multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(this);

//        Gdx.input.setInputProcessor(this);

        Gdx.input.setInputProcessor(multiplexer);
    }

    GameScreen(String strLevelName) {
        init(strLevelName);
    }

    GameScreen() {
        this("The Balcony");
    }

    @Override
    public void show() {

    }

    // Render Objects
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

        lCharLabel.setPosition(healthBarLeft.getX() + 10, healthBarLeft.getY() + 9);

        batch.setProjectionMatrix(camera.combined);

        stage.act(delta);
        batchWork(delta);
        stage.draw();
    }

    private void batchWork(float delta){
        batch.begin();

        platform.render(batch);

//        rightCharacter.movement(false,false,false,
//            false, platform, leftCharacter);
//        rightCharacter.render(batch, delta);
//
//        controlButtons.render(batch, delta, camera, platform, rightCharacter);
//
//        leftCharacter.collideWithEnemy(rightCharacter);


        if(lCharacter != null){
            float touchpadKnobPercentX = myController.getDirectionsTouchpad().getKnobPercentX();
            float touchpadKnobPercentY = myController.getPunchesTouchpad().getKnobPercentY();

            lCharacter.movement(false,false,false,false,
                platform, rCharacter);

            System.out.println(leftCharacter.getCenteredX() + '\n' + lCharacter.getCenteredX());
            lCharacter.render(batch, delta);

        }

        if(rCharacter != null){
            rCharacter.movement(false,false,false,false,
                platform, lCharacter);

            rCharacter.render(batch, delta);

        }

        healthBarLeft.setHealth(leftCharacter.getHealth());
        healthBarRight.setHealth(rightCharacter.getHealth());

        healthBarLeft.render(batch, camera);
        healthBarRight.render(batch, camera);

//        if (controlButtons.directionButtons.isUpPressed){
//            leftCharacter.setPosition(platform.getX() + 500, platform.getY() + 80);
//            rightCharacter.setPosition(platform.getX() + 700, platform.getY() + 80);
//        }

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

    // Button methods

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
