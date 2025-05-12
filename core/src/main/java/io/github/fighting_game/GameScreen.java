package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    private MyStatistic statistic;
    private  MyStatistic punchesStatistic;

    //Free The Memory
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
        healthBarLeft = new HealthBar(lCharacter.getName(),100, 0, 0,
            "LifeBar/lifeBar1.png", false);

        healthBarRight = new HealthBar(rCharacter.getName(),100, 0, 0,
            "LifeBar/lifeBar1.png", true);

        //Load controller

        try{
            myController = new MyController();
        }
        catch (Exception e){
            System.out.println("There was an error appeared when controller init");
        }

        statistic = new MyStatistic();
        punchesStatistic = new MyStatistic();
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

        //Init the characters
        charactersInit();

        //Init UI elements
        initUIComponents();

        //Create batch
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        //Add UI components
        if (myController != null){
            stage.addActor(myController.getDirectionsTouchpad());
            stage.addActor(myController.getPunchesTouchpad());
        }
        stage.addActor(statistic);
        stage.addActor(punchesStatistic);

        //Combine stages to make sensor touch
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
        float newCameraPosX = (float) (lCharacter.getCenteredX() + rCharacter.getCenteredX()) /2;

        if(newCameraPosX - camera.viewportWidth / 1.4 >= platform.getX() &&
            newCameraPosX + camera.viewportWidth / 1.4 <= platform.getRightBorder())
            camera.position.x = newCameraPosX;

        camera.viewportWidth = WORLD_WIDTH * zoomFactor + 100;
        camera.update();

        healthBarLeft.setPosition(camera.position.x - 150, camera.position.y + 105);
        healthBarRight.setPosition(camera.position.x + 150, camera.position.y + 105);

        myController.setDirButtonsPos(camera.position.x - 250,camera.position.y - 100);
        myController.setPunchButtonsPos(camera.position.x + 150, camera.position.y - 100);

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

            float touchpadKnobPercentXPunch = myController.getPunchesTouchpad().getKnobPercentX();
            float touchpadKnobPercentYPunch = myController.getPunchesTouchpad().getKnobPercentY();

            float touchpadKnobPercentXDir = myController.getDirectionsTouchpad().getKnobPercentX();
            float touchpadKnobPercentYDir = myController.getDirectionsTouchpad().getKnobPercentY();


            lCharacter.movement(touchpadKnobPercentXDir, touchpadKnobPercentYDir, platform, rCharacter);
//            lCharacter.movement(false,false,false,false,
//                platform, rCharacter);

            lCharacter.render(batch, delta);

        }

        if(rCharacter != null){

            rCharacter.movement(false,false,false,false,
                platform, rCharacter);

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

        statistic.setPosition(healthBarLeft.getX(),  healthBarLeft.getY() - 50);
        statistic.setStats(myController.getDirectionsTouchpad());

        punchesStatistic.setPosition(healthBarRight.getX(),  healthBarRight.getY() - 50);
        punchesStatistic.setStats(myController.getPunchesTouchpad());

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
