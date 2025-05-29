package io.github.fighting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

    //Screen
    private OrthographicCamera camera;
    private Viewport viewport;

    //World parameters
    private final int WORLD_WIDTH = 1280;
    private final int WORLD_HEIGHT = 720;

    //Graphics
    private SpriteBatch batch;
    private Stage stage;

    //Fields
    private final float ZOOM_FACTOR = 0.375f;
//    private final float scale = 1f;

    private final float TIME_TILL_NEXT_INPUT = 0.2f;
    private float time;

    //Graphics

    private MyCharacter lCharacter;
    private MyCharacter rCharacter;

    private Platform platform;

    private MyHealthBar healthBarLeft;
    private MyHealthBar healthBarRight;

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

        lCharacter.dispose();
        rCharacter.dispose();

        healthBarRight.dispose();
        healthBarLeft.dispose();

        myController.dispose();

        punchesStatistic.dispose();
        statistic.dispose();
    }

    //Initiation

    private void initCharacters(){
        final float CHARACTERS_INIT_POS_X = 500f;
        final float CHARACTERS_INIT_POS_Y = 80f;

        //Load left Player
        try{
            lCharacter = new MyCharacter("Scorpion");
            lCharacter.setPosition(platform.getX() + CHARACTERS_INIT_POS_X,
                platform.getY() + CHARACTERS_INIT_POS_Y);
            lCharacter.showColliders(true);
        }
        catch (Exception e){
            System.out.println("Error to init the left Character");
        }

        //Load right Player
        try{
            rCharacter = new MyCharacter("Scorpion");
            rCharacter.setPosition(platform.getX() + CHARACTERS_INIT_POS_X + 200f,
                platform.getY() + CHARACTERS_INIT_POS_Y);
            rCharacter.showColliders(true);
            rCharacter.switchRight();

        }
        catch (Exception e){
            System.out.println("Error to init the right Character");
        }

        if (lCharacter != null && rCharacter != null){
            lCharacter.movement(false,false,false,
                false, platform, rCharacter);
            rCharacter.movement(false, false, false,
                false, platform, lCharacter);
        }
    }

    private void initUIComponents(){

        //Load health bars
        healthBarLeft = new MyHealthBar(lCharacter.getName(), false);

        healthBarRight = new MyHealthBar(rCharacter.getName(), true);

        //Load controller

        try{
            myController = new MyController();

            myController.getPunchesTouchpad().addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    lCharacter.punch(myController.getPunchesTouchpad());
                    return true;
//                    return super.touchDown(event, x, y, pointer, button);
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    super.touchUp(event, x, y, pointer, button);
                }

                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer) {
                    super.touchDragged(event, x, y, pointer);
                }
            });

            myController.getDirectionsTouchpad().addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    lCharacter.movement(myController.getDirectionsTouchpad(), platform, rCharacter);
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    lCharacter.movement(false,false,false,
                        false, platform, rCharacter);
                    super.touchUp(event, x, y, pointer, button);
                }

                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer) {
//                    lCharacter.movement(myController.getDirectionsTouchpad(), platform, rCharacter);
                    super.touchDragged(event, x, y, pointer);
                }
            });
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
        viewport = new StretchViewport(WORLD_WIDTH * ZOOM_FACTOR,
            WORLD_HEIGHT * ZOOM_FACTOR, camera);
        viewport.apply();

        //Init the characters
        initCharacters();

        //Init UI elements
        initUIComponents();

        //Create batch
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        //Add UI components
        if (myController != null){
            myController.addToStage(stage);
        }
        stage.addActor(statistic);
        stage.addActor(punchesStatistic);

        //Combine stages to make sensor touch
//        multiplexer = new InputMultiplexer();
//        multiplexer.addProcessor(stage);
//        multiplexer.addProcessor(this);

        Gdx.input.setInputProcessor(stage);
//        Gdx.input.setInputProcessor(this);
//        Gdx.input.setInputProcessor(multiplexer);
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

        camera.viewportWidth = WORLD_WIDTH * ZOOM_FACTOR + 100;
        camera.update();

        final float HEALTH_BAR_OFFSET_X = 150F;
        final float HEALTH_BAR_OFFSET_y = 105F;

        healthBarLeft.setPosition(camera.position.x - HEALTH_BAR_OFFSET_X,
            camera.position.y + HEALTH_BAR_OFFSET_y);
        healthBarRight.setPosition(camera.position.x + HEALTH_BAR_OFFSET_X,
            camera.position.y + HEALTH_BAR_OFFSET_y);

        myController.setDirButtonsPos(camera.position.x - 250,camera.position.y - 105);
        myController.setPunchButtonsPos(camera.position.x + 150, camera.position.y - 105);

        batch.setProjectionMatrix(camera.combined);

        stage.act(delta);
        batchWork(delta);
        stage.draw();
    }

    private void batchWork(float delta){
        batch.begin();

        platform.render(batch);


        if(lCharacter != null){
            lCharacter.movement(myController, platform, rCharacter);
            lCharacter.tryComboAttempt(myController);
            lCharacter.collideWithEnemy(rCharacter);
//            lCharacter.movement(false,false,true,false,
//                platform, rCharacter);

            lCharacter.render(batch, delta);
            healthBarLeft.setHealth(lCharacter.getHealth());
        }

        if(rCharacter != null){
            rCharacter.movement(false,false,false,false,
                platform, lCharacter);

            rCharacter.render(batch, delta);
            healthBarRight.setHealth(rCharacter.getHealth());
        }

//        healthBarLeft.render(batch, camera);
//        healthBarRight.render(batch, camera);

        healthBarLeft.draw(batch, camera,  1);
        healthBarRight.draw(batch, camera, 1);

        final float STATISTICS_OFFSET_Y = 50F;

        statistic.setPosition(healthBarLeft.getX(),  healthBarLeft.getY() - STATISTICS_OFFSET_Y);
        statistic.setStats(myController.getDirectionsTouchpad());

        punchesStatistic.setPosition(healthBarRight.getX(),  healthBarRight.getY() - STATISTICS_OFFSET_Y);
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

}
